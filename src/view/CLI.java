    package view;

    import model.Task;
    import controller.Scheduler;
    import util.Logger;

    import java.util.*;

    public class CLI {
        private final Scheduler scheduler;
        private final Scanner scanner = new Scanner(System.in);

        public CLI(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        public void start() {
            Logger.log("Bem-vindo ao Agendador de Tarefas!");
            boolean running = true;
        
            while (running) {
                System.out.println("\nComandos disponíveis: add, remove, list, run, exit");
                System.out.print("Digite um comando: ");
                String input = scanner.nextLine().trim();
        
                switch (input.toLowerCase()) {
                    case "add" -> addTask();
                    case "remove" -> removeTask();
                    case "list" -> listTasks();
                    case "run" -> runTasks();
                    case "exit" -> {
                        running = false;
                        Logger.log("Encerrando...");
                    }
                    default -> System.out.println("Comando inválido.");
                }
            }
            scanner.close();
            System.exit(0);
        }
        
        private void runTasks() {
            scheduler.executePendingTasks();
        }

        private void addTask() {
            System.out.print("ID da tarefa: ");
            String id = scanner.nextLine().trim();

            System.out.print("Prioridade (quanto maior, mais urgente): ");
            int priority = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("IDs das dependências (separados por vírgula, ou enter se nenhuma): ");
            String depsLine = scanner.nextLine().trim();
            List<String> dependencies = depsLine.isEmpty() ? List.of() :
                    Arrays.stream(depsLine.split(","))
                            .map(String::trim)
                            .toList();

            Task task = new Task(id, priority, dependencies, () -> Logger.log("Executando " + id));
            scheduler.addTask(task);
            Logger.log("Tarefa " + id + " adicionada.");

        }

        private void removeTask() {
            System.out.print("ID da tarefa a remover: ");
            String id = scanner.nextLine().trim();
            scheduler.removeTask(id);
            Logger.log("Tarefa " + id + " removida.");
        }

        private void listTasks() {
            List<Task> tasks = scheduler.listTasks();
            if (tasks.isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada.");
            } else {
                System.out.println("Tarefas ativas:");
                for (Task task : tasks) {
                    System.out.println("- " + task.getId() + " (prioridade " + task.getPriority() + ", status " + task.getStatus() + ")");
                }
            }
        }        
    }
