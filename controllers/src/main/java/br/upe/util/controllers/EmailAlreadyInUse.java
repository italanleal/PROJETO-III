package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class EmailAlreadyInUse extends SystemException{
    public EmailAlreadyInUse(String msg, Throwable cause) { super(msg, cause);}
}
