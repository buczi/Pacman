package model.events;

public interface Running {
    /**
     * Initiate running behaviour for ghosts
     */
    void startRunning();

    /**
     * Return to normal behaviour for ghosts
     */
    void stopRunning();
}
