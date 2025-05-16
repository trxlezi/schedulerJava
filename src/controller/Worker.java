package controller;

import model.Task;
import util.Logger;

public class Worker extends Thread {
    private final Scheduler scheduler;
    private final int workerId;

    public Worker(Scheduler scheduler, int id) {
        this.scheduler = scheduler;
        this.workerId = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = scheduler.getNextTask();
                if (task == null) {
                    Thread.sleep(500);
                    continue;
                }

                Logger.log("Worker " + workerId + " executando tarefa " + task.getId());
                Thread.sleep(2000);
                scheduler.markTaskCompleted(task);
                Logger.log("Worker " + workerId + " completou tarefa " + task.getId());
            } catch (InterruptedException e) {
                Logger.log("Worker " + workerId + " interrompido.");
                break;
            }
        }
    }
}
