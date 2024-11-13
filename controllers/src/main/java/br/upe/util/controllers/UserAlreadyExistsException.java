package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class UserAlreadyExistsException extends SystemException {
    public UserAlreadyExistsException(String msg, Throwable cause) { super(msg, cause);}
}
