package file.logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Logger {
    //For debug
    private final static boolean debug = true;
    private static int eventNumber = 0;
    public static FileOutputStream file = null;
    public static final String path;

    static {
        if (debug) {
            DateTimeFormatter dir = DateTimeFormatter.ofPattern("yyyy_MM_dd");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dirPath = new File("").getAbsolutePath() + "/src/log/" + dir.format(now);
            File dirFile = new File(dirPath);

            if (!dirFile.exists())
                dirFile.mkdir();
            path = (dirPath + "/appLog-" + dtf.format(now) + ".log");
            File some = new File(path);
            try {
                some.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void generateLog(String className, String message, boolean exception, LogException exceptionBody) {
        if(debug)
            if (exception)
                log("[" + eventNumber + "] " + "CLASS: " + className +
                        "\n" + "[" + eventNumber + "] " + "EVENT: " + message +
                        "\n" + "[" + eventNumber + "] " + "EXCEPTION: " + exceptionBody.getExceptionMessage());
            else
                log("[" + eventNumber + "] " + "CLASS: " + className +
                        "\n" + "[" + eventNumber + "] " + "EVENT: " + message + "\n");
    }

    //TODO throw some exception and in game timestamps
    public static void log(String message) {
        if (!debug || path == null)
            return;
        try {
            file = new FileOutputStream(path, true);
            file.write(message.getBytes());
            file.close();
            eventNumber += 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
