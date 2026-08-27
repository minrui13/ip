package yappa.task;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task.
     *
     * @param description Task description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a todo task with the given completion state.
     *
     * @param description Task description.
     * @param isDone Whether the task is completed.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Formats this todo for file storage.
     *
     * @return Storage representation of this todo.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0")
                + " | " + description;
    }

    /**
     * Formats this todo for display.
     *
     * @return Display representation of this todo.
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
