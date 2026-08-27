package yappa.task;
/**
 * Represents a generic task managed by Yappa.
 * Base class for specific task types like Todo, Deadline, and Event.
 */
public abstract class Task {

    /** Description shown to the user. */
    protected String description;

    /** Whether this task has been completed. */
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     *
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a task with the given description and completion state.
     *
     * @param description task description
     * @param isDone whether the task is completed
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the task description.
     *
     * @return task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    private String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Formats the task's completion state and description for display.
     *
     * @return display representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Formats the task as a record suitable for persistent storage.
     *
     * @return storage representation of the task
     */
    public abstract String toFileString();
}
