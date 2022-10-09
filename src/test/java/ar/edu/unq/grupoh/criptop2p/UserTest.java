package ar.edu.unq.grupoh.criptop2p;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    public User anUser() throws UserException {
        return User
                .builder()
                .withName("Pepe")
                .withLastname("Argento")
                .withAddress("1234567891")
                .withEmail("asdsadsa@gmail.com")
                .withPassword("aAsadsadsad#")
                .withCvu("1234567891234567891233")
                .withWallet("12345678")
                .build();
    }

    @Test
    void createOneUserSuccessful() throws UserException {
        User user1 = anUser();
        assertEquals("Pepe",user1.getName());
        assertEquals("Argento",user1.getLastname());
        assertEquals("asdsadsa@gmail.com",user1.getEmail());
        assertEquals("aAsadsadsad#",user1.getPassword());
    }
}