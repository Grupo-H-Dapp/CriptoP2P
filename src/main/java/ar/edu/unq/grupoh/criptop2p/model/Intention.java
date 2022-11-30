package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "intention")
public class Intention {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dateCreated;
    @Getter @Setter
    private LocalDateTime dateEnded;
    @Getter @Setter
    private Double priceARS;

    public Intention() {
        this.status = IntentionStatus.ACTIVE;
        this.dateCreated = LocalDateTime.now();
    }

    public void completeIntention(){
        this.status = IntentionStatus.ENDED;
        this.dateEnded = LocalDateTime.now();
    }

    public static final class IntentionBuilder {

        private final Intention intention = new Intention();

        private IntentionBuilder() {
        }

        public IntentionBuilder withTypeOperation(TypeOperation type){
            intention.setTypeOperation(type);
            return this;
        }

        public IntentionBuilder withQuantity(double quantity){
            intention.setQuantity(quantity);
            return this;
        }

        public IntentionBuilder withPrice(float price){
            intention.setPrice(price);
            return this;
        }

        public IntentionBuilder withCryptoCurrency(CriptosNames cryptoName){
            intention.setCrypto(cryptoName);
            return this;
        }

        public IntentionBuilder withUser(User user){
            intention.setUser(user);
            return this;
        }

        public IntentionBuilder withId(Long id) {
            intention.setId(id);
            return this;
        }

        public IntentionBuilder withPriceARS(Double priceARS){
            intention.setPriceARS(priceARS);
            return this;
        }

        public Intention build() {
            return intention;
        }
    }

    public static IntentionBuilder builder() {
        return new IntentionBuilder();
    }
}

