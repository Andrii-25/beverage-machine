package com.andrii.beveragemachine.exception;

public class NoRemainderException extends RuntimeException {
    private final String message;

    public NoRemainderException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
