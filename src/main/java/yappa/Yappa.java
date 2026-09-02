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
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Runs the Yappa task manager and coordinates user input, storage, and output.
 */

// Application instance by calling no argument constructior
// But if there is no other constructor in the class, there is no need to
// provide such a constructor
// If there is an existing constructor which takes arguments in that class, need
// to create an overloaded
// constructor with no arguments.

public class Yappa extends Application {

    private final Storage STORAGE = new Storage("data/yappa.txt");
    private TaskList tasks = new TaskList();
    private Ui ui = new Ui();

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    /**
     * Starts Yappa, loads saved tasks, and processes commands until the session
     * ends.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        loadTasks();

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        sendButton = new Button("Send");

        userInput.setOnAction((event) -> handleUserInput());
        sendButton.setOnAction((event) -> handleUserInput());

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout, 400, 400);

        String greetingMessage = ui.showGreeting();
        dialogContainer.getChildren().add(new Label("Yappa: " + greetingMessage));

        // Setting the stage to show our scene
        stage.setScene(scene);
        // Set title
        stage.setTitle("Yappa");
        // render the stage
        stage.show();
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
            tasks = STORAGE.loadTasks();
        } catch (FileNotFoundException e) {
            dialogContainer.getChildren().add(new Label("Yappa: " + e));
            tasks = new TaskList();
        }
    }

    /**
     * Saves the current task list and displays an error if fails.
     */
    private void saveTasks() throws YappaException {
        try {
            STORAGE.saveTasks(tasks);
        } catch (IOException e) {
            throw new YappaException("Oh no! I couldn't save the updated task list: " + e.getMessage());
        }
    }

    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }
        String response = getResponse(input);

        dialogContainer.getChildren().addAll(
                new Label("User: " + input),
                new Label("Yappa: " + response));

        userInput.clear();
    }

    private String handleExit() {

        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();

        return ui.showGoodbye();
    }

    /**
     * Reads and executes commands until the user exits or the input stream closes.
     */
    private String getResponse(String input) {

        if (input == null || input.isBlank()) {
            return handleExit();
        }
        try {
            String command = Parser.getCommandWord(input);

            switch (command) {
                case "bye":
                    return handleExit();
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
