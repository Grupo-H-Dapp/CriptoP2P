package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;

import static ar.edu.unq.grupoh.criptop2p.model.enums.Action.TRANSFER_MONEY;

public class WaitingTransferMoney extends StateTransaction{
    public void change(Action action, User user, Transaction transaction) {
        if (action == TRANSFER_MONEY) {
            if (user != null ) {
                transaction.setStateTransaction(null);
            } else {
                throw new RuntimeException("No se puede transferir a si mismo");
            }
        }
        throw new RuntimeException("La operacion debe ser transferencia de dinero");
    }
}
