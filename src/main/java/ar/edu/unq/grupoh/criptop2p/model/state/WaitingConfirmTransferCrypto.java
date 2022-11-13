package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;

public class WaitingConfirmTransferCrypto extends StateTransaction {

    public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction){
        if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
            return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
        }else{
            return userAction.getUserId() == transaction.getSecondUser().getUserId();
        }
    }

    //CONFIRM_CRYPTO
    public void checkValidation(User user, Transaction transaction) throws TransactionStatusException {
        if (checkUserBasedOnTypeIntention(user,transaction)) {
            transaction.setStateTransaction(new Completed());
            transaction.givePointsCompleted();
        } else {
            throw new TransactionStatusException("Usuario invalido para la accion");
        }
    }

    public void change(Action action, User user, Transaction transaction) throws TransactionStatusException {
        switch (action) {
            case CANCEL:
                user.substractPoints();
                transaction.setStateTransaction(new Canceled());
                break;
            case CONFIRM_CRYPTO:
                checkValidation(user,transaction);
                break;
            default:
                throw new TransactionStatusException("Accion invalida");
        }
    }
}
