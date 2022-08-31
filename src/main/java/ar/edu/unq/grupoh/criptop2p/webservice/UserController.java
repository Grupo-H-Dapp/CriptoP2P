package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> allCars() {
        List<User> list = otroMetodo();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user){
        return ResponseEntity.ok().body(this.userService.save(user));
    }

    private List<User> otroMetodo() {
        return userService.findAll();
    }

}
