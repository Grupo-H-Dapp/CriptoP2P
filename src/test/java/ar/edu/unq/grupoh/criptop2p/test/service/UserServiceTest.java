package ar.edu.unq.grupoh.criptop2p.test.service;
import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private UserRequest toUserRequest(User user) {
        return UserRequest.builder()
                .name(user.getName())
                .surname(user.getLastname())
                .address(user.getAddress())
                .email(user.getEmail())
                .password(user.getPassword())
                .cvu(user.getCvu())
                .walletAddress(user.getAddressWallet())
                .build();
    }

    private User saveUserAnonimuous() throws UserException, UserAlreadyExistException, UserNotFoundException {
        UserRequest userAnonimous = UserRequest.builder()
                .name("Anonimous")
                .surname("Anonimous")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        return this.userService.saveUser(userAnonimous);
    }

    @BeforeEach
    void setUp() throws UserException, UserAlreadyExistException {
        UserRequest user = UserRequest.builder()
                .name("Prueba")
                .surname("Prueba")
                .address("1234567891")
                .email("prueba@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        this.userService.saveUser(user);
    }
    @Test
    void createOneUserAndSave() throws UserException, UserAlreadyExistException, UserNotFoundException {
        UserRequest user = UserRequest.builder()
            .name("Pepe")
            .surname("Argento")
            .address("1234567891")
            .email("asdsadsa@gmail.com")
            .password("aAsadsadsad#")
            .cvu("1234567891234567891233")
            .walletAddress("12345678")
            .build();
        this.userService.saveUser(user);
        User userCreated = this.userService.getUserByEmail("asdsadsa@gmail.com");
        assertEquals("Pepe", userCreated.getName());
        assertEquals("Argento", userCreated.getLastname());
        assertEquals("1234567891", userCreated.getAddress());
        assertEquals("asdsadsa@gmail.com", userCreated.getEmail());
        assertEquals("1234567891234567891233", userCreated.getCvu());
        assertEquals("12345678", userCreated.getAddressWallet());
    }

    @Test
    void updateEmailOfUserPrueba() throws UserException, UserAlreadyExistException, UserNotFoundException {
        User userPrueba = this.userService.getUserByEmail("prueba@gmail.com");
        userPrueba.setEmail("actualizado@gmail.com");
        this.userService.updateUser(this.toUserRequest(userPrueba), userPrueba.getUserId());
        User userEmailUpdate = this.userService.getUserByEmail("actualizado@gmail.com");
        assertEquals("actualizado@gmail.com", userEmailUpdate.getEmail());
    }

    @Test
    void getAllUsers() {
        List<User> users = this.userService.findAll();
        assertEquals( 1, users.size());
        assertEquals("Prueba", users.get(0).getName());
        assertEquals("Prueba", users.get(0).getLastname());
    }

    @Test
    void getUserById() throws UserException, UserAlreadyExistException, UserNotFoundException {
        User userAnonimuous = this.saveUserAnonimuous();
        User userById = this.userService.getUserById(userAnonimuous.getUserId());
        assertEquals("Anonimous", userById.getName());
        assertEquals("Anonimous", userById.getLastname());
    }

    @Test
    void deleteUserCreated() throws UserException, UserAlreadyExistException, UserNotFoundException {
        User userAnonimuous = this.saveUserAnonimuous();
        this.userService.deleteUser(userAnonimuous.getUserId());
    }
    @Test
    void saveTwoSameUser() throws  UserException, UserAlreadyExistException, UserNotFoundException {
        this.saveUserAnonimuous();
        UserAlreadyExistException exception = assertThrows(
                UserAlreadyExistException.class,
                () -> {
                    this.saveUserAnonimuous();
                }
        );
        assertEquals("Ya existe un usuario con el email anonimous@gmail.com", exception.getMessage());
    }

    @Test
    void searchUserByIdThatNotExists() {
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> {
                    this.userService.getUserById(100);
                }
        );
        assertEquals("No se puede encontrar el usuario con el id 100", exception.getMessage());
    }

    @Test
    void searchUserByEmailThatNotExists() {
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> {
                    this.userService.getUserByEmail("pepito@gmail.com");
                }
        );
        assertEquals("No se puede encontrar el usuario pepito@gmail.com", exception.getMessage());
    }

    @Test
    void deleteUserThatNotExists() {
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> {
                    this.userService.deleteUser(100);
                }
        );
        assertEquals("No se puede encontrar el usuario con el id 100", exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        this.userService.deleteAllUsers();
    }

}
