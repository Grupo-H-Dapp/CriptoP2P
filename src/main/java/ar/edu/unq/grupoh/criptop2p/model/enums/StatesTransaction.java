package ar.edu.unq.grupoh.criptop2p.model.enums;

import ar.edu.unq.grupoh.criptop2p.exceptions.ExceedPriceDifference;
import ar.edu.unq.grupoh.criptop2p.exceptions.IlegalActionOnStateTransaction;
import ar.edu.unq.grupoh.criptop2p.exceptions.IlegalUserChangeStateTransaction;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.response.BinanceResponse;

//WAITING_TRANSFER_MONEY,WAITING_CONFIRM_TRANSFER_MONEY,WAITING_TRANSFER_CRYPTO,WAITING_CONFIRM_TRANSFER_CRYPTO,CANCELED,COMPLETED
public enum StatesTransaction {

    WAITING_TRANSFER_MONEY {

        public boolean acceptedAction(Action action){
            return action == Action.TRANSFER_MONEY;
        }

        public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction){
            if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
                return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
            }else{
                return userAction.getUserId() == transaction.getSecondUser().getUserId();
            }
        }

        public void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction, IlegalUserChangeStateTransaction {
            if (action == Action.CANCEL && StatesTransaction.isUserInTransaction(user,transaction)) {
                user.substractPoints();
                transaction.setStateTransaction(CANCELED);
                return;
            }
            if (!acceptedAction(action)) {
                throw new IlegalActionOnStateTransaction();
            }
            if (!checkUserBasedOnTypeIntention(user,transaction)) {
                throw new IlegalUserChangeStateTransaction();
            } else {
                transaction.setStateTransaction(WAITING_CONFIRM_TRANSFER_MONEY);
            }
        }

    },
    WAITING_CONFIRM_TRANSFER_MONEY {
        public boolean acceptedAction(Action action){
            return action == Action.CONFIRM_MONEY;
        }

        public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction){
            if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
                return userAction.getUserId() == transaction.getSecondUser().getUserId() ;
            }else{
                return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
            }
        }

        //CONFIRM_MONEY
        public void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction, IlegalUserChangeStateTransaction {
            if (action == Action.CANCEL && StatesTransaction.isUserInTransaction(user,transaction)) {
                user.substractPoints();
                transaction.setStateTransaction(CANCELED);
                return;
            }
            if (!acceptedAction(action)) {
                throw new IlegalActionOnStateTransaction();
            }
            if (!checkUserBasedOnTypeIntention(user,transaction)) {
                throw new IlegalUserChangeStateTransaction();
            } else {
                transaction.setStateTransaction(WAITING_TRANSFER_CRYPTO);
            }
        }
    },
    WAITING_TRANSFER_CRYPTO {
        public boolean acceptedAction(Action action){
            return action == Action.TRANSFER_CRYPTO;
        }

        public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction){
            if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
                return userAction.getUserId() == transaction.getSecondUser().getUserId();

            }else{
                return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
            }
        }

        //Action TRANSFER_CRYPTO
        public void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction, IlegalUserChangeStateTransaction {
            if (action == Action.CANCEL && StatesTransaction.isUserInTransaction(user,transaction)) {
                user.substractPoints();
                transaction.setStateTransaction(CANCELED);
                return;
            }
            if (!acceptedAction(action)) {
                throw new IlegalActionOnStateTransaction();
            }
            if (!checkUserBasedOnTypeIntention(user,transaction)) {
                throw new IlegalUserChangeStateTransaction();
            } else {
                transaction.setStateTransaction(WAITING_CONFIRM_TRANSFER_CRYPTO);
            }
        }
    },

    WAITING_CONFIRM_TRANSFER_CRYPTO {
        public boolean acceptedAction(Action action){
            return action == Action.CONFIRM_CRYPTO;
        }

        public boolean checkUserBasedOnTypeIntention(User userAction , Transaction transaction ){
            if(transaction.getIntention().getTypeOperation() == TypeOperation.BUY){
                return userAction.getUserId() == transaction.getIntention().getUser().getUserId();
            }else{
                return userAction.getUserId() == transaction.getSecondUser().getUserId();
            }
        }

        public void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction, IlegalUserChangeStateTransaction, ExceedPriceDifference {
            if (action == Action.CANCEL && StatesTransaction.isUserInTransaction(user,transaction)) {
                user.substractPoints();
                transaction.setStateTransaction(CANCELED);
                return;
            }
            if (!acceptedAction(action)) {
                throw new IlegalActionOnStateTransaction();
            }
            if (!checkUserBasedOnTypeIntention(user,transaction)) {
                throw new IlegalUserChangeStateTransaction();
            } else {
                BinanceResponse response = transaction.getApiBinance().getBinanceResponse(transaction.getCrypto());
                Cryptocurrency cryptocurrency = new Cryptocurrency(transaction.getCrypto(), response.getPrice());
                if(transaction.isInPriceRange(cryptocurrency)){
                    transaction.setStateTransaction(COMPLETED);
                    transaction.givePointsCompleted();
                    transaction.completeIntention();
                }
            }
        }
    },
    COMPLETED {
        public void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction {
            throw new IlegalActionOnStateTransaction();
        }
    },
    CANCELED {
        public void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction {
            throw new IlegalActionOnStateTransaction();
        }
    };

    public abstract void onChange(User user, Transaction transaction,Action action) throws IlegalActionOnStateTransaction, IlegalUserChangeStateTransaction, ExceedPriceDifference;

    private static boolean isUserInTransaction(User userAction, Transaction transaction) {
        return userAction.getUserId() == transaction.getSecondUser().getUserId() || userAction.getUserId() == transaction.getIntention().getUser().getUserId();
    }

}
