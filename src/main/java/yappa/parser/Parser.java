package yappa.parser;

import yappa.exception.YappaException;

/**
 * Extracts commands and task details from raw user input.
 */
public class Parser {

    /**
     * Gets the command word from the user's input.
     *
     * @param input user input
     * @return command word in lowercase
     */
    public static String getCommandWord(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        return trimmedInput.split("\\s+", 2)[0].toLowerCase();
    }

    /**
     * Parses a one-based task number into a zero-based index.
     *
     * @param input user input
     * @return zero-based task index
     * @throws YappaException if a task number is missing or invalid
     */
    public static int parseIndex(String input) throws YappaException {
        String[] parts = input.trim().split("\\s+");

        if (parts.length < 2) {
            throw new YappaException("Please specify a task number!");
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new YappaException("Please give me a valid task number!");
        }
    }

    /**
     * Parses the description of a todo command.
     *
     * @param input user input
     * @return todo description
     * @throws YappaException if the description is empty
     */
    public static String parseTodo(String input) throws YappaException {
        String description = input.substring(4).trim();

        if (description.isEmpty()) {
            throw new YappaException(
                    "Oh no! Todo description must not be empty!");
        }

        return description;
    }

    /**
     * Parses a deadline command.
     *
     * @param input user input
     * @return description and deadline date/time
     * @throws YappaException if the command is invalid
     */
    public static String[] parseDeadline(String input)
            throws YappaException {

        String taskBody = input.substring(8).trim();

        if (!taskBody.contains(" /by ")) {
            throw new YappaException(
                    "Oh no! Please re-enter in this format: deadline <task> /by <date/time>");
        }

        String[] parts = taskBody.split(" /by ", 2);

        String description = parts[0].trim();
        String dateTime = parts[1].trim();

        if (description.isEmpty() || dateTime.isEmpty()) {
            throw new YappaException(
                    "Deadline description and date/time cannot be empty.");
        }

        return new String[]{description, dateTime};
    }

    /**
     * Parses an event command.
     *
     * @param input user input
     * @return description, start date/time, and end date/time
     * @throws YappaException if the command is invalid
     */
    public static String[] parseEvent(String input)
            throws YappaException {

        String taskBody = input.substring(5).trim();

        if (!taskBody.contains(" /from ")
                || !taskBody.contains(" /to ")) {
            throw new YappaException(
                    "Oh no! Please re-enter in this format: event <task> /from <start> /to <end>");
        }

        String[] parts = taskBody.split(" /from | /to ", 3);

        if (parts.length < 3) {
            throw new YappaException(
                    "Please provide both a start and end time.");
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new YappaException(
                    "Event description, start time, and end time "
                            + "cannot be empty.");
        }

        return new String[]{description, from, to};
    }
}
