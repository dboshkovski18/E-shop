package mk.ukim.finki.wpaud.service.impl;

import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.exceptions.InvalidArgumentException;
import mk.ukim.finki.wpaud.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wpaud.model.exceptions.PasswordsDoNotMatch;
import mk.ukim.finki.wpaud.model.exceptions.UserAlreadyExists;
import mk.ukim.finki.wpaud.repository.impl.InMemoeryUserRepository;
import mk.ukim.finki.wpaud.repository.impl.jpa.UserRepository;
import mk.ukim.finki.wpaud.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentException();
        }
        return userRepository.findByUsernameAndPassword(username,password).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        return null;
    }
//
//    @Override
//    public User register(String username, String password, String repeatPassword, String name, String surname) {
//        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
//            throw new InvalidArgumentException();
//        }
//        if(!password.equals(repeatPassword)){
//            throw new PasswordsDoNotMatch();
//        }
//
//        if(userRepository.findByUsername(username).isPresent() ||
//           !userRepository.findByUsername(username).isEmpty()){
//            throw new UserAlreadyExists(username);
//        }
//
//        return userRepository.save(new User(username,password,name,surname));
//
//    }
}
