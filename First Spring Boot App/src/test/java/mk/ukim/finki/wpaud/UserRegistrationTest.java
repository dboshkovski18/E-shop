package mk.ukim.finki.wpaud;

import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.enumerations.Role;
import mk.ukim.finki.wpaud.model.exceptions.InvalidArgumentException;
import mk.ukim.finki.wpaud.model.exceptions.PasswordsDoNotMatch;
import mk.ukim.finki.wpaud.model.exceptions.UserAlreadyExists;
import mk.ukim.finki.wpaud.repository.impl.jpa.UserRepository;
import mk.ukim.finki.wpaud.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl service;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);

        User user = new User("username","password","name","surname", Role.ROLE_USER);

        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        service = Mockito.spy(new UserServiceImpl(this.userRepository,this.passwordEncoder));
    }


    @Test
    public void testSuccessRegister(){
            User user= this.service.register("username","password","password","name","surname",Role.ROLE_USER);

            Mockito.verify(this.service).register("username","password","password","name","surname",Role.ROLE_USER);

        Assert.assertNotNull("User is null",user);

        Assert.assertEquals("name do not match","name",user.getName());
        Assert.assertEquals("password do not match","surname",user.getSurname());
        Assert.assertEquals("surname do not match","password",user.getPassword());
        Assert.assertEquals("role do not match","username",user.getUsername());


    }

    @Test
    public void testNullUsername(){
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentException.class,
                ()->service.register(null,"password","password","name","surname",Role.ROLE_USER));
        Mockito.verify(this.service).register(null,"password","password","name","surname",Role.ROLE_USER);
    }

    @Test
    public void testPasswordNull(){
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentException.class,
                ()->service.register("username",null,"password","name","surname",Role.ROLE_USER));
        Mockito.verify(this.service).register("username",null,"password","name","surname",Role.ROLE_USER);
    }

    @Test
    public void testPasswordEmpty(){
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentException.class,
                ()->service.register("username","","password","name","surname",Role.ROLE_USER));
        Mockito.verify(this.service).register("username","","password","name","surname",Role.ROLE_USER);
    }


    @Test
    public void testPasswordsDoNotMatch(){
        Assert.assertThrows("PasswordDoNotMatch expected",
                PasswordsDoNotMatch.class,
                ()->service.register("username","password","otherpassword","name","surname",Role.ROLE_USER));
        Mockito.verify(this.service).register("username","password","otherpassword","name","surname",Role.ROLE_USER);
    }


    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "surename", Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UserAlreadyExists.class,
                () -> this.service.register(username, "password", "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name", "surename", Role.ROLE_USER);
    }

}
