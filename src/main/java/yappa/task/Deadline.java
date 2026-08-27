package yappa.task;
import java.time.LocalDateTime;

import yappa.util.DateUtil;

/**
 * Represents a task with a deadline date/time constraint.
 */
public class Deadline extends Task {
    /** Date and time by which this task should be completed. */
    protected LocalDateTime date;

    /**
     * Creates a new deadline task.
     *
     * @param description task description
     * @param date deadline date and time
     */
    public Deadline(String description, LocalDateTime date) {
        super(description);
        this.date = date;
    }

    /**
     * Creates a deadline task with the known completion state.
     *
     * @param description task description
     * @param isDone whether the task is completed
     * @param date deadline date and time
     */
    public Deadline(String description, boolean isDone, LocalDateTime date) {
        super(description, isDone);
        this.date = date;
    }

    /**
     * Formats this deadline for file storage.
     *
     * @return storage representation of this deadline
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0")
                + " | " + description
                + " | " + DateUtil.toFileString(date);
    }

    /**
     * Formats this deadline for display.
     *
     * @return display representation of this deadline
     */
    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + DateUtil.toDisplayString(this.date) + ")";
    }
}
