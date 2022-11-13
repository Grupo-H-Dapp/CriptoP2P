package ar.edu.unq.grupoh.criptop2p.model.enums;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;


/*
  Ongoing es cuando esta recien creadad/iniciada la Operacion
  Canceled es cuando fue cancelada tanto por el sistema como por el usuario
  Completed es cuando se completo correctamente

  ONGOING -> CANCELED
  ONGOING -> WAITING_CONFIRM -> COMPLETED
  ONGOING -> WAITING_CONFIRM -> CANCELED ?
 */  //WAITING_TRANSFER_MONEY,WAITING_CONFIRM_TRANSFER_MONEY,WAITING_TRANSFER_CRYPTO,WAITING_CONFIRM_TRANSFER_CRYPTO,CANCELED,COMPLETED
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

        public void onChange(User user, Transaction transaction, Cryptocurrency cryptocurrency,Action action) throws TransactionStatusException {
            if (acceptedAction(action) && checkUserBasedOnTypeIntention(user,transaction)) {
                transaction.setStateTransaction(WAITING_CONFIRM_TRANSFER_MONEY);
            } else {
                throw new TransactionStatusException("Usuario invalido para la accion");
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
        public void onChange(User user, Transaction transaction, Cryptocurrency cryptocurrency,Action action) throws TransactionStatusException {
            if (acceptedAction(action) && checkUserBasedOnTypeIntention(user,transaction)) {
                transaction.setStateTransaction(WAITING_TRANSFER_CRYPTO);
            } else {
                throw new TransactionStatusException("Usuario invalido para la accion");
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
        public void onChange(User user, Transaction transaction, Cryptocurrency cryptocurrency,Action action) throws TransactionStatusException {
            if (acceptedAction(action) && checkUserBasedOnTypeIntention(user,transaction)) {
                transaction.setStateTransaction(WAITING_CONFIRM_TRANSFER_CRYPTO);
            } else {
                throw new TransactionStatusException("Usuario invalido para la accion");
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

        //CONFIRM_CRYPTO
        public void onChange(User user, Transaction transaction, Cryptocurrency cryptocurrency,Action action) throws TransactionStatusException {
            if (acceptedAction(action)&& checkUserBasedOnTypeIntention(user,transaction)) {
                if(transaction.isInPriceRange(cryptocurrency)){
                    transaction.setStateTransaction(COMPLETED);
                    transaction.givePointsCompleted();
                    transaction.completeIntention();
                }
            } else {
                throw new TransactionStatusException("Usuario invalido para la accion");
            }
        }
    },
    COMPLETED {
        
    },
    CANCELED {
        public void onChange(User user, Transaction transaction, Cryptocurrency cryptocurrency,Action action) throws TransactionStatusException {
            throw new TransactionStatusException("La transaccion esta cancelada , no se aceptan mas acciones");
        }
    };

    public void onChange(User user, Transaction transaction, Cryptocurrency cryptocurrency,Action action) throws TransactionStatusException {
    }
}
