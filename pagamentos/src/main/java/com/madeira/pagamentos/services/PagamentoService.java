package com.madeira.pagamentos.services;

import com.madeira.pagamentos.client.PedidoClient;
import com.madeira.pagamentos.dto.PagamentoDto;
import com.madeira.pagamentos.dto.PedidoDto;
import com.madeira.pagamentos.entities.Pagamento;
import com.madeira.pagamentos.entities.Status;
import com.madeira.pagamentos.exceptions.PagamentoException;
import com.madeira.pagamentos.exceptions.PagamentoNotFoundException;
import com.madeira.pagamentos.repositories.PagamentoRepositoy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepositoy repository;

    private final  ModelMapper modelMapper;

    private final PedidoClient pedido;

    public List<Pagamento> obterTodos() {
        return repository
                .findAll();
    }

    public Pagamento obterPorId(Long id) {
        Optional<Pagamento> response = repository.findById(id);
        return response.orElseThrow(() -> new PagamentoNotFoundException("Pagamento não encontrado"));
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        PedidoDto pedidoDto = pedido.obterPedido(pagamento.getPedidoId());
        BigDecimal valor = pagamento.getValor();
        if(valor.compareTo(pedidoDto.getTotal()) == -1 || valor.compareTo(pedidoDto.getTotal()) == 1) {
            throw new PagamentoException("Pagamento não é igual a total do pedido");

        }
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent()) {
            throw new PagamentoNotFoundException("Pagamento não encontrado");
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        repository.save(pagamento.get());
        pedido.atualizaPagamento(pagamento.get().getPedidoId());
    }

}

