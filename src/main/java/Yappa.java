import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Yappa {

    public static final String LINE = "____________________________________________________________";
    public static final String LOGO = "__   __                    \n"
            + "\\ \\ / /_ _ _ __  _ __  __ _ \n"
            + " \\ V / _` | '_ \\| '_ \\/ _` |\n"
            + "  | | (_| | |_) | |_) | (_| |\n"
            + "  |_|\\__,_| .__/| .__/ \\__,_|\n"
            + "          |_|   |_|          \n";

    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        printGreeting();
        inputAndEcho();
        printMessage("\t Catch you later :)!");
    }

    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println("Good " + getTimeOfDay() + "! I'm Yappa. Ready to yap and get stuff done!");
        System.out.println("What are we tackling today? Let's do this!");
        System.out.println(LINE);
    }

    private static void printList() {
        System.out.println(LINE);
        System.out.println("Here are your current tasks: ");
        if (tasks.size() == 0) {
            System.out.println("No tasks");
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    private static void printMessage(String text) {
        System.out.println(LINE);
        System.out.println(text);
        System.out.println(LINE);
    }

    private static void addTask(Task task) {
        tasks.add(task);
        System.out.println(LINE);
        System.out.println("Ok! I have added the task:");
        System.out.println("\t" + task);
        System.out.println("Now you have " + tasks.size() + (tasks.size() > 1 ? " tasks " : " task ") + "in the list");
        System.out.println(LINE);
    }

    public static String getTimeOfDay() {
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

    public static void inputAndEcho() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                input = input.trim();

                if (input.equalsIgnoreCase("bye")) {
                    break;
                }

                else if (input.equalsIgnoreCase("list")) {
                    printList();
                } else if (input.startsWith("mark ")) {
                    int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                    mark(taskIndex);
                } else if (input.startsWith("unmark ")) {
                    int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                    unmark(taskIndex);
                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    Task task = new Todo(description);
                    addTask(task);
                } else if (input.startsWith("deadline ")) {
                    String[] taskParts = input.substring(9).split(" /by ", 2);
                    Task task = new Deadline(taskParts[0], taskParts[1]);
                    addTask(task);
                } else if (input.startsWith("event ")) {
                    String[] taskParts = input.substring(6).split(" /from | /to ");
                    Task task = new Event(taskParts[0], taskParts[1], taskParts[2]);
                    addTask(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }

    private static void mark(int taskIndex) {
        if (taskIndex >= tasks.size()) {
            printMessage("Task does not exist");
        } else {
            tasks.get(taskIndex).markAsDone();
            printMessage("Ok! I've marked this task as completed: \n\t[X] " + tasks.get(taskIndex).getDescription());
        }
    }

    private static void unmark(int taskIndex) {
        if (taskIndex >= tasks.size()) {
            printMessage("Task does not exist");
        } else {
            tasks.get(taskIndex).markAsUndone();
            printMessage("Ok! I've unmarked this task as completed: \n\t[ ] " + tasks.get(taskIndex).getDescription());
        }
    }

}
