package yappa;

import java.io.FileNotFoundException;
import java.io.IOException;
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

/**
 * Runs the Yappa task manager and coordinates user input, storage, and output.
 */

public class Yappa {

    private final Storage storage = new Storage("data/yappa.txt");
    private TaskList tasks = new TaskList();
    private Ui ui = new Ui();

    /**
     * Creates Yappa and loads previously saved tasks.
     */
    public Yappa() {
        loadTasks();
    }

    /**
     * Returns the greeting shown when the application starts.
     *
     * @return User-facing greeting.
     */
    public String getGreeting() {
        return ui.showGreeting();
    }

    /**
     * Adds a task, persists the updated list, and displays the result.
     *
     * @param task Task to add.
     */
    private String addTask(Task task) throws YappaException {
        tasks.add(task);
        saveTasks();
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Loads saved tasks, or displays an error if the storage file cannot be read.
     */
    private void loadTasks() {
        try {
            tasks = storage.loadTasks();
        } catch (FileNotFoundException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Saves the current task list and displays an error if fails.
     */
    private void saveTasks() throws YappaException {
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            throw new YappaException("Oh no! I couldn't save the updated task list: " + e.getMessage());
        }
    }

    /**
     * Reads and executes commands until the user exits or the input stream closes.
     */
    /**
     * Processes a user command and returns the corresponding response.
     *
     * @param input User command.
     * @return User-facing response.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.getCommandWord(input);

            switch (command) {
                case "bye":
                    return ui.showGoodbye();
                case "list":
                    return ui.showTaskList(tasks);
                case "mark": {
                    int taskIndex = Parser.parseIndex(input);
                    Task task = tasks.mark(taskIndex);
                    saveTasks();
                    return ui.showTaskMarked(task.getDescription());
                }
                case "unmark": {
                    int taskIndex = Parser.parseIndex(input);
                    Task task = tasks.unmark(taskIndex);
                    saveTasks();
                    return ui.showTaskUnmarked(task.getDescription());
                }
                case "todo": {
                    String description = Parser.parseTodo(input);
                    Task task = new Todo(description);
                    return addTask(task);
                }
                case "deadline": {
                    String[] taskParts = Parser.parseDeadline(input);
                    LocalDateTime dateTime = DateUtil.parseDateTime(taskParts[1]);
                    Task task = new Deadline(taskParts[0], dateTime);
                    return addTask(task);
                }
                case "event": {
                    String[] taskParts = Parser.parseEvent(input);
                    LocalDateTime startTime = DateUtil.parseDateTime(taskParts[1]);
                    LocalDateTime endTime = DateUtil.parseDateTime(taskParts[2]);
                    Task task = new Event(taskParts[0], startTime, endTime);
                    return addTask(task);
                }
                case "delete": {
                    int taskIndex = Parser.parseIndex(input);
                    Task task = tasks.remove(taskIndex);
                    saveTasks();
                    return ui.showTaskDeleted(task, tasks.size());
                }
                case "find": {
                    String searchQuery = Parser.parseFind(input);
                    TaskList matchedTasks = tasks.find(searchQuery);
                    return ui.showMatchingTasks(matchedTasks);
                }
                case "clear": {
                    tasks.clear();
                    saveTasks();
                    return ui.showTaskCleared();
                }
                default:
                    throw new YappaException("Oh no...sorry, I am not sure what you mean :(");
            }

        } catch (YappaException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Please give me a valid task number!";
        }
    }

}
