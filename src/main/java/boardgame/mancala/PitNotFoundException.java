package mancala;
import java.io.Serializable;
public class PitNotFoundException extends Exception{
    public PitNotFoundException(final String message) {
        super(message);
    }
}