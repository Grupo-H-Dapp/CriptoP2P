package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.service.response.CotizationUSDToARS;
import org.springframework.web.client.RestTemplate;

public class ApiDolar {
    private final RestTemplate restTemplate = new RestTemplate();

    public CotizationUSDToARS getUSDCotization() {
        String url = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        return restTemplate.getForObject(url, CotizationUSDToARS.class);
    }

}
