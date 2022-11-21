package ar.edu.unq.grupoh.criptop2p.test.controller;

import ar.edu.unq.grupoh.criptop2p.exceptions.CryptoException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.webservice.CryptosController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CryptoControllerTest extends JWTHeaderTest {
    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @MockBean
    private CryptosService cryptosService;

    @Autowired
    private CryptosController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;
    @BeforeAll
    void before() throws CryptoException {
        this.token = this.generateUserAndAuthenticated(HTTP_LOCALHOST,this.port);
        List<Cryptocurrency> cryptos = List.of(Cryptocurrency.builder().withCryptoCurrency(CriptosNames.AAVEUSDT).build(),
                Cryptocurrency.builder().withCryptoCurrency(CriptosNames.ALICEUSDT).build());
        Mockito.when(this.cryptosService.findAll()).thenReturn(cryptos);
    }

    @Test
    void getAllCryptos() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ this.token);
        HttpEntity<String> body = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Cryptocurrency[]> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/crypto/cryptocurrency",HttpMethod.GET,
                body, Cryptocurrency[].class);
        List<Cryptocurrency> cryptos = Arrays.stream(response.getBody()).toList();
        assertEquals(2, cryptos.size());
    }
}
