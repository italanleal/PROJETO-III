package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

public class UserIsNotAdmin extends SystemException {
    public UserIsNotAdmin() { super("User isnt an admin", null);}
}