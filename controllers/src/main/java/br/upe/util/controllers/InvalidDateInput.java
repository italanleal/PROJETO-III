package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class InvalidDateInput extends SystemException {
    public InvalidDateInput(String msg, Throwable cause) { super(msg, cause); }
}
