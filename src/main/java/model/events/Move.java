package model.events;

import model.objects.ghosts.Ghost;
import util.Direction;

/**
 * Communicate with gui
 */
public interface Move {
    /**
     * Move pacman on the screen
     * @param direction direction of movement
     * @return if movement can be made
     */
     boolean movePacman(Direction direction);

    /**
     * Move ghost on the screen
     * @param ghost which object should be moved
     * @param direction direction of movement
     */
    void moveGhost(Ghost ghost, Direction direction);

    /**
     * Checks if object is in the tunnel
     */
    boolean isInTunnel();

    /**
     * Gets pacman speed
     * @return pacman speed
     */
    int getSpeed();
}
