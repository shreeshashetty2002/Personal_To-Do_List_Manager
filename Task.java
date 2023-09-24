package ei;

import java.time.LocalDate;

public class Task {
    private String description;
    private LocalDate dueDate;
    private boolean completed;

    private Task(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    // Task Builder
    public static class TaskBuilder {
        private String description;
        private LocalDate dueDate;

        public TaskBuilder(String description) {
            this.description = description;
        }

        public TaskBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Task build() {
            return new Task(description, dueDate);
        }
    }
}
