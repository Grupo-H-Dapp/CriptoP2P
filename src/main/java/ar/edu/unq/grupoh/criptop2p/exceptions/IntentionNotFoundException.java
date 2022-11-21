package ar.edu.unq.grupoh.criptop2p.exceptions;

public class IntentionNotFoundException extends Exception {
    public IntentionNotFoundException(int id) { super("Intention with " + id + " not found");}
}
