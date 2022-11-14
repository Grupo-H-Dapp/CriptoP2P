package ar.edu.unq.grupoh.criptop2p;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
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

    ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void initialize() throws UserException, UserAlreadyExistException {
        if (className.equals("prod")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UserException, UserAlreadyExistException {
        User userPepe = User.builder().withName("Pepe").withLastname("Argento").withAddress("1234567891").withEmail("asdsadsa@gmail.com").withPassword("aAsadsadsad#")
                .withCvu("1234567891234567891233").withWallet("12345678").build();
        User userDardo = User.builder().withName("Dardo").withLastname("Fuseneco").withAddress("9876543219").withEmail("dardoF@gmail.com").withPassword("asdsadsadD#")
                .withCvu("1234567891234567891255").withWallet("87654321").build();
        User pepeDB = userService.saveUser(modelMapper.map(userPepe, UserRequest.class));
        User dardoDB =userService.saveUser(modelMapper.map(userDardo, UserRequest.class));
        Intention i1 = Intention.builder().withUser(pepeDB).withCryptoCurrency(CriptosNames.ALICEUSDT).withQuantity(0.10).withTypeTransaction(TypeOperation.BUY)
                .withAmountArg(100.0).withPrice(10.0f).build();
        intentionService.saveIntention(i1);
        Transaction t1 = Transaction.builder().withIntention(i1).withUserSecondUser(dardoDB).build();
        transactionService.saveTransaction(t1);
    }
}
