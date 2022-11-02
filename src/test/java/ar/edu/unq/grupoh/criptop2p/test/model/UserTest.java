package ar.edu.unq.grupoh.criptop2p.test.model;
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

    @Test
    void createOneUserWithEmailWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withEmail("anything")
        );
        assertEquals("Email not valid", exception.getMessage());
    }

    @Test
    void createOneUserWithPasswordWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withPassword("123")
        );
        assertEquals("Password not valid", exception.getMessage());
    }

    @Test
    void createOneUserWithNameWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withName("1")
        );
        assertEquals("Name or Lastname not valid", exception.getMessage());
    }

    @Test
    void createOneUserWithLastNameWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withLastname("12345678910234567891231444111223")
        );
        assertEquals("Name or Lastname not valid", exception.getMessage());
    }

    @Test
    void createOneUserWithAddressWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withAddress("123456")
        );
        assertEquals("Address not valid", exception.getMessage());
    }

    @Test
    void createOneUserWithWalletAddressWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withWallet("walletAdress")
        );
        assertEquals("Wallet Adress not valid", exception.getMessage());
    }

    @Test
    void createOneUserWithCVUWrong() {
        UserException exception = assertThrows(
                UserException.class,
                () -> User.builder().withCvu("CVU Adress")
        );
        assertEquals("Cvu not valid", exception.getMessage());
    }
}