package yappa.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import yappa.task.Deadline;
import yappa.task.Event;
import yappa.task.TaskList;
import yappa.task.Todo;

/**
 * Tests persistence of each task type without touching the user's real data file.
 */
public class StorageTest {

    @TempDir
    Path tempDir;

    /**
     * Verifies that loading a missing file produces an empty task list.
     *
     * @throws Exception if storage access unexpectedly fails
     */
    @Test
    public void loadTasks_missingFile_returnsEmptyTaskList() throws Exception {
        Storage storage = new Storage(toWorkingDirectoryRelativePath(tempDir.resolve("missing.txt")));

        assertEquals(0, storage.loadTasks().size());
    }

    /**
     * Verifies that saving and loading preserve every supported task type.
     *
     * @throws Exception if storage access or task construction unexpectedly fails
     */
    @Test
    public void saveAndLoadTasks_allTaskTypes_preservesTaskData() throws Exception {
        LocalDateTime start = LocalDateTime.of(2026, 12, 2, 14, 0);
        LocalDateTime end = LocalDateTime.of(2026, 12, 2, 16, 0);
        TaskList originalTasks = new TaskList(List.of(
                new Todo("read book", true),
                new Deadline("submit report", false, end),
                new Event("project meeting", true, start, end)));
        Storage storage = new Storage(toWorkingDirectoryRelativePath(tempDir.resolve("nested/tasks.txt")));

        storage.saveTasks(originalTasks);
        TaskList loadedTasks = storage.loadTasks();

        assertEquals(3, loadedTasks.size());
        assertEquals(originalTasks.toString(), loadedTasks.toString());
    }

    /**
     * Converts a temporary absolute path to the relative path required by {@link Storage}.
     *
     * @param path absolute temporary path
     * @return path relative to the application's current working directory
     */
    private String toWorkingDirectoryRelativePath(Path path) {
        Path workingDirectory = Path.of("").toAbsolutePath();
        return workingDirectory.relativize(path.toAbsolutePath()).toString();
    }
}
