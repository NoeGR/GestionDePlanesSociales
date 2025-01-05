package Exceptions;

public class MissingArgumentException extends RuntimeException{
    public MissingArgumentException(String message) {
        super(message);
    }
}
