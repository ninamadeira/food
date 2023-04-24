package com.madeira.pagamentos.exceptions;

public class PagamentoNotFoundException extends RuntimeException{
    public PagamentoNotFoundException(String message) {
        super(message);
    }
}
