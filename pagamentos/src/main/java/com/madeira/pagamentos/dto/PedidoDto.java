package com.madeira.pagamentos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime dataHora;
    private BigDecimal desconto;
    private BigDecimal total;

}
