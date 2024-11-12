package br.upe.util;

public abstract class SystemException extends Exception {
    protected SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
