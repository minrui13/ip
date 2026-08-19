/**
 * Represents a task with a deadline date/time constraint.
 */
public class Deadline extends Task {
    protected String date;

    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.date + ")";
    }
}
