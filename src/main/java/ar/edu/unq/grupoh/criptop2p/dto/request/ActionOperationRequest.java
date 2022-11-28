package ar.edu.unq.grupoh.criptop2p.dto.request;

import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ActionOperationRequest {
    private Action action;
    private Long user;
    private Long transaction;
}
