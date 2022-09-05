package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserRequest model) {
        User newUser = User.build(0,model.getName(),model.getLastname(),model.getEmail(),model.getAddress(),model.getPassword(),
                            model.getCvu(),model.getAddressWallet());
        return this.userRepository.save(newUser);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException {
        User user= userRepository.findByUserId(id);
        if(user!=null){
            return user;
        }else{
            throw new UserNotFoundException("user not found with id : "+id);
        }
    }
}
