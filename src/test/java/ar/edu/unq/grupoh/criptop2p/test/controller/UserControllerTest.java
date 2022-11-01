package ar.edu.unq.grupoh.criptop2p.test.controller;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import ar.edu.unq.grupoh.criptop2p.webservice.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

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
        UserRequest userAnonimous = UserRequest.builder()
                .name("Anonimous")
                .surname("Anonimous")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        this.userService.saveUser(userAnonimous);
    }

    @Test
    void getAllUsersFromSystem() {
        User[] response = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/users",
                User[].class);
        List<User> users = Arrays.stream(response).toList();
        assertEquals(2, users.size());

    }

    @Test
    void getUserWithEmail() {
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,null,User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("anonimous@gmail.com", response.getBody().getEmail());
    }

    @Test
    void searchUserByEmailThatNotExists() {
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/100", HttpMethod.GET,null,User.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void saveUser() {
        UserRequest user = UserRequest.builder()
                .name("Usuario")
                .surname("Usuario")
                .address("1234567891")
                .email("usuario@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        HttpEntity<UserRequest> body = new HttpEntity<>(user);
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users", HttpMethod.POST,body,User.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario", response.getBody().getName());
        assertEquals("Usuario", response.getBody().getLastname());
        assertEquals("usuario@gmail.com", response.getBody().getEmail());
    }

    @Test
    void saveUserWithDataWrong() {
        UserRequest user = UserRequest.builder()
                .name("Usuario")
                .surname("Usuario")
                .address("1234567891")
                .email("null")
                .password("123")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        HttpEntity<UserRequest> body = new HttpEntity<>(user);
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users", HttpMethod.POST,body,User.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveTwoUsersSame() {
        UserRequest user = UserRequest.builder()
                .name("Usuario")
                .surname("Usuario")
                .address("1234567891")
                .email("usuario@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        HttpEntity<UserRequest> body = new HttpEntity<>(user);
        this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users", HttpMethod.POST,body,User.class);
        UserRequest user2 = UserRequest.builder()
                .name("Usuario")
                .surname("Usuario")
                .address("1234567891")
                .email("usuario@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        body = new HttpEntity<>(user2);
        ResponseEntity<User> response2 = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users", HttpMethod.POST,body,User.class);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    void updateUserEmail() {
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,null,User.class);
        assertEquals("anonimous@gmail.com", response.getBody().getEmail());
        UserRequest user = UserRequest.builder()
                .name(response.getBody().getName())
                .surname(response.getBody().getLastname())
                .address(response.getBody().getAddress())
                .email("emailActualizado@gmail.com")
                .password(response.getBody().getPassword())
                .cvu(response.getBody().getCvu())
                .walletAddress(response.getBody().getAddressWallet())
                .build();
        HttpEntity<UserRequest> body = new HttpEntity<>(user);
        ResponseEntity<User> responseUpdate = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/"+ response.getBody().getUserId(), HttpMethod.PUT,body,User.class);
        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
        assertEquals("emailActualizado@gmail.com", responseUpdate.getBody().getEmail());

    }

    @Test
    void deleteUserWithEmailAnonimous() {
        ResponseEntity<User> user = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,null,User.class);
        ResponseEntity<String> responseDelete = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/"+ user.getBody().getUserId(),HttpMethod.DELETE, null , String.class);
        assertEquals(HttpStatus.ACCEPTED, responseDelete.getStatusCode());
    }

    @AfterEach
    void tearDown() {
        this.userService.deleteAllUsers();
    }
}
