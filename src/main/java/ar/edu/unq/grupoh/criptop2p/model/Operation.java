package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StateOperation;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

import static ar.edu.unq.grupoh.criptop2p.model.enums.StateOperation.WAITING_CONFIRM;

@Data
public class Operation {
    private LocalDateTime dateTime;
    private Intention intention;
    private User secondUser;
    private String direccionEnvio; //intention == venta ? intention.user.cvu : intention.user.addressWallet
    private CriptosNames crypto;
    private Double quantity;
    private Double price; //PRECIO NOMINAL
    private StateOperation stateOperation;

    public Operation(Intention intention, User secondUser) {
        this.intention = intention;
        this.secondUser = secondUser;
        this.direccionEnvio = intention.getTypeOperation() == TypeOperation.SELL ? intention.getUser().getCvu() : intention.getUser().getAddressWallet();
        this.crypto = intention.getCrypto();
        this.quantity = intention.getQuantity();
        this.price = intention.getPrice();
        this.stateOperation = StateOperation.ONGOING;
    }

    public void doTransfer(User userAction) {
        if(stateOperation == StateOperation.ONGOING){
            if(userAction == this.getIntention().getUser()){
                throw new RuntimeException("No se puede transferir a si mismo");
            }
            this.stateOperation = StateOperation.WAITING_CONFIRM;
        }
        throw new RuntimeException("La transaccion no esta activa"); // no esta esperando o cancelada
    }

    public void doConfirm(User userAction) {
        if(stateOperation == StateOperation.WAITING_CONFIRM){
            if(! (userAction == this.getIntention().getUser())){
                throw new RuntimeException("El usuario no es el que tiene que confirmar");
            } else if (this.validateDiffPrice()) {
                this.stateOperation = StateOperation.COMPLETED;
                this.givePointsCompleted();
            } else {
                this.stateOperation = StateOperation.CANCELED;
            }
        }
        throw new RuntimeException("La operacion no puede avanzar"); // no esta esperando o cancelada
    }

    public void doCancel(User userAction) {
        if (userAction == this.getIntention().getUser() || userAction == this.getSecondUser()){
            this.stateOperation = StateOperation.CANCELED;
            userAction.substractPoints();
        }
    }

    public void doCancelSystem(){
        this.stateOperation = StateOperation.CANCELED;
    }

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
}
