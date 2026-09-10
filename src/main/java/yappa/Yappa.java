package yappa;

import yappa.exception.YappaException;
import yappa.parser.Parser;
import yappa.storage.Storage;
import yappa.task.TaskList;
import yappa.ui.Ui;
import yappa.command.Command;

/**
 * Runs the Yappa task manager and coordinates user input, storage, and output.
 */

public class Yappa {

    private final Storage storage = new Storage("data/yappa.txt");
    private TaskList tasks = new TaskList();
    private final Ui ui = new Ui();
    private boolean isExitRequested;

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
     * Loads saved tasks, or displays an error if the storage file cannot be read.
     */
    private void loadTasks() {
        try {
            tasks = storage.loadTasks();
        } catch (YappaException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Executes a user command and returns the corresponding response.
     *
     * @param input User command.
     * @return User-facing response.
     */
    public String getResponse(String input) {
        isExitRequested = false;

        try {
            Command command = Parser.parse(input);
            // Parser.parse returns a command for every successful parse.
            assert command != null;

            String response = command.execute(tasks, ui);

            if (command.modifiesTasks()) {
                storage.saveTasks(tasks);
            }

            isExitRequested = command.isExit();

            return response;
        } catch (YappaException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns whether the most recently executed command requested application exit.
     *
     * @return True when the application should exit.
     */
    public boolean isExitRequested() {
        return isExitRequested;
    }

}
