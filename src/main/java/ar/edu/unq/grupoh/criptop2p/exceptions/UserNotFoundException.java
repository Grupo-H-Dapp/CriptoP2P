package ar.edu.unq.grupoh.criptop2p.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(int id) {
        super("No se puede encontrar el usuario con el id"+ id);
    }
}
