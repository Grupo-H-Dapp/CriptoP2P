package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.model.state.StateTransaction;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import static ar.edu.unq.grupoh.criptop2p.model.enums.Action.TRANSFER_MONEY;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
public class Transaction {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intention_id")
    private Intention intention;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User secondUser;
    @Getter @Setter
    @Column(nullable = false)
    private String direccionEnvio;
    @Getter @Setter
    @Column(nullable = false)
    private CriptosNames crypto;
    @Getter @Setter
    @Column(nullable = false)
    private Double quantity;
    @Getter @Setter
    @Column(nullable = false)
    private Float price; //PRECIO NOMINAL
    @Getter @Setter
    private StatesTransaction stateTransaction;

    public Transaction(Intention intention, User secondUser) {
        this.intention = intention;
        this.secondUser = secondUser;
        this.direccionEnvio = intention.getTypeOperation() == TypeOperation.SELL ? intention.getUser().getCvu() : intention.getUser().getAddressWallet();
        this.crypto = intention.getCrypto();
        this.quantity = intention.getQuantity();
        this.price = intention.getPrice();
        this.stateTransaction = StatesTransaction.WAITING_TRANSFER_MONEY;
    }



    public void givePointsCompleted() {
        LocalDateTime endedTime = LocalDateTime.now();
        long timePassed = Duration.between(this.getDateTime(), endedTime).toMinutes();
        int points = timePassed <= 30 ? 10 : 5;
        this.getSecondUser().setPoints(points);
        this.getIntention().getUser().setPoints(points);
    }

    private Double priceMarket(CriptosNames crypto) {
        return 0.0; // Ver como obtenemos el valor de la crypto
    }


    public boolean isInPriceRange(Cryptocurrency cryptoCurrency){
        return cryptoCurrency.validateDiffPrice(intention.getPrice());
    }

    public void completeIntention(){
        this.intention.completeIntention();
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
}
