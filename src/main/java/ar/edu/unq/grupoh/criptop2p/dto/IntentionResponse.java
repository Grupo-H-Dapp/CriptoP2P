package ar.edu.unq.grupoh.criptop2p.dto;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Getter;
import lombok.Setter;


public class IntentionResponse {

    private final Intention wrapped;

    public IntentionResponse(Intention wrapped) {
        this.wrapped = wrapped;
    }

    private Long id;
    private CriptosNames crypto;
    private Integer idUser;
    private TypeOperation typeOperation;
    private IntentionStatus status;

    public Long getId() {
        return wrapped.getId();
    }

    public CriptosNames getCrypto() {
        return wrapped.getCrypto();
    }

    public Integer getIdUser() {
        return wrapped.getUser().getUserId();
    }

    public TypeOperation getTypeOperation() {
        return wrapped.getTypeOperation();
    }

    public IntentionStatus getStatus() {
        return wrapped.getStatus();
    }
}
