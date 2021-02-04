package file.logger;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tracks down events in the code
 */
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
                if(!dirFile.mkdir())
                {
                    System.out.println("Make dir failed");
                    System.exit(-1);
                }

            path = (dirPath + "/appLog-" + dtf.format(now) + ".log");
            File some = new File(path);
            try {
                if(!some.createNewFile()){
                    System.out.println("Make file failed");
                    System.exit(-1);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Creates log after some type of event
     * @param className where log is coming from
     * @param message what happened
     * @param exception was it an exception
     * @param exceptionBody exception type
     */
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


    /**
     * Writes message to log file
     * @param message content of log
     */
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

    public static boolean getDebug(){
        return debug;
    }
}
