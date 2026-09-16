package yappa.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import yappa.exception.YappaException;

/**
 * Converts date-time values between user input, display, and storage formats.
 */
public class DateUtil {
    private static final DateTimeFormatter DATETIME_INPUT_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter DATETIME_OUTPUT_FORMATTER = DateTimeFormatter
            .ofPattern("MMM dd yyyy, h:mm a", Locale.ENGLISH);
    private static final DateTimeFormatter DATETIME_STORAGE_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter TIMESTAMP_LABEL_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private DateUtil() {
    }

    private static LocalDateTime parse(String input, DateTimeFormatter formatter, String errorMessage)
            throws YappaException {
        try {
            return LocalDateTime.parse(input.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new YappaException(errorMessage, e);
        }
    }

    /**
     * Parses a date-time entered by a user in {@code dd/MM/yyyy HHmm} format.
     *
     * @param input Date-time text to parse.
     * @return Parsed date-time.
     * @throws YappaException If the input does not follow the required format.
     */
    public static LocalDateTime parseDateTime(String input) throws YappaException {
        return parse(input, DATETIME_INPUT_FORMATTER,
                "Oh no, invalid datetime! Please use the format dd/MM/yyyy HHmm, e.g. 02/12/2019 1800");
    }

    /**
     * Parses a date-time read from Yappa's storage format.
     *
     * @param input Stored date-time text.
     * @return Parsed date-time.
     * @throws YappaException If the stored value is invalid.
     */
    public static LocalDateTime parseStorageDateTime(String input) throws YappaException {
        return parse(input, DATETIME_STORAGE_FORMAT, "Oh no, corrupted date/time in storage file!:" + input);
    }

    /**
     * Formats a date-time for display to the user.
     *
     * @param dateTime Date-time to format.
     * @return User-facing date-time text.
     */
    public static String toDisplayString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_OUTPUT_FORMATTER);
    }

    /**
     * Formats a date-time for writing to the storage file.
     *
     * @param dateTime Date-time to format.
     * @return Storage-compatible date-time text.
     */
    public static String toFileString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_STORAGE_FORMAT);
    }

    /**
     * Formats the current time for display under a chat bubble.
     *
     * @return Current time as {@code HH:mm}.
     */
    public static String nowAsTimeLabel() {
        return LocalTime.now().format(TIMESTAMP_LABEL_FORMATTER);
    }
}
