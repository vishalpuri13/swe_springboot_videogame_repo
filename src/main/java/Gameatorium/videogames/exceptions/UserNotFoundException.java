package Gameatorium.videogames.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }

    public UserNotFoundException(String message) {
        super("User not found with: " + message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super("User not found with: " + message, cause);
    }
}
