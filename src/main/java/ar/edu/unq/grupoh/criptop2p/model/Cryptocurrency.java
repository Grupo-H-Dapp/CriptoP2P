package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.CryptoException;
import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cryptocurrency")
@NoArgsConstructor
public class Cryptocurrency {
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

    public boolean validateDiffPrice2(float price) throws TransactionStatusException {
        Float min = this.getPrice() * 0.95F ;
        Float max = this.getPrice() * 1.05F ;
        System.out.println("Min Diff " + min);
        System.out.println("Max Diff " +max);
        System.out.println("Price Intention " +price);
        if((min <= price) && (price <= max)){
            return true;
        }else{
            throw new TransactionStatusException("Diferencia de precio");
        }
    }

    public boolean validateDiffPrice(float price) throws TransactionStatusException {
        Double minValue = this.price*(0.95);
        Double maxValue = this.price*(1.05);
        System.out.println("Crypto Price " + this.price);
        System.out.println("Min Dif " + minValue);
        System.out.println("Max Dif " +maxValue);
        System.out.println("Price Intention " +price);
        if(price > minValue && price < maxValue){
            return true;
        }else{
            throw new TransactionStatusException("Diferencia de precio");
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

        public Cryptocurrency build() throws CryptoException {
            return cryptoCurrency;
        }
    }

    public static Builder builder(){
        return new Builder();
    }

}
