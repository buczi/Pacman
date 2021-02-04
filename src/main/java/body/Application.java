package body;

import body.event.Reset;
import body.event.Restart;
import exception.CriticalException;
import file.FileHandler;
import file.Settings;

import static file.logger.Logger.*;

import gui.Frame;
import model.Map;
import model.objects.ghosts.Ghost;
import model.thread.Manager;


import java.awt.event.WindowEvent;
import java.io.File;

import static java.lang.Thread.sleep;

/**
 * Main body of application
 */
public class Application implements Restart {
    private final Settings settings;
    private String[] fileContent;
    private final String filePath;
    private Frame frame;
    private Map map;

    public Application(String filePath) throws CriticalException {

        settings = FileHandler.loadSettings(    new File("")
                .getAbsolutePath() + "/src/main/resources/difficulty/easy.json");
        this.filePath = filePath;
        fileContent = FileHandler.fileLoader(this.filePath);
        log("Start\n");
        frame = new Frame(this);

        map = new Map.Builder()
                .withMapFile(fileContent)
                .onFrame(frame)
                .withSettings(settings)
                .build();

        frame.init(map);
        try{
            sleep((long)settings.getTime());
        }
        catch (InterruptedException exception){
            System.out.println("Nothing to worry about. Ghosts will have small advantage in this round");
        }

        map.getPacman().initLives();
        Manager manager = new Manager(map);
        frame.initEvent(manager);

    }

    public static void main(String[] args) {
        try{
            new Application(new File("").getAbsolutePath() +
                    "/src/main/resources/maps/2.txt");
        }
        catch (CriticalException exception){
            System.out.println(exception.getCriticalExceptionTrace());
        }
    }

    /**
     * Restart the game after player interaction with reset button
     */
    @Override
    public void restart() {
        try{
            Reset.killSwitch();
            sleep(100);
            Reset.killSwitch();

            fileContent = FileHandler.fileLoader(filePath);
            log("Restart\n");
            frame.dispose();
            System.gc();
            frame = new Frame(this);
            map = new Map.Builder()
                    .withMapFile(fileContent)
                    .onFrame(frame)
                    .withSettings(settings)
                    .build();

            frame.init(map);
            map.getPacman().initLives();
            Manager manager = new Manager(map);
            frame.initEvent(manager);
        }
        catch (CriticalException exception ){
            System.out.println(exception.getCriticalExceptionTrace());
            frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSED));
        }
        catch (InterruptedException exception){
            frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSED));
        }

    }

    /**
     * Stops the game after pressing space
     */
    @Override
    public void pause() {
        map.getPacman().turnActive();
        map.getGhosts().forEach(Ghost::turnActive);
    }
}