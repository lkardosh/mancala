package mancala;

import java.io.Serializable;

public class NoSuchPlayerException extends Exception implements Serializable{
    public NoSuchPlayerException(final String message) {
        super(message);
    }
}