package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;

import static ar.edu.unq.grupoh.criptop2p.model.enums.Action.TRANSFER_CRYPTO;
import static ar.edu.unq.grupoh.criptop2p.model.enums.Action.TRANSFER_MONEY;

public class WaitingTransferMoney extends StateTransaction{

    //Validar si el usuario de la accion es que el corresponde al estado actual
    public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction){
        if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
            return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
        }else{
            return userAction.getUserId() == transaction.getSecondUser().getUserId();
        }
    }

    public void checkValidation(User user, Transaction transaction) throws TransactionStatusException {
        if (checkUserBasedOnTypeIntention(user,transaction)) {
                transaction.setStateTransaction(new WaitingConfirmTransferMoney());
            } else {
                throw new TransactionStatusException("Usuario invalido para la accion");
            }
    }

    public void change(Action action, User user, Transaction transaction, Cryptocurrency cryptocurrency) throws TransactionStatusException {
        switch (action) {
            case CANCEL:
                user.substractPoints();
                transaction.setStateTransaction(new Canceled());
                break;
            case TRANSFER_MONEY:
                checkValidation(user,transaction);
                break;
            default:
                throw new TransactionStatusException("Accion invalida");
        }
    }
}