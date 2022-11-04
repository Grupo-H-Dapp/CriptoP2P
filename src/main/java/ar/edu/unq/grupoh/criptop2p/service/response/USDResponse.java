package ar.edu.unq.grupoh.criptop2p.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class USDResponse {
    @Getter @Setter
    private Float venta;

    @Getter @Setter
    private Float compra;
}
