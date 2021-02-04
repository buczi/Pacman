package model.events;

import model.objects.field.Field;
import model.objects.field.FieldType;

/**
 * Events connected with death of pacman
 */
public interface Respawn {
    /**
     * Move pacman to given field
     * @param field field representing pacman
     */
    void respawn(Field field);

    /**
     * Make all ghosts flee to their starting position. Occurs when pacman is killed on its starting location
     */
    void resetGhosts();

    /**
     * Make one ghost flee to its starting position. Occurs when pacman kills ghost
     * @param fieldType type of ghost interacted with
     */
    void resetGhost(FieldType fieldType);

    /**
     * End game after losing all lives
     */
    void endGame();

}
