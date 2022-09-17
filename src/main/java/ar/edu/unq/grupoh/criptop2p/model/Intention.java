package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Intention {

    private LocalDateTime dateTime;
    private CriptosNames crypto;
    private Double quantity;
    private Double price; //PRECIO NOMINAL
    private Double amountArg;
    private User user;
    private TypeOperation typeOperation; //COMPRA O VENTA

}
