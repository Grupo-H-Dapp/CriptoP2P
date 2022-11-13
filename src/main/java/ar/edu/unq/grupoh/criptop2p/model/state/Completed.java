package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;

public class Completed extends StateTransaction {

    @Override
    public void change(Action action, User user, Transaction transaction) throws TransactionStatusException {
        transaction.getIntention().completeIntention();
        throw new TransactionStatusException("La transaccion ya esta Completada , no se aceptan mas acciones");
    }
}
