package ar.edu.unq.grupoh.criptop2p.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(int id) {
        super("No se puede encontrar el usuario con el id "+ id);
    }

    public UserNotFoundException(String username) {
        super("No se puede encontrar el usuario " + username);
    }
}
