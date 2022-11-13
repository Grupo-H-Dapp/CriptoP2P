package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;

abstract public class StateTransaction {
    abstract public void change(Action action, User user, Transaction transaction) throws TransactionStatusException;
}
