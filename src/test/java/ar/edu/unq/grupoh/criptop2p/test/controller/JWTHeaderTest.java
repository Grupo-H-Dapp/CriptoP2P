package ar.edu.unq.grupoh.criptop2p.test.controller;

import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.TokenResponse;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserLoginRequest;
import ar.edu.unq.grupoh.criptop2p.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JWTHeaderTest {
    public String generateUserAndAuthenticated(String URL, int port) {
        //Generated User
        RestTemplate server = new RestTemplate();
        UserRequest userRegisterRequest = UserRequest.builder()
                .name("Anonimous")
                .surname("Anonimous")
                .address("1234567891")
                .email("anonimous@gmail.com")
                .password("aAsadsadsad#")
                .cvu("1234567891234567891233")
                .walletAddress("12345678")
                .build();
        HttpEntity<UserRequest> userRegister = new HttpEntity<>(userRegisterRequest);
        server.exchange(URL + port + "/auth/register", HttpMethod.POST,userRegister,User.class);
        //Authenticated user
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("anonimous@gmail.com");
        userLoginRequest.setPassword("aAsadsadsad#");
        HttpEntity<UserLoginRequest> userLogin = new HttpEntity<>(userLoginRequest);
        ResponseEntity<TokenResponse> token = server.exchange(URL + port + "/auth/login", HttpMethod.POST,userRegister,TokenResponse.class);
        return token.getBody().token;
    }
}
