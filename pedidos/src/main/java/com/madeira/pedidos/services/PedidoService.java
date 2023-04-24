package com.madeira.pedidos.services;


import com.madeira.pedidos.dto.PedidoDto;
import com.madeira.pedidos.dto.StatusDto;
import com.madeira.pedidos.entities.ItemDoPedido;
import com.madeira.pedidos.entities.Pedido;
import com.madeira.pedidos.entities.Status;
import com.madeira.pedidos.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

    private final ModelMapper modelMapper;


    public List<PedidoDto> obterTodos() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setDataHora(LocalDateTime.now());
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        BigDecimal total = soma(pedido.getItens());
        if(Objects.nonNull(pedido.getDesconto())){
           if(total.compareTo(pedido.getDesconto()) == 1){
               total = total.subtract(pedido.getDesconto());
           }
        }
        pedido.setTotal(total);
        pedido.setStatus(Status.REALIZADO);
        Pedido salvo = repository.save(pedido);
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto atualizaStatus(Long id, StatusDto dto) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.getStatus());
        repository.atualizaStatus(dto.getStatus(), pedido);
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }

    private BigDecimal soma(List<ItemDoPedido> itens) {
        BigDecimal total = BigDecimal.ZERO;
        for(ItemDoPedido item: itens){
            total = total.add(item.getValor());
        }
        return total;
    }

}
