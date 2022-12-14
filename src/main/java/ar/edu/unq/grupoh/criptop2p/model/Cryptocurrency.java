package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.CryptoException;
import ar.edu.unq.grupoh.criptop2p.exceptions.ExceedPriceDifference;
import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cryptocurrency")
@NoArgsConstructor
public class Cryptocurrency implements Serializable {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private CriptosNames crypto;
    @Getter @Setter
    @NotNull
    private Float price;
    @Getter @Setter
    private LocalDateTime date;

    public Cryptocurrency(CriptosNames crypto, Float price) {
        this.crypto = crypto;
        this.price = price;
        this.date = LocalDateTime.now();
    }

    public boolean validateDiffPrice(float price) throws ExceedPriceDifference {
        Double minValue = this.price*(0.95);
        Double maxValue = this.price*(1.05);
        if(price > minValue && price < maxValue){
            return true;
        }else{
            throw new ExceedPriceDifference();
        }
    }



    public static final class Builder {
        private final Cryptocurrency cryptoCurrency = new Cryptocurrency();

        private Builder() {}

        public Builder withCryptoCurrency(CriptosNames cryptoName){
            cryptoCurrency.setCrypto(cryptoName);
            return this;
        }

        public Builder withPrice(float price) {
            cryptoCurrency.setPrice(price);
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            cryptoCurrency.setDate(date);
            return this;
        }

        public Cryptocurrency build() {
            return cryptoCurrency;
        }
    }

    public static Builder builder(){
        return new Builder();
    }

}
