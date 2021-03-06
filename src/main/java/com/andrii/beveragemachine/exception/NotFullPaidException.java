package com.andrii.beveragemachine.exception;

public class NotFullPaidException extends RuntimeException {
    private final String message;
    private final double remaining;

    public NotFullPaidException(String message, double remaining) {
        this.message = message;
        this.remaining = remaining;
    }

    public double getRemaining() {
        return remaining;
    }

    @Override
    public String getMessage() {
        return message + remaining;
    }
}
