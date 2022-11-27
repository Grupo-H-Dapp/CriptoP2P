package ar.edu.unq.grupoh.criptop2p;

import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionExceedPriceDifferenceException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.service.IntentionService;
import ar.edu.unq.grupoh.criptop2p.service.TransactionService;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${database:NONE}")
    private String className;

    @Autowired
    private UserService userService;
    @Autowired
    private IntentionService intentionService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CryptosService cryptosService;

    ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void initialize() throws UserException, UserAlreadyExistException, UserNotFoundException, IntentionExceedPriceDifferenceException {
        if (className.equals("prod")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UserException, UserAlreadyExistException, UserNotFoundException, IntentionExceedPriceDifferenceException {
        cryptosService.updateAllCryptos();
        User userPepe = User.builder().withName("Pepe").withLastname("Argento").withAddress("1234567891").withEmail("asdsadsa@gmail.com").withPassword("aAsadsadsad#")
                .withCvu("1234567891234567891233").withWallet("12345678").build();
        User userDardo = User.builder().withName("Dardo").withLastname("Fuseneco").withAddress("9876543219").withEmail("dardoF@gmail.com").withPassword("asdsadsadD#")
                .withCvu("1234567891234567891255").withWallet("87654321").build();
        userPepe = userService.saveUser(modelMapper.map(userPepe, UserRequest.class));
        userDardo =userService.saveUser(modelMapper.map(userDardo, UserRequest.class));
        Intention i1 = Intention.builder().withUser(userPepe).withCryptoCurrency(CriptosNames.ALICEUSDT).withQuantity(0.10).withTypeOperation(TypeOperation.BUY)
                .withPrice(1.1000f).build();
        intentionService.saveIntentionModel(i1);
        Transaction t1 = Transaction.builder().withIntention(i1).withUserSecondUser(userDardo).withState(StatesTransaction.WAITING_CONFIRM_TRANSFER_CRYPTO).build();
        transactionService.saveTransaction(t1);
    }
}
