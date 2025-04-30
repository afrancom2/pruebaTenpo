package com.tenpo.prueba.util.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Forbidden request");
    }
}
