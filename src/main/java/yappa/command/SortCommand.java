package yappa.command;

import yappa.task.TaskList;
import yappa.ui.Ui;

/** Sorts tasks alphabetically by their descriptions. */
public class SortCommand extends Command {

    /**
     * Sorts the task list and formats the result.
     *
     * @param tasks Task list to sort.
     * @param ui User interface used to format the response.
     * @return Formatted sorting response.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) {
        tasks.sort();
        return ui.showTasksSorted();
    }

    /**
     * Returns whether this command modifies the task list.
     *
     * @return True because the command sorts the task list.
     */
    @Override
    public boolean modifiesTasks() {
        return true;
    }
}
