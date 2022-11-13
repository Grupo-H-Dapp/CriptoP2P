package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.model.state.StateTransaction;
import ar.edu.unq.grupoh.criptop2p.model.state.WaitingTransferMoney;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import static ar.edu.unq.grupoh.criptop2p.model.enums.Action.TRANSFER_MONEY;

@Data
public class Transaction {
    private LocalDateTime dateTime;
    private Intention intention;
    private User secondUser;
    private String direccionEnvio; //intention == venta ? intention.user.cvu : intention.user.addressWallet
    private CriptosNames crypto;
    private Double quantity;
    private Double price; //PRECIO NOMINAL
    private StateTransaction stateTransaction;

    public Transaction(Intention intention, User secondUser) {
        this.intention = intention;
        this.secondUser = secondUser;
        this.direccionEnvio = intention.getTypeOperation() == TypeOperation.SELL ? intention.getUser().getCvu() : intention.getUser().getAddressWallet();
        this.crypto = intention.getCrypto();
        this.quantity = intention.getQuantity();
        this.price = intention.getPrice();
        this.stateTransaction = new WaitingTransferMoney();
    }
/*
    public void doTransfer(User userAction) {
        if(stateOperation == ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.ONGOING){
            if(userAction == this.getIntention().getUser()){
                throw new RuntimeException("No se puede transferir a si mismo");
            }
            this.stateOperation = ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.WAITING_CONFIRM;
        }
        throw new RuntimeException("La transaccion no esta activa"); // no esta esperando o cancelada
    }

    public void doConfirm(User userAction) {
        if(stateOperation == ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.WAITING_CONFIRM){
            if(! (userAction == this.getIntention().getUser())){
                throw new RuntimeException("El usuario no es el que tiene que confirmar");
            } else if (this.validateDiffPrice()) {
                this.stateOperation = ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.COMPLETED;
                this.givePointsCompleted();
            } else {
                this.stateOperation = ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.CANCELED;
            }
        }
        throw new RuntimeException("La operacion no puede avanzar"); // no esta esperando o cancelada
    }

    public void doCancel(User userAction) {
        if (userAction == this.getIntention().getUser() || userAction == this.getSecondUser()){
            this.stateOperation = ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.CANCELED;
            userAction.substractPoints();
        }
    }

    public void doCancelSystem(){
        this.stateOperation = ar.edu.unq.grupoh.criptop2p.model.enums.StateTransaction.CANCELED;
    }*/

    private void givePointsCompleted() {
        LocalDateTime endedTime = LocalDateTime.now();
        long timePassed = Duration.between(this.getDateTime(), endedTime).toMinutes();
        int points = timePassed <= 30 ? 10 : 5;
        this.getSecondUser().setPoints(points);
        this.getIntention().getUser().setPoints(points);
    }

    private boolean validateDiffPrice() {
        Double min = this.getPrice() * 0.95 ;
        Double max = this.getPrice() * 1.05 ;
        Double marketPrice = this.priceMarket(this.getCrypto()) ;
        return (min <= marketPrice) && (marketPrice <= max);//true en el caso que este en el margen
    }

    private Double priceMarket(CriptosNames crypto) {
        return 0.0; // Ver como obtenemos el valor de la crypto
    }

    public void changeState(Action action, User user) throws TransactionStatusException {
        this.stateTransaction.change(action,user,this);
    }
}
