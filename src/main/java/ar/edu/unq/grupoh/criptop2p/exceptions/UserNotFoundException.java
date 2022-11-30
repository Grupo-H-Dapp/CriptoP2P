package ar.edu.unq.grupoh.criptop2p.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(int id) {
        super("Not found user "+ id);
    }

    public UserNotFoundException(String username) {
        super("Not found user " + username);
    }
}
