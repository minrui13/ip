package yappa.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import yappa.exception.YappaException;
import yappa.task.Deadline;
import yappa.task.Event;
import yappa.task.Task;
import yappa.task.TaskList;
import yappa.task.Todo;
import yappa.util.DateUtil;

/**
 * Loads and saves Yappa tasks in a line-based text file.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates storage rooted at the application's working directory.
     *
     * @param relativePath Path to the storage file, relative to the working directory.
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(".", relativePath);
    }

    /**
     * Loads all valid task records from the storage file.
     *
     * <p>If the file does not exist, an empty task list is returned. Invalid records
     * are reported and skipped so that remaining tasks can still be loaded.</p>
     *
     * @return Tasks loaded from storage.
     * @throws FileNotFoundException If an existing storage file cannot be opened.
     */
    public TaskList loadTasks() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File file = filePath.toFile();

        if (!file.exists()) {
            return new TaskList(tasks);
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String taskString = scanner.nextLine().trim();

                if (taskString.isEmpty()) {
                    continue;
                }

                try {
                    tasks.add(parseTask(taskString));
                } catch (YappaException e) {
                    System.out.println(
                            "Task cannot be loaded: " + e.getMessage());
                }
            }
        }

        return new TaskList(tasks);
    }

    /**
     * Converts one storage record into its corresponding task subtype.
     *
     * @param taskString Storage record to parse.
     * @return Parsed task.
     * @throws YappaException If a stored date-time or event range is invalid.
     */
    private Task parseTask(String taskString) throws YappaException {
        String[] taskParts = taskString.split(" \\| ");

        String taskType = taskParts[0];
        boolean isDone = taskParts[1].equals("1");
        String description = taskParts[2];

        switch (taskType) {
            case "T":
                return new Todo(description, isDone);

            case "D":
                LocalDateTime date = DateUtil.parseStorageDateTime(taskParts[3]);
                return new Deadline(description, isDone, date);

            case "E":
                LocalDateTime from = DateUtil.parseStorageDateTime(taskParts[3]);
                LocalDateTime to = DateUtil.parseStorageDateTime(taskParts[4]);
                return new Event(description, isDone, from, to);

            default:
                throw new IllegalArgumentException(
                        "Unknown task type: " + taskType);
        }
    }

    /**
     * Saves all tasks in the task list to the storage file.
     *
     * @param tasks Tasks to save in iteration order.
     * @throws IOException If the directory or storage file cannot be written.
     */
    public void saveTasks(TaskList tasks) throws IOException {
        File file = filePath.toFile();

        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Task task : tasks) {
                fileWriter.write(task.toFileString()
                        + System.lineSeparator());
            }
        }
    }
}
