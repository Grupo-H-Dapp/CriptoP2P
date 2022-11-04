package ar.edu.unq.grupoh.criptop2p;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.User;
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

    ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void initialize() throws UserException, UserAlreadyExistException {
        if (className.equals("prod")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UserException, UserAlreadyExistException {
        User user = User.builder().withName("Pepe").withLastname("Argento").withAddress("1234567891").withEmail("asdsadsa@gmail.com").withPassword("aAsadsadsad#")
                .withCvu("1234567891234567891233").withWallet("12345678").build();
        User user1 = User.builder().withName("Dardo").withLastname("Fuseneco").withAddress("9876543219").withEmail("dardoF@gmail.com").withPassword("asdsadsadD#")
                .withCvu("1234567891234567891255").withWallet("87654321").build();
        userService.saveUser(modelMapper.map(user, UserRequest.class));
        userService.saveUser(modelMapper.map(user1, UserRequest.class));
    }
}
