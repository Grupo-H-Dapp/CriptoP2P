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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
    void getUserWithId1() {
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/1", HttpMethod.GET,null,User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getUserId());
    }

    @Test
    void searchUserByIdThatNotExists() {
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/100", HttpMethod.GET,null,User.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @AfterEach
    void tearDown() {
        this.userService.deleteAllUsers();
    }
}
