import java.time.LocalDateTime;

/**
 * Represents a task with a deadline date/time constraint.
 */
public class Deadline extends Task {
    protected LocalDateTime date;

    public Deadline(String description, LocalDateTime date) {
        super(description);
        this.date = date;
    }

    public Deadline(String description, boolean isDone, LocalDateTime date) {
        super(description, isDone);
        this.date = date;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0")
                + " | " + description
                + " | " + DateUtil.toFileString(date);
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + DateUtil.toDisplayString(this.date) + ")";
    }
}
