package ar.edu.unq.grupoh.criptop2p.dto;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;


public class IntentionResponse {
    private Long id;
    private CriptosNames crypto;
    private Integer user;
    private TypeOperation typeOperation;
    private IntentionStatus status;
    private Double price;
    private Double amount;
    private Double amountARS;

    public IntentionResponse(Long id, CriptosNames crypto, Integer user, TypeOperation typeOperation, IntentionStatus status, Double price, Double amount, Double amountARS) {
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
        return new IntentionResponse(intention.getId(), intention.getCrypto(), intention.getUser().getUserId(), intention.getTypeOperation(), intention.getStatus(), intention.getPrice().doubleValue(), intention.getQuantity(), intention.getAmountArg());
    }
}
