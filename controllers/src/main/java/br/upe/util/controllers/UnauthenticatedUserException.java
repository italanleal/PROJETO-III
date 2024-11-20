package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class UnauthenticatedUserException extends SystemException {
    public UnauthenticatedUserException() {
        super("User is not logged in", null);
    }
}
