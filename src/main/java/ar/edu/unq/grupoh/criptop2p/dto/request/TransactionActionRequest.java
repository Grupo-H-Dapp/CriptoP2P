package ar.edu.unq.grupoh.criptop2p.dto.request;

import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import lombok.Getter;
import lombok.Setter;

public class TransactionActionRequest {

    @Getter @Setter
    private Action action;
    @Getter @Setter
    private Integer userId;
    @Getter @Setter
    private Long transactionId;
}
