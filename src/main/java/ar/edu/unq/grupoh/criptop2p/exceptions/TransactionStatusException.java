package ar.edu.unq.grupoh.criptop2p.exceptions;

public class TransactionStatusException extends Exception{
    public TransactionStatusException(String  msg) {
        super("Problema con el estado de la Intencion " + msg);
    }
}
