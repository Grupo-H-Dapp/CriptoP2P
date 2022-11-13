package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import lombok.Getter;

public class TransactionActionRequestDto {

    @Getter
    private Action action;
    @Getter
    private Integer userId;
    @Getter
    private Long intentionId;
}
