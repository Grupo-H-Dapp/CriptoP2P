package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.service.response.BinanceResponse;
import org.springframework.web.client.RestTemplate;

public class ApiBinance {

    private final RestTemplate restTemplate = new RestTemplate();

    public BinanceResponse getBinanceResponse(CriptosNames cryptoName) {
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + cryptoName.name();
        BinanceResponse br = restTemplate.getForObject(url, BinanceResponse.class);
        return br != null ? br : new BinanceResponse();
    }
}
