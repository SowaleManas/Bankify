package bankify;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class SecurityLogger {
    private static final String LOG_FILE = "security_logs.txt";

    public static void logEvent(String eventType, String details) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + " - " + eventType + ": " + details + "\n");
        } catch (IOException e) {
            System.out.println("❌ Error writing to log file: " + e.getMessage());
        }
    }
}
