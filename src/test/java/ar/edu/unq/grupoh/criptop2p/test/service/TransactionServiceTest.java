package ar.edu.unq.grupoh.criptop2p.test.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionActionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.service.IntentionService;
import ar.edu.unq.grupoh.criptop2p.service.TransactionService;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TransactionServiceTest {
    @MockBean
    private CryptosService cryptosService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private IntentionService intentionService;
    @Autowired
    private UserService userService;

    @Test
    public void createdAndTransactionOfAnIntentionSell() throws UserAlreadyExistException, UserException, IntentionExceedPriceDifferenceException, UserNotFoundException, IntentionNotFoundException {
        UserRequest userBuyer = UserRequest.builder()
                .name("Buyer")
                .surname("Buyer")
                .address("1234567891")
                .email("buyer@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        UserRequest userOwnerIntention = UserRequest.builder()
                .name("Owner")
                .surname("Owner")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("87654321")
                .build();
        User userOwn = userService.saveUser(userOwnerIntention);
        User userBuy = userService.saveUser(userBuyer);

        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));


        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,userOwn.getUserId());
        IntentionResponse intentionResponse = intentionService.saveIntention(intentionRequest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setIntentionId(intentionResponse.getId());
        transactionRequest.setUser(userBuy.getUserId());

        Transaction transaction = this.transactionService.createTransaction(transactionRequest);

        assertEquals(StatesTransaction.WAITING_TRANSFER_MONEY, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(userOwn.getUserId(), transaction.getIntention().getUser().getUserId());
        assertEquals(userBuy.getUserId(), transaction.getSecondUser().getUserId());
    }

    @Test
    public void sendActionTransferMoneyOnTransactionOfAnIntentionSell() throws
    UserAlreadyExistException, UserException, IntentionExceedPriceDifferenceException, UserNotFoundException, IntentionNotFoundException,
    TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference, TransactionStatusException {

        UserRequest userBuyer = UserRequest.builder()
                .name("Buyer")
                .surname("Buyer")
                .address("1234567891")
                .email("buyer@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        UserRequest userOwnerIntention = UserRequest.builder()
                .name("Owner")
                .surname("Owner")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("87654321")
                .build();
        User userOwn = userService.saveUser(userOwnerIntention);
        User userBuy = userService.saveUser(userBuyer);

        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));


        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,userOwn.getUserId());
        IntentionResponse intentionResponse = intentionService.saveIntention(intentionRequest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setIntentionId(intentionResponse.getId());
        transactionRequest.setUser(userBuy.getUserId());

        Transaction transaction = this.transactionService.createTransaction(transactionRequest);

        TransactionActionRequest transactionActionRequest = new TransactionActionRequest();
        transactionActionRequest.setTransactionId(transaction.getId());
        transactionActionRequest.setAction(Action.TRANSFER_MONEY);
        transactionActionRequest.setUserId(userBuy.getUserId());
        Transaction transactionWithStateWaitingConfirmTransferMoney = this.transactionService.processActionOperation(transactionActionRequest);

        assertEquals(StatesTransaction.WAITING_CONFIRM_TRANSFER_MONEY, transactionWithStateWaitingConfirmTransferMoney.getStateTransaction());
    }

    @Test
    public void sendActionConfirmMoneyOnTransactionOfAnIntentionSell() throws
    UserAlreadyExistException, UserException, IntentionExceedPriceDifferenceException, UserNotFoundException, IntentionNotFoundException,
    TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference, TransactionStatusException {

        UserRequest userBuyer = UserRequest.builder()
                .name("Buyer")
                .surname("Buyer")
                .address("1234567891")
                .email("buyer@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        UserRequest userOwnerIntention = UserRequest.builder()
                .name("Owner")
                .surname("Owner")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("87654321")
                .build();
        User userOwn = userService.saveUser(userOwnerIntention);
        User userBuy = userService.saveUser(userBuyer);

        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));


        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,userOwn.getUserId());
        IntentionResponse intentionResponse = intentionService.saveIntention(intentionRequest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setIntentionId(intentionResponse.getId());
        transactionRequest.setUser(userBuy.getUserId());

        Transaction transaction = this.transactionService.createTransaction(transactionRequest);

        //TRANSFER_MONEY
        TransactionActionRequest transactionActionRequest = new TransactionActionRequest();
        transactionActionRequest.setTransactionId(transaction.getId());
        transactionActionRequest.setAction(Action.TRANSFER_MONEY);
        transactionActionRequest.setUserId(userBuy.getUserId());
        this.transactionService.processActionOperation(transactionActionRequest);
        //CONFIRM_MONEY
        transactionActionRequest.setAction(Action.CONFIRM_MONEY);
        transactionActionRequest.setUserId(userOwn.getUserId());
        Transaction transactionWithStateWaitingTransferCrypto = this.transactionService.processActionOperation(transactionActionRequest);

        assertEquals(StatesTransaction.WAITING_TRANSFER_CRYPTO, transactionWithStateWaitingTransferCrypto.getStateTransaction());
    }

    @Test
    public void sendActionTransferCryptoOnTransactionOfAnIntentionSell() throws
    UserAlreadyExistException, UserException, IntentionExceedPriceDifferenceException, UserNotFoundException, IntentionNotFoundException,
    TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference, TransactionStatusException {

        UserRequest userBuyer = UserRequest.builder()
                .name("Buyer")
                .surname("Buyer")
                .address("1234567891")
                .email("buyer@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        UserRequest userOwnerIntention = UserRequest.builder()
                .name("Owner")
                .surname("Owner")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("87654321")
                .build();
        User userOwn = userService.saveUser(userOwnerIntention);
        User userBuy = userService.saveUser(userBuyer);

        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));


        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,userOwn.getUserId());
        IntentionResponse intentionResponse = intentionService.saveIntention(intentionRequest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setIntentionId(intentionResponse.getId());
        transactionRequest.setUser(userBuy.getUserId());

        Transaction transaction = this.transactionService.createTransaction(transactionRequest);

        //TRANSFER_MONEY
        TransactionActionRequest transactionActionRequest = new TransactionActionRequest();
        transactionActionRequest.setTransactionId(transaction.getId());
        transactionActionRequest.setAction(Action.TRANSFER_MONEY);
        transactionActionRequest.setUserId(userBuy.getUserId());
        this.transactionService.processActionOperation(transactionActionRequest);
        //CONFIRM_MONEY
        transactionActionRequest.setAction(Action.CONFIRM_MONEY);
        transactionActionRequest.setUserId(userOwn.getUserId());
        this.transactionService.processActionOperation(transactionActionRequest);
        //TRANSFER_Crypto
        transactionActionRequest.setAction(Action.TRANSFER_CRYPTO);
        transactionActionRequest.setUserId(userOwn.getUserId());
        Transaction transactionWithStateWaitingConfirmTransferCrypto = this.transactionService.processActionOperation(transactionActionRequest);

        assertEquals(StatesTransaction.WAITING_CONFIRM_TRANSFER_CRYPTO, transactionWithStateWaitingConfirmTransferCrypto.getStateTransaction());
    }

    @Test
    public void sendActionConfirmCryptoOnTransactionOfAnIntentionSellAndExceedDifferencePrice() throws
    UserAlreadyExistException, UserException, IntentionExceedPriceDifferenceException, UserNotFoundException, IntentionNotFoundException,
    TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference, TransactionStatusException {

        UserRequest userBuyer = UserRequest.builder()
                .name("Buyer")
                .surname("Buyer")
                .address("1234567891")
                .email("buyer@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        UserRequest userOwnerIntention = UserRequest.builder()
                .name("Owner")
                .surname("Owner")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("87654321")
                .build();
        User userOwn = userService.saveUser(userOwnerIntention);
        User userBuy = userService.saveUser(userBuyer);

        Mockito.when(this.cryptosService.getCryptoCurrency(CriptosNames.ALICEUSDT)).thenReturn(new Cryptocurrency(CriptosNames.ALICEUSDT, 10.00f));


        IntentionRequest intentionRequest = new IntentionRequest(TypeOperation.SELL,5.00,10.00f, CriptosNames.ALICEUSDT,userOwn.getUserId());
        IntentionResponse intentionResponse = intentionService.saveIntention(intentionRequest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setIntentionId(intentionResponse.getId());
        transactionRequest.setUser(userBuy.getUserId());

        Transaction transaction = this.transactionService.createTransaction(transactionRequest);

        //TRANSFER_MONEY
        TransactionActionRequest transactionActionRequest = new TransactionActionRequest();
        transactionActionRequest.setTransactionId(transaction.getId());
        transactionActionRequest.setAction(Action.TRANSFER_MONEY);
        transactionActionRequest.setUserId(userBuy.getUserId());
        this.transactionService.processActionOperation(transactionActionRequest);
        //CONFIRM_MONEY
        transactionActionRequest.setAction(Action.CONFIRM_MONEY);
        transactionActionRequest.setUserId(userOwn.getUserId());
        this.transactionService.processActionOperation(transactionActionRequest);
        //TRANSFER_CRYPTO
        transactionActionRequest.setAction(Action.TRANSFER_CRYPTO);
        transactionActionRequest.setUserId(userOwn.getUserId());
        this.transactionService.processActionOperation(transactionActionRequest);
        //CONFIRM_CRYPTO
        transactionActionRequest.setAction(Action.CONFIRM_CRYPTO);
        transactionActionRequest.setUserId(userBuy.getUserId());
        //ExceedPriceDifference
        ExceedPriceDifference exceptionDifferencePrice = assertThrows(
                ExceedPriceDifference.class,
                () -> this.transactionService.processActionOperation(transactionActionRequest)
        );

        assertEquals("Price exceed difference 5%", exceptionDifferencePrice.getMessage());
    }

    @AfterEach
    void tearDown() {
        this.transactionService.deleteAll();
        this.intentionService.deleteAllIntentions();
        this.userService.deleteAllUsers();
    }
}
