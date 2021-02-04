package model.objects.ghosts.movement;

import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.ghosts.Ghost;
import model.objects.pacman.Pacman;
import util.Pair;

import java.util.List;
import java.util.Random;

public class MovementBehaviour {
    private static final int max = 9;
    private static final int min = 3;
    /**
     *     Matrix of all possible normalized vectors in 2D space
      */
    private static final Pair[] matrix = {
            new Pair<>(-1, -1), new Pair<>(0, -1), new Pair<>(1, -1),
            new Pair<>(-1, 0), new Pair<>(0, 0), new Pair<>(1, 0),
            new Pair<>(-1, 1), new Pair<>(0, 1), new Pair<>(1, 1)
    };

    /**
     * Find point to which ghost should move
     * @param map available fields
     * @param pacman pacman object
     * @param ghost object which will move
     * @return current target
     */
    public static Pair<Integer, Integer> endPoint(Field[][] map, Pacman pacman, Ghost ghost) {
        Random random = new Random();
        Pair<Integer, Integer> result = new Pair<>();
        Pair<Integer, Integer> pair = !ghost.isRunning() ? getRandomDirection(getNormalizedVectorToPacman(pacman,ghost)) :
                getOppositeDirection(getNormalizedVectorToPacman(pacman,ghost));
        int numberX = random.nextInt(max) + min;
        int numberY = random.nextInt(max) + min;
        for (int i = 0; i < matrix.length - 1; i++) {
            result = checkCompatibility(map, pair, numberX, numberY, ghost);
            if (!result.equals(new Pair<>()))
                return new Pair<>(result.getX(), result.getY());
            pair = !ghost.isRunning() ? getNextDirection(pair) : getOppositeDirection(getNormalizedVectorToPacman(pacman,ghost));
        }
        return new Pair<>(ghost.getX(), ghost.getY());
    }

    /**
     * Check if ghost can move in given chunk
     * @param map available fields
     * @param newField direction of movement (normalized 2D vector)
     * @param multiX x axis multiplayer
     * @param multiY y axis multiplayer
     * @param ghost object which will move
     * @return point in chunk if movement possible or empty point if not
     */
    private static Pair<Integer,Integer> checkCompatibility(Field[][] map, Pair<Integer, Integer> newField, int multiX, int multiY, Ghost ghost) {
        for (int i = multiX; i >= min; i--) {
            for (int j = multiY; j >= min; j--) {
                if (ghost.getY() + newField.getY() * j < map.length
                        && ghost.getY() + newField.getY() * j >= 0
                        && ghost.getX() + newField.getX() * i < map[0].length
                        && ghost.getX() + newField.getX() * i >= 0){
                    if (!List.of(FieldType.Wall, FieldType.EmptyWall)
                            .contains(map[ghost.getY() + newField.getY() * j][ghost.getX() + newField.getX() * i]
                                    .getType()))
                        return new Pair<>(ghost.getX() + newField.getX() * i,ghost.getY() + newField.getY() * j);
                }
            }
        }
        return new Pair<>();
    }

    /**
     * From 8 possible directions gets next in clockwise fashion
     * @param prevDirection previously tried direction
     * @return next direction from matrix
     */
    private static Pair<Integer, Integer> getNextDirection(Pair<Integer, Integer> prevDirection) {
        for (int i = 0; i < matrix.length; i++) {
            if (prevDirection.equals(matrix[i])) {
                i++;
                if (i == 4)
                    i++;
                if (i == matrix.length)
                    i = 0;
                return matrix[i];
            }
        }
        return prevDirection;
    }

    /**
     * Gets direction that does not face previous direction
     * @param prevDirection direction to pacman
     * @return direction opposite to previous
     */
    public static Pair<Integer, Integer> getOppositeDirection(Pair<Integer, Integer> prevDirection) {
        Random random = new Random();
        int maxR = 3;
        int result = random.nextInt(maxR);
        switch(result){
            case 0:
                return new Pair<>(prevDirection.getX(), prevDirection.getY() * -1);
            case 1:
                return new Pair<>(prevDirection.getX() * -1, prevDirection.getY() * -1);
            case 2:
                return new Pair<>(prevDirection.getX() * -1, prevDirection.getY());
        }
        return prevDirection;
    }

    /**
     * Gets random direction from direction matrix, direction to pacman has 30% chance to be selected, rest directions have 10%
     * @param pacmanDirection direction to pacman
     * @return next step direction
     */
    public static Pair<Integer, Integer> getRandomDirection(Pair<Integer, Integer> pacmanDirection) {
        Random random = new Random();
        int number = random.nextInt(10);
        if (List.of(4, 9).contains(number))
            return pacmanDirection;
        return matrix[number];

    }

    /**
     * Gets direction to pacman as normalized 2D vector
     * @param pacman object of pacman
     * @param ghost current ghost
     * @return normalized vector
     */
    private static Pair<Integer, Integer> getNormalizedVectorToPacman(Pacman pacman, Ghost ghost) {
        int pX = pacman.getX() - ghost.getX();
        int pY = pacman.getY() - ghost.getY();

        if (pX == 0 && pY != 0)
            pY = pY / Math.abs(pY);
        else if (pY == 0 && pX != 0)
            pX = pX / Math.abs(pX);
        else if(pX != 0){
            pX = pX / Math.abs(pX);
            pY = pY / Math.abs(pY);
        }
        return new Pair<>(pX, pY);
    }
}
