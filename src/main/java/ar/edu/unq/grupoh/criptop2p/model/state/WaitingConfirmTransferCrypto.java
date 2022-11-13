package ar.edu.unq.grupoh.criptop2p.model.state;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import org.springframework.beans.factory.annotation.Autowired;

public class WaitingConfirmTransferCrypto extends StateTransaction {

    public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction ){
        if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
            return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
        }else{
            return userAction.getUserId() == transaction.getSecondUser().getUserId();
        }
    }

    //CONFIRM_CRYPTO
    public void checkValidation(User user, Transaction transaction, Cryptocurrency cryptocurrency) throws TransactionStatusException {
        if (checkUserBasedOnTypeIntention(user,transaction)) {
            if(transaction.isInPriceRange(cryptocurrency)){
                transaction.setStateTransaction(new Completed());
                transaction.givePointsCompleted();
                transaction.completeIntention();
            }
        } else {
            throw new TransactionStatusException("Usuario invalido para la accion");
        }
    }

    public void change(Action action, User user, Transaction transaction , Cryptocurrency cryptocurrency) throws TransactionStatusException {
        switch (action) {
            case CANCEL:
                user.substractPoints();
                transaction.setStateTransaction(new Canceled());
                break;
            case CONFIRM_CRYPTO:
                checkValidation(user,transaction,cryptocurrency);
                break;
            default:
                throw new TransactionStatusException("Accion invalida");
        }
    }
}
