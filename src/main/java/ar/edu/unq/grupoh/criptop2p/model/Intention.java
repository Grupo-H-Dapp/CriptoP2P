package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.Criptos;
import ar.edu.unq.grupoh.criptop2p.model.enums.Operation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Intention {

    private LocalDateTime dateTime;
    private Criptos crypto;
    private Double quantity;
    private Double price; //PRECIO NOMINAL
    private Double amountArg;
    private User user;
    private Operation operation; //COMPRA O VENTA

}
