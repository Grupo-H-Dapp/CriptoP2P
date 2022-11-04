package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
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

    public User saveUser(UserRequest model) throws UserAlreadyExistException, UserException {
        Optional<User> userToAdd = userRepository.findByEmail(model.getEmail());
        if(userToAdd.isEmpty()){
            return this.userRepository.save(User.build(model));
        }else{
            throw new UserAlreadyExistException(model.getEmail());
        }
    }
    public User updateUser(@Valid UserRequest user,int id) {
        return userRepository.findById(id).map(userFound -> {
            try {
                userFound.setName(user.getName());
                userFound.setLastname(user.getLastname());
                userFound.setEmail(user.getEmail());
                userFound.setAddress(user.getAddress());
                userFound.setPassword(user.getPassword());
                userFound.setCvu(user.getCvu());
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
            return userRepository.save(userFound);
        }).orElseGet(() -> {
            try {
                return userRepository.save(User.build(user));
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
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

    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(email);
        }
    }

    public User getUserById(int id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }
}
