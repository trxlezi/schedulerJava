package app;

import controller.Scheduler;
import view.CLI;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        CLI cli = new CLI(scheduler);
        cli.start();
    }
}
