package ar.edu.unq.grupoh.criptop2p.test.controller;

import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.webservice.CryptosController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoControllerTest {
    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private CryptosController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CryptosService service;

    @Test
    public void criptosTest() throws Exception { //HTTP_LOCALHOST + port + "/crypto/cryptocurrency/update" //Cryptocurrency[].class
        Cryptocurrency[] listCotizacion = Objects.requireNonNull(this.restTemplate.postForObject(HTTP_LOCALHOST + port + "/crypto/cryptocurrency/update",
                null,Cryptocurrency[].class));
        assertThat(listCotizacion.length).isGreaterThan(0);
        assertThat(listCotizacion.length).isEqualTo(13);
    }
}
