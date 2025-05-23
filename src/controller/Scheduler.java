package controller;

import model.Task;
import model.TaskStatus;
import util.Logger;

import java.util.*;
import java.util.concurrent.*;

public class Scheduler {

    private final Map<String, Task> allTasks = new ConcurrentHashMap<>();
    private final Set<String> completed = ConcurrentHashMap.newKeySet();

    private final PriorityBlockingQueue<Task> queue =
            new PriorityBlockingQueue<>(100, Comparator.comparingInt(Task::getPriority).reversed());

    // Adiciona uma nova tarefa
    public void addTask(Task task) {
        synchronized (this) {
            allTasks.put(task.getId(), task);
    
            if (hasCircularDependency(task)) {
                Logger.log("Erro: dependência circular detectada ao adicionar a tarefa " + task.getId());
                allTasks.remove(task.getId());
                return;
            }
    
            task.setStatus(TaskStatus.PENDING);
            if (areDependenciesCompleted(task)) {
                queue.offer(task);
            }
        }
    }

    public Map<String, Set<String>> getDependencyGraph() {
        Map<String, Set<String>> graph = new HashMap<>();
        for (Task task : allTasks.values()) {
            graph.put(task.getId(), new HashSet<>(task.getDependencies()));
        }
        return graph;
    }

    public void forceRemoveTask(String id) {
        synchronized (this) {
            Task removed = allTasks.remove(id);
            if (removed != null) {
                queue.remove(removed);
            }
    
            for (Task task : allTasks.values()) {
                task.getDependencies().remove(id);
            }
        }
    }
    

    public synchronized void removeTask(String id) {
        Task removed = allTasks.remove(id);
        if (removed != null) {
            queue.remove(removed);
        }
    }

    private boolean areDependenciesCompleted(Task task) {
        for (String depId : task.getDependencies()) {
            if (!completed.contains(depId)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasCircularDependency(Task task) {
        Set<String> visited = new HashSet<>();
        return hasCycle(task.getId(), visited, new HashSet<>());
    }
    
    private boolean hasCycle(String current, Set<String> visited, Set<String> stack) {
        if (stack.contains(current)) return true;
        if (visited.contains(current)) return false;
    
        visited.add(current);
        stack.add(current);
    
        Task task = allTasks.get(current);
        if (task != null) {
            for (String depId : task.getDependencies()) {
                if (hasCycle(depId, visited, stack)) {
                    return true;
                }
            }
        }
    
        stack.remove(current);
        return false;
    }    

    public synchronized void executePendingTasks() {
        Logger.log("Executando tarefas pendentes...");
        while (!queue.isEmpty()) {
            Task task = queue.poll();
            if (task != null) {
                task.setStatus(TaskStatus.RUNNING);
                task.getAction().run();
                task.setStatus(TaskStatus.COMPLETED);
                completed.add(task.getId());

                updateReadyTasks();
            }
        }
    }

    public Task getNextTask() {
        try {
            return queue.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public synchronized void markTaskCompleted(Task task) {
        completed.add(task.getId());
        task.setStatus(TaskStatus.COMPLETED);
        updateReadyTasks();
    }

    private void updateReadyTasks() {
        for (Task task : allTasks.values()) {
            if (task.getStatus() == TaskStatus.PENDING && areDependenciesCompleted(task)) {
                if (!queue.contains(task)) {
                    queue.offer(task);
                }
            }
        }
    }

    public List<Task> listTasks() {
        return new ArrayList<>(allTasks.values());
    }

    public Map<String, Task> getAllTasks() {
        return allTasks;
    }
}
