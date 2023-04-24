package com.madeira.pagamentos.client;

import com.madeira.pagamentos.dto.PedidoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "pedidos", url = "localhost:8090")
public interface PedidoClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/pedidos/{id}/pago")
    void atualizaPagamento(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/pedidos/{id}")
    PedidoDto obterPedido(@PathVariable Long id);
}
