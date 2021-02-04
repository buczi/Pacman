package gui.events;

public interface Update {
    /**
     * Update points on graphical interface
     * @param points current amount of points
     */
    void updatePoints(int points);
    /**
     * Update lives on graphical interface
     * @param lives lives remaining
     */
    void updateLives(int lives);
}
