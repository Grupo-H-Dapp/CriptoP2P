package ar.edu.unq.grupoh.criptop2p.test.service;
import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
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

import java.util.List;

@SpringBootTest
public class IntentionServiceTest {
    @Autowired
    private IntentionService intentionService;
    @Autowired
    private UserService userService;
    @MockBean
    private CryptosService cryptosService;
    @BeforeEach
    void setUp() throws UserException, UserAlreadyExistException {
        UserRequest userAnonimous = UserRequest.builder()
                .name("Anonimous")
                .surname("Anonimous")
                .address("1234567891")
                .email("prueba@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        this.userService.saveUser(userAnonimous);

    }
    @Test
    public void getAllIntentions() {
        List<IntentionResponse> intentions = intentionService.findAll();

        assertEquals(0,intentions.size());
    }

    @Test
    public void saveIntentionSellForCryptoUSDT() throws IntentionExceedPriceDifferenceException, UserNotFoundException {
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));

        User user = this.userService.getUserByEmail("prueba@gmail.com");
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,user.getUserId());
        IntentionResponse intentionResponse = this.intentionService.saveIntention(intentionRequest);

        assertEquals(TypeOperation.SELL,intentionResponse.getTypeOperation());
        assertEquals(5.00,intentionResponse.getAmount());
        assertEquals(10.00,intentionResponse.getPrice());
        assertEquals(CriptosNames.ALICEUSDT,intentionResponse.getCrypto());
        assertEquals(user.getUserId(),intentionResponse.getUser());
        assertEquals(IntentionStatus.ACTIVE,intentionResponse.getStatus());
    }

    @Test
    public void saveIntentionBuyExceedDifferencePrice() throws UserNotFoundException{
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 105.00f));
        User user = this.userService.getUserByEmail("prueba@gmail.com");
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,user.getUserId());
        IntentionExceedPriceDifferenceException exception = assertThrows(
                IntentionExceedPriceDifferenceException.class,
                () -> {
                    this.intentionService.saveIntention(intentionRequest);
                }
        );

        assertEquals("Price exceed difference 5%",exception.getMessage());
    }

    @Test
    public void getIntentionById() throws IntentionExceedPriceDifferenceException, IntentionNotFoundException, UserNotFoundException {
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));

        User user = this.userService.getUserByEmail("prueba@gmail.com");
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,user.getUserId());
        IntentionResponse intentionResponseCreated = this.intentionService.saveIntention(intentionRequest);
        IntentionResponse intentionById = this.intentionService.findById(intentionResponseCreated.getId());

        assertEquals(TypeOperation.SELL,intentionById.getTypeOperation());
        assertEquals(5.00,intentionById.getAmount());
        assertEquals(10.00,intentionById.getPrice());
        assertEquals(CriptosNames.ALICEUSDT,intentionById.getCrypto());
        assertEquals(user.getUserId(),intentionById.getUser());
    }

    @Test
    public void getIntentionThatNotExists() {
        IntentionNotFoundException exception = assertThrows(
                IntentionNotFoundException.class,
                () -> {
                    this.intentionService.findById((long) 1);
                }
        );

        assertEquals("Intention with 1 not found", exception.getMessage());

    }

    @Test
    public void getIntentionsActives() throws UserNotFoundException, IntentionExceedPriceDifferenceException{
        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));

        User user = this.userService.getUserByEmail("prueba@gmail.com");
        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,user.getUserId());
        this.intentionService.saveIntention(intentionRequest);
        List<IntentionResponse> intentionsActives =  this.intentionService.findAllActive();

        assertEquals(1,intentionsActives.size());

    }
    @AfterEach
    void tearDown() {
        this.intentionService.deleteAllIntentions();
        this.userService.deleteAllUsers();
    }
}
