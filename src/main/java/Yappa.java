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
        if (tasks.size() == 0) {
            System.out.println("No tasks");
        }
        for (int i = 0; i < tasks.size(); i++) {
            String description = tasks.get(i).getDescription();
            String status = tasks.get(i).getStatusIcon();
            System.out.println("\t" + (i + 1) + ". " + "[" + status + "] " + description);
        }
        System.out.println(LINE);
    }

    private static void printMessage(String text) {
        System.out.println(LINE);
        System.out.println(text);
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
                } else {
                    tasks.add(new Task(input));
                    printMessage("\tadded: " + input);
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
