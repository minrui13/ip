package yappa;
import java.time.LocalDateTime;

import yappa.exception.YappaException;
import yappa.parser.Parser;
import yappa.storage.Storage;
import yappa.task.Deadline;
import yappa.task.Event;
import yappa.task.Task;
import yappa.task.TaskList;
import yappa.task.Todo;
import yappa.ui.Ui;
import yappa.util.DateUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Yappa {

    private static final Storage STORAGE = new Storage("data/yappa.txt");
    private static TaskList tasks = new TaskList();
    private static Ui ui = new Ui();

    public static void main(String[] args) {
        loadTasks();
        ui.showGreeting();
        handleUserInput();
        ui.showGoodbye();
    }

    private static void addTask(Task task) {
        tasks.add(task);
        saveTasks();
        ui.showTaskAdded(task, tasks.size());
    }

    private static void loadTasks() {
        try {
            tasks = STORAGE.loadTasks();
        } catch (FileNotFoundException e) {
            ui.showError("Oh no! Unable to load saved tasks.");

        }
    }

    private static void saveTasks() {
        try {
            STORAGE.saveTasks(tasks);
        } catch (IOException e) {
            ui.showError("oH no! I couldn't save the updated task list.");
            return;
        }
    }

    private static void handleUserInput() {
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                if (input == null) {
                    break;
                }

                String command = Parser.getCommandWord(input);

                if (command.equals("bye")) {
                    isExit = true;
                } else if (command.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (command.equals("mark")) {
                    int taskIndex = Parser.parseIndex(input);
                    Task task = tasks.mark(taskIndex);
                    saveTasks();
                    ui.showTaskMarked(task.getDescription());
                } else if (command.equals("unmark")) {
                    int taskIndex = Parser.parseIndex(input);
                    Task task = tasks.unmark(taskIndex);
                    saveTasks();
                    ui.showTaskUnmarked(task.getDescription());
                } else if (command.equals("todo")) {
                    String description = Parser.parseTodo(input);
                    Task task = new Todo(description);
                    addTask(task);
                } else if (command.equals("deadline")) {
                    String[] taskParts = Parser.parseDeadline(input);
                    LocalDateTime dateTime = DateUtil.parseDateTime(taskParts[1]);
                    Task task = new Deadline(taskParts[0], dateTime);
                    addTask(task);
                } else if (command.equals("event")) {
                    String[] taskParts = Parser.parseEvent(input);
                    LocalDateTime startTime = DateUtil.parseDateTime(taskParts[1]);
                    LocalDateTime endTime = DateUtil.parseDateTime(taskParts[2]);
                    Task task = new Event(taskParts[0], startTime, endTime);
                    addTask(task);
                } else if (command.equals("delete")) {
                    int taskIndex = Parser.parseIndex(input);
                    Task task = tasks.remove(taskIndex);
                    saveTasks();
                    ui.showTaskDeleted(task, tasks.size());
                } else {
                    throw new YappaException("Oh no...sorry, I am not sure what you mean :(");
                }
            } catch (YappaException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("Please give me a valid task number!");
            } catch (IOException e) {
                ui.showError("Oh no! Error reading input: " + e.getMessage());
                break;
            }
        }
    }

}
