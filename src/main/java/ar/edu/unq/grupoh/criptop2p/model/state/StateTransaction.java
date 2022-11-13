package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;

abstract public class StateTransaction {
    public static final String STARTED_NAME = "Started";
    abstract public void change(Action action, User user, Transaction transaction, Cryptocurrency cryptocurrency) throws TransactionStatusException;
}
