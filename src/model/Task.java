package model;

import java.util.List;

public class Task implements Comparable<Task> {
    private String id;
    private int priority;
    private List<String> dependencies;
    private Runnable action;
    private TaskStatus status;

    public Task(String id, int priority, List<String> dependencies, Runnable action) {
        this.id = id;
        this.priority = priority;
        this.dependencies = dependencies;
        this.action = action;
        this.status = TaskStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public Runnable getAction() { 
        return action;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);
    }
}
