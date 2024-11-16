package repository;

public class FileNotValidException extends Exception {
    public FileNotValidException(String message) {
        super(message);
    }

    // New constructor to accept both a message and a cause
    public FileNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
