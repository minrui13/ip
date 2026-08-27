package yappa.exception;

/**
 * Represents application-specific exceptions thrown by the Yappa application
 * when encountering invalid user inputs or execution errors.
 */
public class YappaException extends Exception {
    /**
     * Creates an exception with a user-facing explanation.
     *
     * @param message Explanation of the error.
     */
    public YappaException(String message) {
        super(message);
    }
}
