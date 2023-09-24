package ei;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToDoListApp {
    private static final Logger LOGGER = Logger.getLogger(ToDoListApp.class.getName());
    private static final TaskList taskList = new TaskList();

    public static void main(String[] args) {
        configureLogging();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    markTaskCompleted(scanner);
                    break;
                case 3:
                    deleteTask(scanner);
                    break;
                case 4:
                    viewTasks(scanner);
                    break;
                case 5:
                    taskList.undo();
                    break;
                case 6:
                    LOGGER.info("Exiting To-Do List Manager. Goodbye!");
                    System.exit(0);
                default:
                    LOGGER.warning("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void configureLogging() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        LOGGER.setLevel(Level.INFO);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        LOGGER.addHandler(consoleHandler);
    }

    private static void printMenu() {
        LOGGER.info("===== To-Do List Manager =====");
        LOGGER.info("1. Add Task");
        LOGGER.info("2. Mark Completed");
        LOGGER.info("3. Delete Task");
        LOGGER.info("4. View Tasks");
        LOGGER.info("5. Undo");
        LOGGER.info("6. Exit");
        LOGGER.info("Enter your choice: ");
    }

    private static int getChoice(Scanner scanner) {
        int choice = -1;
        while (choice < 1 || choice > 6) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                scanner.nextLine(); // Consume invalid input
            }
            if (choice < 1 || choice > 6) {
                LOGGER.warning("Invalid choice. Please try again.");
            }
        }
        return choice;
    }

    private static void addTask(Scanner scanner) {
        LOGGER.info("Enter task description: ");
        String description = scanner.nextLine();
        LOGGER.info("Enter due date (yyyy-MM-dd): ");
        String dueDateStr = scanner.nextLine();

        try {
            LocalDate dueDate = LocalDate.parse(dueDateStr);
            Task task = new Task.TaskBuilder(description)
                    .dueDate(dueDate)
                    .build();
            taskList.addTask(task);
            LOGGER.info("Task added successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding task: " + e.getMessage());
        }
    }

    private static void markTaskCompleted(Scanner scanner) {
        LOGGER.info("Enter task description to mark as completed: ");
        String completedTask = scanner.nextLine();
        taskList.markTaskCompleted(completedTask);
        LOGGER.info("Task marked as completed.");
    }

    private static void deleteTask(Scanner scanner) {
        LOGGER.info("Enter task description to delete: ");
        String taskToDelete = scanner.nextLine();
        taskList.deleteTask(taskToDelete);
        LOGGER.info("Task deleted.");
    }

    private static void viewTasks(Scanner scanner) {
        LOGGER.info("View Tasks:");
        LOGGER.info("1. Show all");
        LOGGER.info("2. Show completed");
        LOGGER.info("3. Show pending");
        LOGGER.info("Enter your choice: ");
        int viewChoice = getChoice(scanner);
        String filter = "";
        switch (viewChoice) {
            case 1:
                filter = "Show all";
                break;
            case 2:
                filter = "Show completed";
                break;
            case 3:
                filter = "Show pending";
                break;
        }
        List<Task> tasks = taskList.viewTasks(filter);
        for (Task t : tasks) {
            LOGGER.info(t.getDescription() + " - " +
                    (t.isCompleted() ? "Completed" : "Pending") +
                    (t.getDueDate() != null ? ", Due: " + t.getDueDate() : ""));
        }
    }
}
