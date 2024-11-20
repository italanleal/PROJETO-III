package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class SystemIOException extends SystemException {
    public SystemIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
