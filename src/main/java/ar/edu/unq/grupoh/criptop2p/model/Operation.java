package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StateOperation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Operation {

    private LocalDateTime dateTime;
    private Intention intention;
    private User secondUser;
    private String direccionEnvio; //intention == venta ? intention.user.cvu : intention.user.addressWallet
    private CriptosNames crypto;
    private Double quantity;
    private Double price; //PRECIO NOMINAL
    private StateOperation stateOperation;
}
