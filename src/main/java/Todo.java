/**
 * Represents a todo task
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    public String toFileString() {
        return "T | " + (isDone ? "1" : "0")
                + " | " + description;
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
