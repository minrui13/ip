package yappa.task;

import java.time.LocalDateTime;

import yappa.exception.YappaException;
import yappa.util.DateUtil;

/**
 * Represents a task that occurs within a specific time range.
 */
public class Event extends Task {
    /** Date and time at which this event starts. */
    protected LocalDateTime from;

    /** Date and time at which this event ends. */
    protected LocalDateTime to;

    /**
     * Creates a new event, verifying the end is after its start.
     *
     * @param description task description
     * @param from        event start datetime
     * @param to          event end datetime
     * @throws YappaException if the event does not end after it starts
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) throws YappaException {
        super(description);
        validateDateTimes(from, to);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an event with the given completion state.
     *
     * @param description task description
     * @param isDone      whether the task is completed
     * @param from        event start date and time
     * @param to          event end date and time
     * @throws YappaException if the event does not end after it starts
     */
    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) throws YappaException {
        super(description, isDone);
        validateDateTimes(from, to);
        this.from = from;
        this.to = to;
    }

    /**
     * Ensures that an event has a positive duration.
     *
     * @param from event start date and time
     * @param to   event end date and time
     * @throws YappaException if {@code to} is not after {@code from}
     */
    private static void validateDateTimes(LocalDateTime from, LocalDateTime to) throws YappaException {
        if (!to.isAfter(from)) {
            throw new YappaException("Event end time must be after its start time :(.");
        }
    }

    /**
     * Formats this event file storage.
     *
     * @return storage representation of this event
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0")
                + " | " + description
                + " | " + DateUtil.toFileString(from)
                + " | " + DateUtil.toFileString(to);
    }

    /**
     * Formats this event for display.
     *
     * @return display representation of this event
     */
    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + DateUtil.toDisplayString(this.from) + " to: "
                + DateUtil.toDisplayString(this.to) + ")";
    }
}
