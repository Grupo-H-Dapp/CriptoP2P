package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.ExceedPriceDifference;
import ar.edu.unq.grupoh.criptop2p.exceptions.IlegalActionOnStateTransaction;
import ar.edu.unq.grupoh.criptop2p.exceptions.IlegalUserChangeStateTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

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
    private LocalDateTime dateCreated;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intention_id")
    private Intention intention;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondUser")
    private User secondUser;
    @Getter @Setter
    @Column(nullable = false)
    private String addressSendMoney;
    @Getter @Setter
    @Column(nullable = false)
    private String addressSendCrypto;
    @Getter @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CriptosNames crypto;
    @Getter @Setter
    @Column(nullable = false)
    private Double quantity;
    @Getter @Setter
    @Column(nullable = false)
    private Float price; //PRECIO NOMINAL
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private StatesTransaction stateTransaction;
    @Getter @Setter
    private LocalDateTime dateEnded;
    @Getter @Setter
    @Transient
    private ApiBinance apiBinance = new ApiBinance();

    public Transaction(Intention intention, User secondUser,StatesTransaction stateTransaction) {
        this.intention = intention;
        this.secondUser = secondUser;
        this.dateCreated = LocalDateTime.now();
        this.addressSendMoney = intention.getTypeOperation() == TypeOperation.SELL ? intention.getUser().getCvu() : secondUser.getCvu();
        this.addressSendCrypto = intention.getTypeOperation() == TypeOperation.SELL ? secondUser.getAddressWallet() : intention.getUser().getAddressWallet();
        this.crypto = intention.getCrypto();
        this.quantity = intention.getQuantity();
        this.price = intention.getPrice();
        this.stateTransaction = stateTransaction != null ? stateTransaction :  StatesTransaction.WAITING_TRANSFER_MONEY;
    }



    public void givePointsCompleted() {
        LocalDateTime endedTime = LocalDateTime.now();
        long timePassed = Duration.between(this.getDateCreated(), endedTime).toMinutes();
        int points = timePassed <= 30 ? 10 : 5;
        this.getSecondUser().addPoint(points);
        this.getIntention().getUser().addPoint(points);

    }

    public boolean isInPriceRange(Cryptocurrency cryptoCurrency) throws ExceedPriceDifference {
        return cryptoCurrency.validateDiffPrice(intention.getPrice());
    }

    public void completeIntention(){
        this.intention.completeIntention();
    }

    public void change(User user, Action action) throws  IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        this.stateTransaction.onChange(user, this, action);
    }

    public static final class TransactionBuilder {
        private final Transaction transaction = new Transaction();

        private TransactionBuilder() {}

        public TransactionBuilder withIntention(Intention intention){
            transaction.setIntention(intention);
            return this;
        }
        public TransactionBuilder withSecondUser(User user){
            transaction.setSecondUser(user);
            return this;
        }

        public TransactionBuilder withState(StatesTransaction state){
            transaction.setStateTransaction(state);
            return this;
        }

        public Transaction build(){
            return new Transaction(transaction.getIntention(),transaction.getSecondUser(),transaction.getStateTransaction());
        }
    }

    public static TransactionBuilder builder(){return new TransactionBuilder();}

}
