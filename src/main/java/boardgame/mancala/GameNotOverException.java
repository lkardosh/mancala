package mancala;
import java.io.Serializable;

public class GameNotOverException extends Exception implements Serializable{
    public GameNotOverException(final String message) {
        super(message);
    }
}