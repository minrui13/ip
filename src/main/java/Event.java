import java.time.LocalDateTime;

/**
 * Represents a task that occurs within a specific time range.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) throws YappaException {
        super(description);
        validateDateTimes(from, to);
        this.from = from;
        this.to = to;
    }

    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) throws YappaException {
        super(description, isDone);
        validateDateTimes(from, to);
        this.from = from;
        this.to = to;
    }

    private static void validateDateTimes(LocalDateTime from, LocalDateTime to) throws YappaException {
        if (!to.isAfter(from)) {
            throw new YappaException("Event end time must be after its start time :(.");
        }
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0")
                + " | " + description
                + " | " + DateUtil.toFileString(from)
                + " | " + DateUtil.toFileString(to);
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + DateUtil.toDisplayString(this.from) + " to: "
                + DateUtil.toDisplayString(this.to) + ")";
    }
}
