package ar.edu.unq.grupoh.criptop2p.dto.response;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CryptoVolumn {
    @Getter@Setter
    private CriptosNames crypto;
    @Getter@Setter
    private Double priceTotalUSD;
    @Getter@Setter
    private Double ammount;

    public CryptoVolumn(CriptosNames crypto, Double priceTotalUSD, Double ammount) {
        this.crypto = crypto;
        this.priceTotalUSD = priceTotalUSD;
        this.ammount = ammount;
    }
}
