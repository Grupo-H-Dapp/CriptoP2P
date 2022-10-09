package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping(value = "/{id}" , produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest) throws UserAlreadyExistException, UserException {
        return new ResponseEntity<>(userService.saveUser(userRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequest newUser,@PathVariable int id) throws UserNotFoundException {
        Optional<User> user = this.userService.getUserById(id);
        HttpStatus code = user.isPresent() ? HttpStatus.OK : HttpStatus.CREATED;
        User updateUser = this.userService.updateUser(newUser,id);
        return new ResponseEntity<>(updateUser,code);
    }

    //Devolver un mensaje lindo
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) throws UserNotFoundException {
        this.userService.deleteUser(id);
        return new ResponseEntity<>("El usuario con el id " + id + " a sido eliminado" ,HttpStatus.ACCEPTED);
    }
}
