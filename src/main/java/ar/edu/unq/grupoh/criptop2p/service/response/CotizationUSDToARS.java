package ar.edu.unq.grupoh.criptop2p.service.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CotizationUSDToARS {
    @Getter @Setter
    private Float priceSell;

    @Getter @Setter
    private Float priceBuy;

    public CotizationUSDToARS(Float sell, Float buy) {
        this.priceSell = sell;
        this.priceBuy = buy;
    }
}
