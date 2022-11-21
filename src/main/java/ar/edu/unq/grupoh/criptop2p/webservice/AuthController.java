package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.TokenResponse;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserLoginRequest;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import ar.edu.unq.grupoh.criptop2p.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @LogExecutionTime
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest userLoginDTO){
        TokenResponse tokenDTO = userService.login(userLoginDTO);
        if(tokenDTO == null)
           return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.OK).header("token", tokenDTO.token).body(tokenDTO);
    }

    @PostMapping("/register")
    @LogExecutionTime
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest userDto) {
        try {
            User user = userService.saveUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
