/**
 * Represents application-specific exceptions thrown by the Yappa application
 * when encountering invalid user inputs or execution errors.
 */
public class YappaException extends Exception {
    public YappaException(String message) {
        super(message);
    }
}