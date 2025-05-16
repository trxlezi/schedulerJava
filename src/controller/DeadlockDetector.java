package controller;

import util.Logger;

import java.util.*;

public class DeadlockDetector extends Thread {
    private final Scheduler scheduler;

    public DeadlockDetector(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                detectAndResolveDeadlock();
            } catch (InterruptedException e) {
                Logger.log("Detector de deadlock interrompido.");
                break;
            }
        }
    }

    private void detectAndResolveDeadlock() {
        Map<String, Set<String>> graph = scheduler.getDependencyGraph();
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();

        for (String taskId : graph.keySet()) {
            if (dfs(taskId, visited, recStack, graph)) {
                Logger.log("Deadlock detectado envolvendo tarefas: " + recStack);
                String toRemove = recStack.iterator().next();
                scheduler.forceRemoveTask(toRemove);
                Logger.log("Tarefa " + toRemove + " removida para resolver deadlock.");
                break;
            }
        }
    }

    private boolean dfs(String current, Set<String> visited, Set<String> recStack, Map<String, Set<String>> graph) {
        if (recStack.contains(current)) return true;
        if (visited.contains(current)) return false;

        visited.add(current);
        recStack.add(current);

        for (String neighbor : graph.getOrDefault(current, new HashSet<>())) {
            if (dfs(neighbor, visited, recStack, graph)) return true;
        }

        recStack.remove(current);
        return false;
    }
}
