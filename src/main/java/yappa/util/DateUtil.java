package yappa.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import yappa.exception.YappaException;

/**
 * Converts date-time values between user input, display, and storage formats.
 */
public class DateUtil {
    private static final DateTimeFormatter DATETIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter DATETIME_OUTPUT_FORMATTER = DateTimeFormatter
            .ofPattern("MMM dd yyyy, h:mm a");

    private static final DateTimeFormatter DATETIME_STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Parses a date-time entered by a user in {@code dd/MM/yyyy HHmm} format.
     *
     * @param input date-time text to parse
     * @return parsed date-time
     * @throws YappaException if the input does not follow the required format
     */
    public static LocalDateTime parseDateTime(String input) throws YappaException {
        try {
            return LocalDateTime.parse(input.trim(), DATETIME_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new YappaException(
                    "Oh no, invalid datetime!. Please use the format dd/MM/yyyy HHmm, "
                            + "e.g. 02/12/2019 1800");
        }
    }

    /**
     * Parses a date-time read from Yappa's storage format.
     *
     * @param input stored date-time text
     * @return parsed date-time
     * @throws YappaException if the stored value is invalid
     */
    public static LocalDateTime parseStorageDateTime(String input) throws YappaException {
        try {
            return LocalDateTime.parse(input.trim(), DATETIME_STORAGE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new YappaException("Oh no, corrupted date/time in storage file!:" + input);
        }
    }

    /**
     * Formats a date-time for display to the user.
     *
     * @param dateTime date-time to format
     * @return user-facing date-time text
     */
    public static String toDisplayString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_OUTPUT_FORMATTER);
    }

    /**
     * Formats a date-time for writing to the storage file.
     *
     * @param dateTime date-time to format
     * @return storage-compatible date-time text
     */
    public static String toFileString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_STORAGE_FORMAT);
    }
}
