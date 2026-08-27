package yappa.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import yappa.exception.YappaException;

/**
 * Tests command parsing, including valid input variations and malformed commands.
 */
public class ParserTest {

    /**
     * Verifies that command words are trimmed and normalised to lowercase.
     */
    @Test
    public void getCommandWord_mixedCaseAndSurroundingWhitespace_returnsLowercaseCommand() {
        assertEquals("todo", Parser.getCommandWord("  ToDo finish homework  "));
        assertEquals("", Parser.getCommandWord("   "));
    }

    /**
     * Verifies conversion from a one-based task number to a zero-based index.
     *
     * @throws YappaException if valid input is unexpectedly rejected
     */
    @Test
    public void parseIndex_validOneBasedNumber_returnsZeroBasedIndex() throws YappaException {
        assertEquals(0, Parser.parseIndex("mark 1"));
        assertEquals(11, Parser.parseIndex("  delete   12  "));
    }

    /**
     * Verifies that missing and non-numeric task numbers are rejected.
     */
    @Test
    public void parseIndex_missingOrNonNumericNumber_throwsYappaException() {
        YappaException missingNumber = assertThrows(
                YappaException.class, () -> Parser.parseIndex("mark"));
        assertEquals("Please specify a task number!", missingNumber.getMessage());

        YappaException nonNumericNumber = assertThrows(
                YappaException.class, () -> Parser.parseIndex("mark first"));
        assertEquals("Please give me a valid task number!", nonNumericNumber.getMessage());
    }

    /**
     * Verifies that a todo description is extracted and trimmed.
     *
     * @throws YappaException if valid input is unexpectedly rejected
     */
    @Test
    public void parseTodo_validDescription_trimsAndReturnsDescription() throws YappaException {
        assertEquals("read a book", Parser.parseTodo("todo   read a book   "));
    }

    /**
     * Verifies that an empty todo description is rejected.
     */
    @Test
    public void parseTodo_emptyDescription_throwsYappaException() {
        assertThrows(YappaException.class, () -> Parser.parseTodo("todo   "));
    }

    /**
     * Verifies extraction of a deadline description and date-time.
     *
     * @throws YappaException if valid input is unexpectedly rejected
     */
    @Test
    public void parseDeadline_validCommand_returnsDescriptionAndDateTime() throws YappaException {
        assertArrayEquals(
                new String[]{"submit report", "02/12/2026 1800"},
                Parser.parseDeadline("deadline submit report /by 02/12/2026 1800"));
    }

    /**
     * Verifies that incomplete deadline commands are rejected.
     */
    @Test
    public void parseDeadline_missingDelimiterOrField_throwsYappaException() {
        assertThrows(YappaException.class,
                () -> Parser.parseDeadline("deadline submit report"));
        assertThrows(YappaException.class,
                () -> Parser.parseDeadline("deadline submit report /by "));
        assertThrows(YappaException.class,
                () -> Parser.parseDeadline("deadline  /by 02/12/2026 1800"));
    }

    /**
     * Verifies extraction of an event description, start, and end.
     *
     * @throws YappaException if valid input is unexpectedly rejected
     */
    @Test
    public void parseEvent_validCommand_returnsDescriptionStartAndEnd() throws YappaException {
        assertArrayEquals(
                new String[]{"project meeting", "02/12/2026 1400", "02/12/2026 1600"},
                Parser.parseEvent(
                        "event project meeting /from 02/12/2026 1400 /to 02/12/2026 1600"));
    }

    /**
     * Verifies that incomplete event commands are rejected.
     */
    @Test
    public void parseEvent_missingDelimiterOrField_throwsYappaException() {
        assertThrows(YappaException.class,
                () -> Parser.parseEvent("event meeting /from 02/12/2026 1400"));
        assertThrows(YappaException.class,
                () -> Parser.parseEvent("event meeting /to 02/12/2026 1600"));
        assertThrows(YappaException.class,
                () -> Parser.parseEvent("event meeting /from /to 02/12/2026 1600"));
    }
}
