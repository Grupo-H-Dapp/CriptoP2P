package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserRequest model) throws UserAlreadyExistException {
        Optional<User> userToAdd = userRepository.findByEmail(model.getEmail());
        if(userToAdd.isPresent()){
            User newUser = User.build(0,model.getName(),model.getLastname(),model.getEmail(),model.getAddress(),model.getPassword(),
                    model.getCvu(),model.getAddressWallet(),0,0);
            return this.userRepository.save(newUser);
        }else{
            throw new UserAlreadyExistException(model.getEmail());
        }
    }

    public Optional<User> updateUser(User user , int id){
        return this.userRepository.findById(id);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException {
        User user= userRepository.findByUserId(id);
        if(user!=null){
            return user;
        }else{
            throw new UserNotFoundException(id);
        }
    }

    public void deleteUser(int id) throws UserNotFoundException {
        this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        this.userRepository.deleteById(id);
    }
}
