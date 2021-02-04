package gui.events;

import model.objects.field.FieldType;

public interface Destruction {
    /**\
     * Removes fruit from given position
     * @param i y field value
     * @param j x field value
     */
    void removeFruit(int i, int j);
    /**
     * Moves ghost to given position
     * @param i y field value
     * @param j x field value
     * @param type which ghost should be moved
     */
    void moveGhost(int i, int j, FieldType type, long speed);

    /**
     * Respawn object after colliding with enemy
     * @param i x value
     * @param j y value
     * @param fieldType what object should be moved to given filed
     */
    void respawn(int i, int j, FieldType fieldType);

    /**
     * Generate panel after pacman death
     * @param score amount of points collected by player
     */
    void generateEndPanel(int score);
}
