package yappa.task;

import yappa.exception.YappaException;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task
     *
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a todo task with the given completion state.
     *
     * @param description task description
     * @param isDone      whether the task is completed
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Formats this todo for file storage.
     *
     * @return storage representation of this todo
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0")
                + " | " + description;
    }

    /**
     * Formats this todo for display.
     *
     * @return display representation of this todo
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
