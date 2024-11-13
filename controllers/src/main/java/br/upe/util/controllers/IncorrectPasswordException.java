package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class IncorrectPasswordException extends SystemException {
    public IncorrectPasswordException(String msg, Throwable cause) { super(msg, cause);}
}
