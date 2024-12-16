package br.upe.util.persistencia;

public class UserNotFoundException extends SystemException {
    public UserNotFoundException(String msg, Throwable cause) { super(msg, cause);}
}
