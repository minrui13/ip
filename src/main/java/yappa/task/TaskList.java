package yappa.task;
import java.util.ArrayList;
import java.util.List;

import yappa.exception.YappaException;

import java.util.Iterator;

public class TaskList implements Iterable<Task> {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public Task mark(int taskIndex) throws YappaException {
        validateIndex(taskIndex);
        Task markedTask = tasks.get(taskIndex);
        markedTask.markAsDone();
        return markedTask;
    }

    public Task unmark(int taskIndex) throws YappaException {
        validateIndex(taskIndex);
        Task unmarkedTask = tasks.get(taskIndex);
        unmarkedTask.markAsUndone();
        return unmarkedTask;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int taskIndex) throws YappaException {
        validateIndex(taskIndex);
        return tasks.remove(taskIndex);
    }

    private void validateIndex(int index) throws YappaException {
        if (index < 0 || index >= tasks.size()) {
            throw new YappaException("Task number " + (index + 1) + " does not exist!");
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

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
            ;
        }

        return taskList.toString();
    }
}
