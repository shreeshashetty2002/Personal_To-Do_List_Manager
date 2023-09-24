package ei;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TaskList {
    private List<Task> tasks;
    private Stack<List<Task>> history;

    public TaskList() {
        tasks = new ArrayList<>();
        history = new Stack<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveState();
    }

    public void markTaskCompleted(String description) {
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                task.markCompleted();
                saveState();
                return;
            }
        }
    }

    public void deleteTask(String description) {
        tasks.removeIf(task -> task.getDescription().equals(description));
        saveState();
    }

    public List<Task> viewTasks(String filter) {
        List<Task> filteredTasks = new ArrayList<>();
        switch (filter) {
            case "Show completed":
                filteredTasks = tasks.stream()
                        .filter(Task::isCompleted)
                        .toList();
                break;
            case "Show pending":
                filteredTasks = tasks.stream()
                        .filter(task -> !task.isCompleted())
                        .toList();
                break;
            case "Show all":
            default:
                filteredTasks = tasks;
                break;
        }
        return filteredTasks;
    }

    private void saveState() {
        history.push(new ArrayList<>(tasks));
    }

    public void undo() {
        if (!history.isEmpty()) {
            tasks = history.pop();
        }
    }
}
