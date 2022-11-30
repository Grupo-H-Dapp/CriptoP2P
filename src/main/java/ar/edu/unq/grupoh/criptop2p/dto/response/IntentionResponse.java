package ar.edu.unq.grupoh.criptop2p.dto.response;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;

import lombok.Getter;
public class IntentionResponse {
    @Getter
    private Long id;
    @Getter
    private CriptosNames crypto;
    @Getter
    private Integer user;
    @Getter
    private TypeOperation typeOperation;
    @Getter
    private IntentionStatus status;
    @Getter
    private Double price; //Es el valor en USD
    @Getter
    private Double amount; //deberia ser quantity que seria la cantidad de cryptos
    @Getter
    private Double amountARS;

    public IntentionResponse(Long id, CriptosNames crypto, Integer user, TypeOperation typeOperation, IntentionStatus status, Double price, Double amount,Double amountARS) {
        this.id = id;
        this.crypto = crypto;
        this.user = user;
        this.typeOperation = typeOperation;
        this.status = status;
        this.price = price;
        this.amount = amount;
        this.amountARS = amountARS;
    }


    public static IntentionResponse FromModel(Intention intention) {
        return new IntentionResponse(intention.getId(), intention.getCrypto(), intention.getUser().getUserId(), intention.getTypeOperation(), intention.getStatus(), intention.getPrice().doubleValue(), intention.getQuantity(), intention.getPriceARS().doubleValue());
    }
}
