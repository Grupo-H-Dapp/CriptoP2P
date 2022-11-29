package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserAllResponse;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import ar.edu.unq.grupoh.criptop2p.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    @LogExecutionTime
    public ResponseEntity<List<UserAllResponse>> allUsers() {
        List<User> users = userService.findAll();
        List<UserAllResponse> result = users.stream().map(UserAllResponse::new).toList();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{email}" , produces = "application/json")
    @LogExecutionTime
    public ResponseEntity<UserResponse> getUser(@PathVariable String email) throws UserNotFoundException {
        return ResponseEntity.ok(new UserResponse(userService.getUserByEmail(email)));
    }

    //Devolver un mensaje lindo
    @DeleteMapping(value = "/{id}")
    @LogExecutionTime
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws UserNotFoundException {
        this.userService.deleteUser(id);
        return new ResponseEntity<>("User " + id + " deleted successful" ,HttpStatus.ACCEPTED);
    }
}
