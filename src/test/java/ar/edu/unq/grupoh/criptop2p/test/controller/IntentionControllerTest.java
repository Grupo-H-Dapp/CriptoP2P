package ar.edu.unq.grupoh.criptop2p.test.controller;
import ar.edu.unq.grupoh.criptop2p.JWTHeader;
import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserResponse;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.service.IntentionService;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntentionControllerTest extends JWTHeader {
    @LocalServerPort
    private int port;

    @MockBean
    private CryptosService cryptosService;

    @Autowired
    private IntentionService intentionService;

    @Autowired
    private ar.edu.unq.grupoh.criptop2p.webservice.IntentionController intentionController;

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


        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> jwt = new HttpEntity<String>("", headers);
        ResponseEntity<UserResponse> user = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,jwt,UserResponse.class);
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,user.getBody().getId());
        HttpEntity<IntentionRequest> body = new HttpEntity<>(intentionRequest, headers);
        ResponseEntity<IntentionResponse> response = restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions", HttpMethod.POST, body, IntentionResponse.class);

        assertEquals(TypeOperation.SELL,response.getBody().getTypeOperation());
        assertEquals(5.00,response.getBody().getAmount());
        assertEquals(10.00,response.getBody().getPrice());
        assertEquals(CriptosNames.ALICEUSDT,response.getBody().getCrypto());
        assertEquals(user.getBody().getId(),response.getBody().getUser());
        assertEquals(IntentionStatus.ACTIVE,response.getBody().getStatus());
    }

    @Test
    public void createIntentionBuyForUSDTThatExceedPrice() {
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));

        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> jwt = new HttpEntity<String>("", headers);
        ResponseEntity<UserResponse> user = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,jwt,UserResponse.class);
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,100.00f, CriptosNames.ALICEUSDT,user.getBody().getId());
        HttpEntity<IntentionRequest> body = new HttpEntity<>(intentionRequest, headers);
        ResponseEntity<IntentionResponse> response = restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions", HttpMethod.POST, body, IntentionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getAllIntentionsActives() {
        HttpHeaders headers = this.generateHeaderWithToken();

        HttpEntity<String> jwt = new HttpEntity<String>("", headers);
        ResponseEntity<IntentionResponse[]> intentions = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions/actives", HttpMethod.GET, jwt, IntentionResponse[].class);

        assertEquals(0, intentions.getBody().length);
    }

    @Test
    public void getAllIntentions() {
        HttpHeaders headers = this.generateHeaderWithToken();

        HttpEntity<String> jwt = new HttpEntity<String>("", headers);
        ResponseEntity<IntentionResponse[]> intentions = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions", HttpMethod.GET, jwt, IntentionResponse[].class);

        assertEquals(0, intentions.getBody().length);
    }

    @Test
    public void getIntentionById() {
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));

        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> jwt = new HttpEntity<String>("", headers);
        ResponseEntity<UserResponse> user = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,jwt,UserResponse.class);
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,user.getBody().getId());
        HttpEntity<IntentionRequest> body = new HttpEntity<>(intentionRequest, headers);
        ResponseEntity<IntentionResponse> response = restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions", HttpMethod.POST, body, IntentionResponse.class);
        ResponseEntity<IntentionResponse> intention = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions/" + response.getBody().getId(), HttpMethod.GET, jwt, IntentionResponse.class);

        assertEquals(TypeOperation.SELL,intention.getBody().getTypeOperation());
        assertEquals(5.00,intention.getBody().getAmount());
        assertEquals(10.00,intention.getBody().getPrice());
        assertEquals(CriptosNames.ALICEUSDT,intention.getBody().getCrypto());
    }

    @Test
    public void getIntentionByIdThatNotExists() {
        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> jwt = new HttpEntity<String>("", headers);
        ResponseEntity<IntentionResponse> intention = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/intentions/100", HttpMethod.GET, jwt, IntentionResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, intention.getStatusCode());
    }

    @AfterEach
    public void tearDown() {
        this.intentionService.deleteAllIntentions();
        this.userService.deleteAllUsers();
    }
}
