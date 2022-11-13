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
@Table(name = "transactionIntention")
@NoArgsConstructor
public class Intention {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Getter @Setter
    @Column(nullable = false)
    private CriptosNames crypto;
    @Getter @Setter
    @Column(nullable = false)
    private Double quantity;
    @Getter @Setter
    @Column(nullable = false)
    private Double price; //PRECIO NOMINAL
    @Getter @Setter
    @Column(nullable = false)
    private Double amountArg;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Getter @Setter
    @Column(nullable = false)
    private TypeOperation typeOperation; //COMPRA O VENTA
    @Getter @Setter
    @Column(nullable = false)
    private IntentionStatus status;

    public Intention(LocalDateTime dateTime, CriptosNames crypto, Double quantity, Double price, Double amountArg, User user, TypeOperation typeOperation) {
        this.dateTime = dateTime;
        this.crypto = crypto;
        this.quantity = quantity;
        this.price = price;
        this.amountArg = amountArg;
        this.user = user;
        this.typeOperation = typeOperation;
    }

    public void completeIntention(){
        this.status = IntentionStatus.ENDED;
    }

}

