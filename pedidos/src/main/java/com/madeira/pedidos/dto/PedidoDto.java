package com.madeira.pedidos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.madeira.pedidos.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime dataHora;
    private List<ItemDoPedidoDto> itens = new ArrayList<>();

    private Status status;

    private BigDecimal desconto;
    private BigDecimal total;

}
