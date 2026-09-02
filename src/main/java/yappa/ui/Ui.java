package yappa.ui;

import java.time.LocalTime;

import yappa.task.Task;
import yappa.task.TaskList;

/**
 * Generates Yappa's messages for the user interface.
 */
public class Ui {

    private static final String LOGO = "__   __                    \n"
            + "\\ \\ / /_ _ _ __  _ __  __ _ \n"
            + " \\ V / _` | '_ \\| '_ \\/ _` |\n"
            + "  | | (_| | |_) | |_) | (_| |\n"
            + "  |_|\\__,_| .__/| .__/ \\__,_|\n"
            + "          |_|   |_|          \n";

    /**
     * Returns Yappa's logo and welcome message.
     *
     * @return greeting message
     */
    public String showGreeting() {
        return LOGO
                + "\nGood " + getTimeOfDay()
                + "! I'm Yappa. Ready to yap and get stuff done!\n"
                + "What are we tackling today? Let's do this!";
    }

    /**
     * Returns the goodbye message.
     *
     * @return goodbye message
     */
    public String showGoodbye() {
        return "Catch you later :)!";
    }

    /**
     * Returns all current tasks with a standard header message.
     *
     * @param tasks task list to display
     * @return formatted task list message
     */
    public String showTaskList(TaskList tasks) {
        return showTasks("Here are your current tasks:", tasks);
    }

    /**
     * Returns tasks that match a search query.
     *
     * @param tasks filtered task list containing matching tasks
     * @return formatted matching tasks message
     */
    public String showMatchingTasks(TaskList tasks) {
        return showTasks("Here are the matching tasks:", tasks);
    }

    /**
     * Returns tasks together with the specified header message.
     *
     * @param message header message
     * @param tasks   tasks to display
     * @return formatted task message
     */
    private String showTasks(String message, TaskList tasks) {
        return message + "\n" + tasks;
    }

    /**
     * Returns a confirmation that a task has been marked as completed.
     *
     * @param taskDescription description of the marked task
     * @return task marked confirmation
     */
    public String showTaskMarked(String taskDescription) {
        return "Ok! I've marked this task as completed:\n"
                + "\t[X] " + taskDescription;
    }

    /**
     * Returns a confirmation that a task has been marked as not completed.
     *
     * @param taskDescription description of the unmarked task
     * @return task unmarked confirmation
     */
    public String showTaskUnmarked(String taskDescription) {
        return "Ok! I've marked this task as not completed:\n"
                + "\t[ ] " + taskDescription;
    }

    /**
     * Returns a confirmation that a task has been added.
     *
     * @param task      added task
     * @param taskCount number of tasks after the addition
     * @return task added confirmation
     */
    public String showTaskAdded(Task task, int taskCount) {
        return "Ok! I have added the task:\n"
                + "\t" + task + "\n"
                + "Now you have " + taskCount
                + (taskCount == 1 ? " task " : " tasks ")
                + "in the list.";
    }

    /**
     * Returns a confirmation that a task has been deleted.
     *
     * @param task      deleted task
     * @param taskCount number of tasks after the deletion
     * @return task deleted confirmation
     */
    public String showTaskDeleted(Task task, int taskCount) {
        return "Ok! I will remove this task:\n"
                + "\t" + task + "\n"
                + "Now you have " + taskCount
                + (taskCount == 1 ? " task " : " tasks ")
                + "in the list.";
    }


    /**
     * Determines the greeting period from the current local time.
     *
     * @return {@code Morning}, {@code Afternoon}, or {@code Evening}
     */
    private String getTimeOfDay() {
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();

        if (currentHour >= 12 && currentHour < 17) {
            return "Afternoon";
        } else if (currentHour >= 17 && currentHour < 21) {
            return "Evening";
        } else {
            return "Morning";
        }
    }
}