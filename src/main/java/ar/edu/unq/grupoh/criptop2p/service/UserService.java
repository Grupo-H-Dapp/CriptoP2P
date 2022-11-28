package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.TokenResponse;
import ar.edu.unq.grupoh.criptop2p.dto.response.UserLoginRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserAlreadyExistException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.UserRepository;
import ar.edu.unq.grupoh.criptop2p.security.JWTAuthorizationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public User saveUser(UserRequest model) throws UserAlreadyExistException, UserException {
        Optional<User> userToAdd = userRepository.findByEmail(model.getEmail());
        if(userToAdd.isEmpty()){
            return this.userRepository.save(User.build(model));
        }else{
            throw new UserAlreadyExistException(model.getEmail());
        }
    }
    @Transactional
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

    @Transactional
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public void deleteUser(int id) throws UserNotFoundException {
        if(this.userRepository.findById(id).isEmpty()){
            throw new UserNotFoundException(id);
        }
        this.userRepository.deleteById(id);
    }
    @Transactional
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(email);
        }
    }
    @Transactional
    public User getUserById(int id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public TokenResponse login(UserLoginRequest userLoginDTO){
        Optional<User> user = userRepository.findByEmail(userLoginDTO.getEmail());
        if(!user.isPresent()){return null;}
        //if (passwordEncoder.matches(userLoginDTO.getPassword(), user.get().getPassword())){
        if (Objects.equals(userLoginDTO.getPassword(), user.get().getPassword())){
            return new TokenResponse(JWTAuthorizationFilter.getJWTToken(userLoginDTO.getEmail()));
        }else{
            return null;
        }
    }
    @Transactional
    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }
}
