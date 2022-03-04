package mk.ukim.finki.wpaud.service.impl;

import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.enumerations.Role;
import mk.ukim.finki.wpaud.model.exceptions.InvalidArgumentException;
import mk.ukim.finki.wpaud.model.exceptions.PasswordsDoNotMatch;
import mk.ukim.finki.wpaud.model.exceptions.UserAlreadyExists;
import mk.ukim.finki.wpaud.repository.impl.jpa.UserRepository;
import mk.ukim.finki.wpaud.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname,Role role) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentException();
        }
        if(!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatch();
        }

        if(userRepository.findByUsername(username).isPresent()){
            throw new UserAlreadyExists(username);
        }

        return userRepository.save(new User(username,passwordEncoder.encode(password),name,surname,role));

    }
}
