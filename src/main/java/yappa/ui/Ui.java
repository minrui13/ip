package yappa.ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

import yappa.task.Task;
import yappa.task.TaskList;

/**
 * Reads console input and displays Yappa's messages to the user.
 */
public class Ui {

    private final String LINE = "____________________________________________________________";
    private final String LOGO = "__   __                    \n"
            + "\\ \\ / /_ _ _ __  _ __  __ _ \n"
            + " \\ V / _` | '_ \\| '_ \\/ _` |\n"
            + "  | | (_| | |_) | |_) | (_| |\n"
            + "  |_|\\__,_| .__/| .__/ \\__,_|\n"
            + "          |_|   |_|          \n";

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads the next command from standard input.
     *
     * @return command text, or {@code null} when the input stream is closed
     * @throws IOException if the command cannot be read
     */
    public String readCommand() throws IOException {
        return reader.readLine();
    }

    /**
     * Displays Yappa's logo and welcome message
     */
    public void showGreeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println("Good " + getTimeOfDay() + "! I'm Yappa. Ready to yap and get stuff done!");
        System.out.println("What are we tackling today? Let's do this!");
        System.out.println(LINE);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Catch you later :)!");
    }

    /**
     * Displays a message between horizontal separators.
     *
     * @param message message to display
     */
    private void showMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    /**
     * Displays all tasks in their current order.
     *
     * @param tasks tasks to display
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Here are your current tasks: ");
        System.out.println(tasks);
        System.out.println(LINE);
    }

    /**
     * Confirms that a task has been marked as completed.
     *
     * @param taskDescription description of the marked task
     */
    public void showTaskMarked(String taskDescription) {
        showMessage("Ok! I've marked this task as completed: \n\t[X] " + taskDescription);

    }

    /**
     * Confirms that a task has been unmarked as completed.
     *
     * @param taskDescription description of the unmarked task
     */
    public void showTaskUnmarked(String taskDescription) {
        showMessage("Ok! I've unmarked this task as completed: \n\t[ ] " + taskDescription);

    }

    /**
     * Displays an added task and the new number of tasks.
     *
     * @param task added task
     * @param taskCount number of tasks after the addition
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Ok! I have added the task:");
        System.out.println("\t" + task);
        System.out.println(
                "Now you have " + taskCount + (taskCount > 1 ? " tasks " : " task ") + "in the list");
        System.out.println(LINE);
    }

    /**
     * Displays a deleted task and the new number of tasks.
     *
     * @param task deleted task
     * @param taskCount number of tasks after the deletion
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Ok! I will remove this task:");
        System.out.println("\t" + task);
        System.out.println("Now you have " + taskCount
                + (taskCount > 1 ? " tasks " : " task ")
                + "in the list");
        System.out.println(LINE);
    }

    /**
     * Displays an error message between horizontal separators.
     *
     * @param message error message to display
     */
    public void showError(String message) {
        showMessage(message);
    }

    /**
     * Determines the greeting period from the current local time.
     *
     * @return {@code Morning}, {@code Afternoon}, or {@code Evening}
     */
    private String getTimeOfDay() {
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();

        String period;

        if (currentHour >= 12 && currentHour < 17) {
            period = "Afternoon";
        } else if (currentHour >= 17 && currentHour < 21) {
            period = "Evening";
        } else {
            period = "Morning";
        }

        return period;
    }

}
