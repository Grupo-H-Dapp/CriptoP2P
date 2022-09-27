package ar.edu.unq.grupoh.criptop2p.exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String  msg) {
        super("Ya existe un usuario con el email " + msg);
    }
}
