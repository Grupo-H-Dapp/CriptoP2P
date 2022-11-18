package ar.edu.unq.grupoh.criptop2p.service.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

public class CotizationUSDToARS {
    @Getter @Setter
    private Float compra;
    @Getter @Setter
    private Float venta;


    public CotizationUSDToARS(Float compra, Float venta) {
        this.compra = compra;
        this.venta = venta;
    }

    public CotizationUSDToARS() {
    }
}
