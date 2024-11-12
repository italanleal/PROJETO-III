package br.upe.util;

public class UnauthenticatedUserException extends SystemException {
    public UnauthenticatedUserException() {
        super("User is not logged in", null);
    }
}
