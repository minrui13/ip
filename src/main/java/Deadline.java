/**
 * Represents a task with a deadline date/time constraint.
 */
public class Deadline extends Task {
    protected String date;

    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    public Deadline(String description, boolean isDone, String date) {
        super(description, isDone);
        this.date = date;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0")
                + " | " + description
                + " | " + date;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.date + ")";
    }
}
