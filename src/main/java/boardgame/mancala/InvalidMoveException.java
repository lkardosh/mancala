package mancala;
import java.io.Serializable;
public class InvalidMoveException extends Exception implements Serializable{
    public InvalidMoveException(final String message) {
        super(message);
    }
}