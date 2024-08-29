package com.senla.exceptions;

public class AutowireFailureException extends RuntimeException {
    public AutowireFailureException(String message) {
        super(message);
    }
}
