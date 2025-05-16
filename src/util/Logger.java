package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final Object lock = new Object();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public static void log(String message) {
        synchronized(lock) {
            String timestamp = LocalDateTime.now().format(formatter);
            System.out.println("\n[" + timestamp + "] " + message);        }
    }
}