package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;

public class Canceled extends StateTransaction {
    @Override
    public void change(Action action, User user, Transaction transaction, Cryptocurrency cryptocurrency) throws TransactionStatusException {
        throw new TransactionStatusException("La transaccion esta cancelada , no se aceptan mas acciones");
    }
}
