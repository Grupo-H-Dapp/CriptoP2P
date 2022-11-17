package ar.edu.unq.grupoh.criptop2p.dto;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class IntentionRequest {

    @Getter @Setter
    @NotBlank(message = "the Intention type cannot be null")
    private TypeOperation typeIntention;

    @Getter @Setter
    @NotBlank(message = "The amount cannot be null")
    //@DecimalMin(message = "The amount available must be bigger than 0", value = "1")
    private double amount;

    @Getter @Setter
    @NotBlank(message = "The price must not be null")
    //@DecimalMin(message = "The price must be bigger than 0.01", value = "0.01")
    private float price;

    @Getter @Setter
    @NotBlank(message = "Crypto Currency must be specified")
    private CriptosNames cryptoName;

    @Getter @Setter
    @NotBlank(message = "The user must be specified")
    private Integer user;

    public IntentionRequest(TypeOperation typeIntention, double amount, float price, CriptosNames cryptoName, Integer user) {
        this.typeIntention = typeIntention;
        this.amount = amount;
        this.price = price;
        this.cryptoName = cryptoName;
        this.user = user;
    }

}
