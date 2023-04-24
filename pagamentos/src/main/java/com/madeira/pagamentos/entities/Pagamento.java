package com.madeira.pagamentos.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valor é obrigatório.")
    @Positive
    private BigDecimal valor;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max=100)
    private String nome;

    @NotNull(message = "Numero é obrigatório.")
    @Size(max=19)
    private String numero;

    @NotBlank(message = "Data expiracção é obrigatória.")
    @Size(max=7)
    private String expiracao;

    @NotBlank(message = "Código é obrigatório.")
    @Size(min=3, max=3)
    private String codigo;

    @NotNull(message = "Status é obrigatório.")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Pedido é obrigatório.")
    private Long pedidoId;

    @NotNull(message = "Forma de pagamento é obrigatória.")
    private Long formaDePagamentoId;


}
