import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final Path filePath;

    public Storage(String relativePath) {
        this.filePath = Paths.get(".", relativePath);
    }

    public List<Task> loadTasks() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File file = filePath.toFile();

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String taskString = scanner.nextLine().trim();

                if (taskString.isEmpty()) {
                    continue;
                }

                Task task = parseTask(taskString);
                tasks.add(task);
            }
        }

        return tasks;
    }

    private Task parseTask(String taskString) {
        String[] taskParts = taskString.split(" \\| ");

        String taskType = taskParts[0];
        boolean isDone = taskParts[1].equals("1");
        String description = taskParts[2];

        switch (taskType) {
            case "T":
                return new Todo(description, isDone);

            case "D":
                String date = taskParts[3];
                return new Deadline(description, isDone, date);

            case "E":
                String from = taskParts[3];
                String to = taskParts[4];
                return new Event(description, isDone, from, to);

            default:
                throw new IllegalArgumentException(
                        "Unknown task type: " + taskType);
        }
    }

    public void saveTasks(List<Task> tasks) throws IOException {
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
