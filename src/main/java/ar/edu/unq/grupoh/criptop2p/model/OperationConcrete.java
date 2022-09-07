package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Criptos;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationConcrete {

    private LocalDateTime dateTime;
    private Intention intention;
    private User secondUser;
    private String direccionEnvio; //intention == venta ? intention.user.cvu : intention.user.addressWallet
    private Criptos crypto;
    private Double quantity;
    private Double price; //PRECIO NOMINAL
}
