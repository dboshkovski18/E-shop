package mk.ukim.finki.wpaud.model.exceptions;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String username){
        super(String.format("User with username %s already exists!",username));
    }
}
