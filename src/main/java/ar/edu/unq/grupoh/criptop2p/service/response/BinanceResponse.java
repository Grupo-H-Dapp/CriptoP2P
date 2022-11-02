package ar.edu.unq.grupoh.criptop2p.service.response;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class BinanceResponse {

    @Getter @Setter
    private CriptosNames symbol;
    @Getter @Setter
    private Float price;

    public BinanceResponse(String symbol, Float price){
        this.setSymbol(CriptosNames.valueOf(symbol));
        this.setPrice(price);
    }
}
