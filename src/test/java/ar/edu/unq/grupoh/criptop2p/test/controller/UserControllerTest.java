package ar.edu.unq.grupoh.criptop2p.test.controller;

import ar.edu.unq.grupoh.criptop2p.JWTHeader;
import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends JWTHeader {

    @LocalServerPort
    private int port;

    @Autowired
    private ar.edu.unq.grupoh.criptop2p.webservice.UserController userController;

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
        this.token = this.generateUserAndAuthenticated(HTTP_LOCALHOST,this.port);
    }

    @Test
    void getAllUsersFromSystem() {
        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> body = new HttpEntity<String>("", headers);
        ResponseEntity<User[]> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users",HttpMethod.GET,
                body, User[].class);
        List<User> users = Arrays.stream(response.getBody()).toList();
        assertEquals(2, users.size());

    }

    @Test
    void getUserWithEmail() {
        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> body = new HttpEntity<String>("", headers);
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,body,User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("anonimous@gmail.com", response.getBody().getEmail());
    }

    @Test
    void searchUserByEmailThatNotExists() {
        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> body = new HttpEntity<String>("", headers);
        ResponseEntity<User> response = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/100", HttpMethod.GET,body,User.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUserWithEmailAnonimous() {
        HttpHeaders headers = this.generateHeaderWithToken();
        HttpEntity<String> body = new HttpEntity<String>("", headers);
        ResponseEntity<UserResponse> user = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/anonimous@gmail.com", HttpMethod.GET,body, UserResponse.class);
        ResponseEntity<String> responseDelete = this.restTemplate.exchange(HTTP_LOCALHOST + port + "/users/"+ Objects.requireNonNull(user.getBody()).getId(),HttpMethod.DELETE, body , String.class);
        assertEquals(HttpStatus.ACCEPTED, responseDelete.getStatusCode());
    }

    @AfterEach
    void tearDown() {
        this.userService.deleteAllUsers();
    }
}
