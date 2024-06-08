package com.mycompany.autostockcar.modelo.exception;


public class NegocioException extends RuntimeException {
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
