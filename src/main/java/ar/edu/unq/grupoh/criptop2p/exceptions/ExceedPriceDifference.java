package ar.edu.unq.grupoh.criptop2p.exceptions;

public class ExceedPriceDifference extends Exception {
    public ExceedPriceDifference() {
        super("Price exceed difference 5%");
    }
}
