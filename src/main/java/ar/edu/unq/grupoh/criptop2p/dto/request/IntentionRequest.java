package ar.edu.unq.grupoh.criptop2p.dto.request;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IntentionRequest {
    @NotNull
    @Getter @Setter
    private TypeOperation typeIntention;

    @NotNull
    @Getter @Setter
    private double amount;

    @NotNull
    @Getter @Setter
    private float price;

    @NotNull
    @Getter @Setter
    private CriptosNames crypto;

    @NotNull
    @Getter @Setter
    private Integer user;

    public IntentionRequest(TypeOperation typeIntention, double amount, float price, CriptosNames cryptoName, Integer user) {
        this.typeIntention = typeIntention;
        this.amount = amount;
        this.price = price;
        this.crypto = cryptoName;
        this.user = user;
    }

    public IntentionRequest() {
    }
}
