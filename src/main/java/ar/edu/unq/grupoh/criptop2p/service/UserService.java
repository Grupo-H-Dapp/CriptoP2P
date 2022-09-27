package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserRequest model) throws UserAlreadyExistException {
        Optional<User> userToAdd = userRepository.findByEmail(model.getEmail());
        if(userToAdd.isEmpty()){
            User newUser = User.build(0,model.getName(),model.getLastname(),model.getEmail(),model.getAddress(),model.getPassword(),
                    model.getCvu(),model.getAddressWallet(),0,0);
            return this.userRepository.save(newUser);
        }else{
            throw new UserAlreadyExistException(model.getEmail());
        }
    }
    public User updateUser(@Valid UserRequest user,int id) {
        return userRepository.findById(id).map(userFound -> {
            userFound.setName(user.getName());
            userFound.setLastname(user.getLastname());
            userFound.setEmail(user.getEmail());
            userFound.setAddress(user.getAddress());
            userFound.setPassword(user.getPassword());
            userFound.setCvu(user.getCvu());
            userFound.setAddressWallet(user.getAddressWallet());
            userFound.setAmountOperations(user.getAmountOperations());
            userFound.setPoints(user.getPoints());
            return userRepository.save(userFound);
        }).orElseGet(() -> {
            User newUser = new User(user.getName(),user.getLastname(),user.getEmail(),user.getAddress(),user.getPassword(),user.getCvu()
                    ,user.getAddressWallet(),user.getAmountOperations(), user.getPoints());
            return userRepository.save(newUser);
        });
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
        if(this.userRepository.findById(id).isEmpty()){
            throw new UserNotFoundException(id);
        }
        this.userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }
}
