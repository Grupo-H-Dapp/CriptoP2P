package ar.edu.unq.grupoh.criptop2p.dto.response;

import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

public class TransactionResponse {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private LocalDateTime dateCreated;
    @Getter @Setter
    private Long intentionId;
    @Getter @Setter
    private int userintentionId;
    @Getter @Setter
    private int secondUserId;
    private Transaction wrapped;
    @Getter @Setter
    private CriptosNames crypto;
    @Getter @Setter
    private StatesTransaction stateTransaction;

    public TransactionResponse(Transaction wrapped) {
        this.wrapped = wrapped;
        this.dateCreated = wrapped.getDateCreated();
        this.intentionId = wrapped.getIntention().getId();
        this.id = wrapped.getId();
        this.secondUserId = wrapped.getSecondUser().getUserId();
        this.userintentionId = wrapped.getIntention().getUser().getUserId();
        this.crypto = wrapped.getCrypto();
        this.stateTransaction = wrapped.getStateTransaction();

    }


}
