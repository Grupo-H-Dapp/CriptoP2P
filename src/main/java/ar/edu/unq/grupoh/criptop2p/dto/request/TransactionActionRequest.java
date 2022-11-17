package ar.edu.unq.grupoh.criptop2p.dto.request;

import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import lombok.Getter;

public class TransactionActionRequest {

    @Getter
    private Action action;
    @Getter
    private Integer userId;
    @Getter
    private Long intentionId;
}
