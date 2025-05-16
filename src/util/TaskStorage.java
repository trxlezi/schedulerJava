package util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class TaskStorage {
    private static final String FILE_PATH = "data/tasks.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTasks(Collection<Task> tasks) throws IOException {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        }
    }

    public static List<Task> loadTasks() throws IOException {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Task>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
