/**
 * Exception to be thrown when user input is not valid
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
