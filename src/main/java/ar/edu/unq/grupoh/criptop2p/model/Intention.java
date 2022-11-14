package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "intention")
@NoArgsConstructor
public class Intention {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*  @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dateTime;*/
    @Getter @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CriptosNames crypto;
    @Getter @Setter
    @Column(nullable = false)
    private Double quantity; // CANTIDAD CRYPTO
    @Getter @Setter
    @Column(nullable = false)
    private Float price; //PRECIO NOMINAL en usd
    @Getter @Setter
    @Column(nullable = false)
    private Double amountArg; //PRECIO EN PESOS
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;
    @Getter @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation; //COMPRA O VENTA
    @Getter @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IntentionStatus status;

    public Intention(CriptosNames crypto, Double quantity, Float price, Double amountArg, User user, TypeOperation typeOperation) {
        this.crypto = crypto;
        this.quantity = quantity;
        this.price = price;
        this.amountArg = amountArg;
        this.user = user;
        this.typeOperation = typeOperation;
        this.status = IntentionStatus.ACTIVE;
    }

    public void completeIntention(){
        this.status = IntentionStatus.ENDED;
    }

    public static final class IntentionBuilder {

        private final Intention transaction = new Intention();

        private IntentionBuilder() {
        }

        public IntentionBuilder withTypeTransaction(TypeOperation type){
            transaction.setTypeOperation(type);
            return this;
        }

        public IntentionBuilder withAmountArg(double amount){
            transaction.setAmountArg(amount);
            return this;
        }

        public IntentionBuilder withQuantity(double quantity){
            transaction.setQuantity(quantity);
            return this;
        }

        public IntentionBuilder withPrice(float price){
            transaction.setPrice(price);
            return this;
        }

        public IntentionBuilder withCryptoCurrency(CriptosNames cryptoName){
            transaction.setCrypto(cryptoName);
            return this;
        }

        public IntentionBuilder withUser(User user){
            transaction.setUser(user);
            return this;
        }

        public IntentionBuilder withId(Long id) {
            transaction.setId(id);
            return this;
        }

        public Intention build() {
            return new Intention(transaction.getCrypto(),transaction.getQuantity(),transaction.getPrice(),transaction.getAmountArg(),transaction.getUser(),transaction.getTypeOperation());
        }
    }

    public static IntentionBuilder builder() {
        return new IntentionBuilder();
    }
}

