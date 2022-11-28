package ar.edu.unq.grupoh.criptop2p.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class TransactionRequest {

    @Getter @Setter
    private Integer user;
    @Getter @Setter
    private Long intentionId;


}
