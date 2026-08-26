import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

    private static final Storage STORAGE = new Storage("data/yappa.txt");
    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
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

    private static void mark(int taskIndex) {
        tasks.get(taskIndex).markAsDone();
        saveTasks();
        printMessage("Ok! I've marked this task as completed: \n\t[X] " + tasks.get(taskIndex).getDescription());

    }

    private static void unmark(int taskIndex) {
        tasks.get(taskIndex).markAsUndone();
        saveTasks();
        printMessage("Ok! I've unmarked this task as completed: \n\t[ ] " + tasks.get(taskIndex).getDescription());
    }

    private static void addTask(Task task) {
        tasks.add(task);
        saveTasks();
        System.out.println(LINE);
        System.out.println("Ok! I have added the task:");
        System.out.println("\t" + task);
        System.out.println(
                "Now you have " + tasks.size() + (tasks.size() > 1 ? " tasks " : " task ") + "in the list");
        System.out.println(LINE);
    }

    /**
     * Removes the task at a previously validated zero-based index and reports the
     * updated list size.
     *
     * @param taskIndex zero-based index of the task to remove
     */
    private static void deleteTask(int taskIndex) {
        Task task = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        saveTasks();
        System.out.println(LINE);
        System.out.println("Ok! I will remove this task:");
        System.out.println("\t" + task);
        System.out.println("Now you have " + tasks.size()
                + (tasks.size() > 1 ? " tasks " : " task ")
                + "in the list");
        System.out.println(LINE);
    }

    private static void loadTasks() {
        try {
            tasks = STORAGE.loadTasks();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load saved tasks.");

        }
    }

    private static void saveTasks() {
        try {
            STORAGE.saveTasks(tasks);
        } catch (IOException e) {
            printMessage("Oops! I couldn't save the updated task list.");
            return;
        }
    }

    public static void inputAndEcho() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                input = input.trim();
                try {
                    if (input.equalsIgnoreCase("bye")) {
                        break;
                    } else if (input.equalsIgnoreCase("list")) {
                        printList();
                    } else if (input.startsWith("mark ") || input.equalsIgnoreCase("mark")) {
                        int taskIndex = parseIndex(input);
                        mark(taskIndex);
                    } else if (input.startsWith("unmark ") || input.equalsIgnoreCase("unmark")) {
                        int taskIndex = parseIndex(input);
                        unmark(taskIndex);
                    } else if (input.startsWith("todo")) {
                        String description = input.substring(4).trim();
                        if (description.isEmpty()) {
                            throw new YappaException("Todo description must not be empty :(. Yappa cannot add task.");
                        }
                        Task task = new Todo(description);
                        addTask(task);
                    } else if (input.startsWith("deadline")) {
                        String taskBody = input.substring(8).trim();
                        if (taskBody.isEmpty() || !taskBody.contains(" /by ")) {
                            throw new YappaException(
                                    "Invalid Deadline task. Please re-enter in this format: deadline <task> /by <date/time>");
                        }
                        String[] taskParts = taskBody.split(" /by ", 2);
                        if (taskParts[0].trim().isEmpty() || taskParts[1].trim().isEmpty()) {
                            throw new YappaException(
                                    "Both deadline description and /by date must not be empty :(. Yappa cannot add task.");
                        }
                        LocalDateTime deadlineDateTime = DateUtil.parseDateTime(taskParts[1].trim());
                        Task task = new Deadline(taskParts[0].trim(), deadlineDateTime);
                        addTask(task);
                    } else if (input.startsWith("event")) {
                        String taskBody = input.substring(5).trim();
                        if (taskBody.isEmpty() || !taskBody.contains(" /from ") || !taskBody.contains(" /to ")) {
                            throw new YappaException(
                                    "Invalid Event task. Please re-enter in this format: event <task> /from <start> /to <end>");
                        }
                        String[] taskParts = taskBody.split(" /from | /to ", 3);
                        if (taskParts.length < 3 || taskParts[0].trim().isEmpty() || taskParts[1].trim().isEmpty()
                                || taskParts[2].trim().isEmpty()) {
                            throw new YappaException(
                                    "Event description, /from, and /to fields must not be empty :(. Yappa cannot add task.");
                        }
                        LocalDateTime fromDateTime = DateUtil.parseDateTime(taskParts[1].trim());
                        LocalDateTime toDateTime = DateUtil.parseDateTime(taskParts[2].trim());
                        Task task = new Event(taskParts[0].trim(), fromDateTime, toDateTime);
                        addTask(task);
                    } else if (input.startsWith("delete ") || input.equalsIgnoreCase("delete")) {
                        int taskIndex = parseIndex(input);
                        deleteTask(taskIndex);
                    } else {
                        throw new YappaException("Oh no...sorry, I am not sure what you mean :(");
                    }
                } catch (YappaException e) {
                    printMessage(e.getMessage());
                } catch (NumberFormatException e) {
                    printMessage("Please give me a valid task number!");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }

    private static String getTimeOfDay() {
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

    private static int parseIndex(String input) throws YappaException {
        String[] parts = input.split("\\s+");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new YappaException("Please specify a task number!");
        }
        int index = Integer.parseInt(parts[1]) - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new YappaException("Task number " + (index + 1) + " does not exist!");
        }
        return index;
    }
}
