package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class PasswordDoesNotMatch extends SystemException {
    public PasswordDoesNotMatch(String msg, Throwable cause) {super(msg, cause);}
}
