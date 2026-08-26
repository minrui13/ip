import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final DateTimeFormatter DATETIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter DATETIME_OUTPUT_FORMATTER = DateTimeFormatter
            .ofPattern("MMM dd yyyy, h:mm a");

    private static final DateTimeFormatter DATETIME_STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmm");

    public static LocalDateTime parseDateTime(String input) throws YappaException {
        try {
            return LocalDateTime.parse(input.trim(), DATETIME_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new YappaException(
                    "Invalid date/time :(. Please use the format dd/MM/yyyy HHmm, "
                            + "e.g. 02/12/2019 1800");
        }
    }

    public static String toDisplayString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_OUTPUT_FORMATTER);
    }

    public static String toFileString(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_STORAGE_FORMAT);
    }
}
