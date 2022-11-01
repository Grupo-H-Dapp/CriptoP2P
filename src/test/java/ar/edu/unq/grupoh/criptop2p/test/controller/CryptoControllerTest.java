package ar.edu.unq.grupoh.criptop2p.test.controller;

import ar.edu.unq.grupoh.criptop2p.exceptions.CryptoException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.User;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CryptoControllerTest {
    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @MockBean
    private CryptosService cryptosService;

    @Autowired
    private CryptosController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void before() throws CryptoException {
        List<Cryptocurrency> cryptos = List.of(Cryptocurrency.builder().withCryptoCurrency(CriptosNames.AAVEUSDT).build(),
                Cryptocurrency.builder().withCryptoCurrency(CriptosNames.ALICEUSDT).build());
        Mockito.when(this.cryptosService.findAll()).thenReturn(cryptos);
    }

    @Test
    void getAllCryptos() {
        Cryptocurrency[] response = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/crypto/cryptocurrency",
                Cryptocurrency[].class);
        List<Cryptocurrency> cryptos = Arrays.stream(response).toList();
        assertEquals(2, cryptos.size());

    }
}
