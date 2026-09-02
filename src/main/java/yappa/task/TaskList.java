package yappa.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yappa.exception.YappaException;

/**
 * Stores tasks and provides operations for managing them.
 */
public class TaskList implements Iterable<Task> {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing a defensive copy of the loaded tasks.
     *
     * @param tasks Tasks to copy into the list.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Marks the specified task as complete.
     *
     * @param taskIndex Zero-based task index.
     * @return Task that was marked.
     * @throws YappaException If the index does not identify a task.
     */
    public Task mark(int taskIndex) throws YappaException {
        validateIndex(taskIndex);
        Task markedTask = tasks.get(taskIndex);
        markedTask.markAsDone();
        return markedTask;
    }

    /**
     * Unmarks the specified task as complete.
     *
     * @param taskIndex Zero-based task index.
     * @return Task that was unmarked.
     * @throws YappaException If the index does not identify a task.
     */
    public Task unmark(int taskIndex) throws YappaException {
        validateIndex(taskIndex);
        Task unmarkedTask = tasks.get(taskIndex);
        unmarkedTask.markAsUndone();
        return unmarkedTask;
    }

    /**
     * Appends a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task.
     *
     * @param taskIndex Zero-based task index.
     * @return Removed task.
     * @throws YappaException If the index does not identify a task.
     */
    public Task remove(int taskIndex) throws YappaException {
        validateIndex(taskIndex);
        return tasks.remove(taskIndex);
    }

    /**
     * Finds tasks whose descriptions contain the specified search query.
     *
     * @param searchQuery keyword or text to search for in task descriptions
     * @return a list of tasks with descriptions containing the search query
     */
    public TaskList find(String searchQuery) {
        TaskList matchedTasks = new TaskList();

        for (Task task : tasks) {
            if (task.getDescription().contains(searchQuery)) {
                matchedTasks.add(task);
            }
        }

        return matchedTasks;
    }

    /**
     * Ensures that an index identifies an existing task.
     *
     * @param index Zero-based task index.
     * @throws YappaException If the index is outside the task list.
     */
    private void validateIndex(int index) throws YappaException {
        if (index < 0 || index >= tasks.size()) {
            throw new YappaException("Task number " + (index + 1) + " does not exist!");
        }
    }

    /**
     * Clears all tasks from the task list.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Returns an iterator over tasks in display order.
     *
     * @return Task iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

    /**
     * Returns a string representation of the task list.
     *
     * @return Display representation of the task list.
     */
    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "No tasks";
        }

        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskList
                    .append("\t")
                    .append(i + 1)
                    .append(".")
                    .append(tasks.get(i))
                    .append(System.lineSeparator());
        }

        return taskList.toString();
    }
}
