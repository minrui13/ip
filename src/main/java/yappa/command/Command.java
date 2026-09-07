package yappa.command;

import yappa.exception.YappaException;
import yappa.task.TaskList;
import yappa.ui.Ui;

/**
 * Represents a command that can be executed by Yappa.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks   Task list to operate on.
     * @param ui      User interface used to format responses.
     * @return Response produced by the command.
     * @throws YappaException If the command cannot be executed.
     */
    public abstract String execute(
            TaskList tasks, Ui ui)
            throws YappaException;

    /**
     * Returns whether the command changes the task list.
     *
     * @return False unless a command overrides this method.
     */
    public boolean modifiesTasks() {
        return false;
    }

    /**
     * Returns whether the command requests application exit.
     *
     * @return False unless a command overrides this method.
     */
    public boolean isExit() {
        return false;
    }
}
