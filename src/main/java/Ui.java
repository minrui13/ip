import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

public class Ui {

    private final String LINE = "____________________________________________________________";
    private final String LOGO = "__   __                    \n"
            + "\\ \\ / /_ _ _ __  _ __  __ _ \n"
            + " \\ V / _` | '_ \\| '_ \\/ _` |\n"
            + "  | | (_| | |_) | |_) | (_| |\n"
            + "  |_|\\__,_| .__/| .__/ \\__,_|\n"
            + "          |_|   |_|          \n";

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readCommand() throws IOException {
        return reader.readLine();
    }

    public void showGreeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println("Good " + getTimeOfDay() + "! I'm Yappa. Ready to yap and get stuff done!");
        System.out.println("What are we tackling today? Let's do this!");
        System.out.println(LINE);
    }

    public void showGoodbye() {
        System.out.println("Catch you later :)!");
    }

    private void showMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    public void showTaskList(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Here are your current tasks: ");
        System.out.println(tasks);
        System.out.println(LINE);
    }

    public void showTaskMarked(String taskDescription) {
        showMessage("Ok! I've marked this task as completed: \n\t[X] " + taskDescription);

    }

    public void showTaskUnmarked(String taskDescription) {
        showMessage("Ok! I've unmarked this task as completed: \n\t[ ] " + taskDescription);

    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Ok! I have added the task:");
        System.out.println("\t" + task);
        System.out.println(
                "Now you have " + taskCount + (taskCount > 1 ? " tasks " : " task ") + "in the list");
        System.out.println(LINE);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Ok! I will remove this task:");
        System.out.println("\t" + task);
        System.out.println("Now you have " + taskCount
                + (taskCount > 1 ? " tasks " : " task ")
                + "in the list");
        System.out.println(LINE);
    }

    public void showError(String message) {
        showMessage(message);
    }

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
