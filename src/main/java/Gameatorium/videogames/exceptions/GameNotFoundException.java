package Gameatorium.videogames.exceptions;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(Long gameId) {
        super("Game not found with id: " + gameId);

    }
    public GameNotFoundException(String message) {
            super("Game not found: " + message);
        }
    }
