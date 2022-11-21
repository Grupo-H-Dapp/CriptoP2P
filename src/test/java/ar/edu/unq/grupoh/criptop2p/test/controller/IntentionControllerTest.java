package ar.edu.unq.grupoh.criptop2p.test.controller;
import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.service.IntentionService;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import ar.edu.unq.grupoh.criptop2p.webservice.IntentionController;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntentionControllerTest extends JWTHeaderTest {
    @LocalServerPort
    private int port;

    @MockBean
    private CryptosService cryptosService;

    @Autowired
    private IntentionService intentionService;

    @Autowired
    private IntentionController intentionController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void authenticated() {
        this.token = this.generateUserAndAuthenticated(HTTP_LOCALHOST,this.port);
    }

    @Test
    public void createIntentionBuyForUSDT() {
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));

        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,1);
        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<IntentionRequest> body = new HttpEntity<>(intentionRequest, headers);
        ResponseEntity<IntentionResponse> response = restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions", HttpMethod.POST, body, IntentionResponse.class);

        assertEquals(TypeOperation.SELL,response.getBody().getTypeOperation());
        assertEquals(5.00,response.getBody().getAmount());
        assertEquals(10.00,response.getBody().getPrice());
        assertEquals(CriptosNames.ALICEUSDT,response.getBody().getCrypto());
        assertEquals(1,response.getBody().getUser());
        assertEquals(IntentionStatus.ACTIVE,response.getBody().getStatus());
    }

    @AfterEach
    public void tearDown() {
        this.intentionService.deleteAllIntentions();
        this.userService.deleteAllUsers();
    }
}
