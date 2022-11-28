package ar.edu.unq.grupoh.criptop2p.exceptions;

public class TransactionNotFoundException extends Exception{
    public TransactionNotFoundException() {
        super("Transaction not found");
    }
}
