package model;

import exception.CriticalException;
import exception.ExceptionType;
import file.FileHandler;
import file.Settings;
import file.logger.Logger;
import gui.events.UI;
import model.events.EventLog;
import model.events.MapEvents;
import model.events.Move;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.ghosts.*;
import model.objects.pacman.*;
import util.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * Object representing whole logical model of game
 */
public class Map implements Move, MapEvents {
    private LinkedList<Direction> lastMoves;

    private Direction pacmanDir;
    private Field[][] map;
    private List<Ghost> ghosts;
    private Pacman pacman;
    private UI destruction;
    private final Pair<Integer, Integer> lTunnel = new Pair<>(28, 12);
    private final Pair<Integer, Integer> rTunnel = new Pair<>(-1, 12);
    @SuppressWarnings("FieldCanBeLocal")
    private final int lastMovesSize = 3;

    @Override
    public void respawn(Field field) {
        map[field.getY()][field.getX()] = field;
        map[pacman.getY()][pacman.getX()] = pacman;
        destruction.respawn(pacman.getX(), pacman.getY(), FieldType.PacMan);
    }

    @Override
    public void resetGhosts() {
        ghosts.forEach(Ghost::retreat);
    }

    @Override
    public void resetGhost(FieldType fieldType) {
        ghosts.forEach(x -> {
            if (x.getType().equals(fieldType))
                x.retreat();
        });
    }

    @Override
    public void endGame() {
        pacman.turnActive();
        ghosts.forEach(Ghost::turnActive);
        destruction.generateEndPanel(pacman.getPoints().getPoints());
    }



    @Override
    public void startRunning() {
        ghosts.forEach(Ghost::startRunning);
    }

    @Override
    public void stopRunning() {
        ghosts.forEach(Ghost::stopRunning);
    }

    /**
     * Building map template
     */
    static public class Builder {
        private UI destruction = null;
        private String[] mapFile = null;
        private Settings settings = null;

        public Builder withMapFile(String[] mapFile) {
            this.mapFile = mapFile;
            return this;
        }

        public Builder withSettings(Settings settings) {
            this.settings = settings;
            return this;
        }

        public Builder onFrame(UI destruction) {
            this.destruction = destruction;
            return this;
        }

        public Map build() throws CriticalException {
            Map mapObject = new Map();
            mapObject.lastMoves = new LinkedList<>();
            if (Stream.of(this.mapFile, this.settings, this.destruction)
                    .allMatch(x -> Optional.ofNullable(x).isPresent())) {
                Pair<Field[][], List<Ghost>> pair = MapFactory.createMap(this.mapFile, this.settings, this.destruction);
                mapObject.pacman = MapFactory.getPacman(pair.getX());
                mapObject.map = pair.getX();
                mapObject.ghosts = pair.getY();
                mapObject.destruction = destruction;
                mapObject.pacmanDir = Direction.Left;
                mapObject.getPacman().initRespawn(mapObject);
            } else {
                CriticalException cex = new CriticalException("[ERROR] UNABLE TO CREATE MAP", FileHandler.class.getName(), ExceptionType.CREATE_MAP_EXCEPTION);
                System.out.println("[USER] CRITICAL EXCEPTION CHECK CONFIGURATION FILES OR DOWNLOAD AGAIN APP");
                Logger.generateLog(FileHandler.class.getName(), "FAIL TO CREATE MAP FROM GIVEN ELEMENTS", true, cex);
            }
            Logger.generateLog(FileHandler.class.getName(), "SUCCESSFUL CREATION OF MAP", false, null);

            return mapObject;
        }
    }


    private Map() {
    }

    /**
     * Checks if there is ghost on the next field. If there is, deletes pacman life
     * @return true - there is false - field is clear
     */
    public boolean checkNextFieldForGhost(int nextX, int nextY) {
        for (Ghost ghost : ghosts) {
            if ((ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY())
                    || (ghost.getX() == nextX && ghost.getY() == nextY)) {
                ghost.interact(pacman, destruction);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean movePacman(Direction direction) {
        if(!pacman.isActive())
            return false;
        Pair<Integer, Integer> pair = Direction.getNext(direction);

        if (movePacmanTroughTunnel(checkTunnel(pair)))
            return true;
        if (!isMovePossible(pacman.getY(), pacman.getX(), direction) || pacman.isInTunnel())
            return false;

        map[pacman.getY()][pacman.getX()] = new Field(pacman.getX(), pacman.getY(), FieldType.Empty);
        if(checkNextFieldForGhost(pacman.getX() + pair.getX(),pacman.getY() + pair.getY()))
            return false;
        pacman.move(map[pacman.getY() + pair.getY()][pacman.getX() + pair.getX()], this.destruction);

        movePacmanInDirection(direction);
        return true;
    }

    @Override
    public void moveGhost(Ghost ghost, Direction direction) {
        if(!ghost.isActive())
            return;

        Pair<Integer, Integer> pair = Direction.getNext(direction);
        Field nextField = moveGhostTroughTunnel(ghost, pair);
        if (nextField == null) {
            if (!isMovePossible(ghost.getY(), ghost.getX(), direction))
                return;
            else {
                nextField = map[ghost.getY() + pair.getY()][ghost.getX() + pair.getX()];
            }
        }
        moveGhostToNextField(ghost, nextField);
    }

    @Override
    public boolean isInTunnel() {
        return pacman.isInTunnel();
    }

    @Override
    public int getSpeed() {
        return pacman.getSpeed() + pacman.getPower().getEnhancedSpeed();
    }

    /**
     * Checks if pacman can use tunnel
     *
     * @param pair movement delta
     * @return Left - right tunnel. Right - left tunnel. None - not in tunnel
     */
    private Direction checkTunnel(Pair<Integer, Integer> pair) {
        if (pacman.getY() + pair.getY() == rTunnel.getY() && pacman.getX() + pair.getX() == rTunnel.getX())
            return Direction.Left;
        else if (pacman.getY() + pair.getY() == lTunnel.getY() && pacman.getX() + pair.getX() == lTunnel.getX())
            return Direction.Right;
        else
            return Direction.None;
    }

    /**
     * Start Pacman journey in the tunnel
     *
     * @param direction way of Pacman movement
     * @return true - moved to tunnel. false - failure
     */
    private boolean movePacmanTroughTunnel(Direction direction) {
        if (direction.equals(Direction.None))
            return false;
        map[pacman.getY()][pacman.getX()] = new Field(pacman.getX(), pacman.getY(), FieldType.Empty);
        pacman.setInTunnel(true);
        if (direction.equals(Direction.Left))
            pacman.move(map[lTunnel.getY()][lTunnel.getX() - 1], this.destruction);
        else
            pacman.move(map[rTunnel.getY()][rTunnel.getX() + 1], this.destruction);

        movePacmanInDirection(direction);
        float tunnelTime = 0f;
         new Counter(tunnelTime, pacman);
        return true;
    }

    private void movePacmanInDirection(Direction direction) {
        if (pacman.isActive())
            map[pacman.getY()][pacman.getX()] = pacman;

        lastMoves.add(direction);
        if (lastMoves.size() > lastMovesSize)
            lastMoves.removeFirst();
        this.pacmanDir = this.getCurrentDirection();
    }

    private void moveGhostToNextField(Ghost ghost, Field nextField) {

        Pair<Integer, Integer> pair = copyField(nextField);
        if (nextField.getType().equals(FieldType.PacMan)) {
            Logger.generateLog(ghost.getClass().getName(), EventLog.move(ghost.getClass().getName(), ghost.getY(),
                    ghost.getX(), nextField.getY(), nextField.getX())
                    , false, null);
            ghost.interact(pacman, destruction);
        }
        if (ghost.isActive()) {
            Pair<Integer, Integer> current = new Pair<>(ghost.getX(), ghost.getY());
            ghost.move(new Field(pair.getX(), pair.getY(), FieldType.Empty));
            destruction.moveGhost(-current.getX() + pair.getX(), -current.getY() + pair.getY(), ghost.getType(), ghost.getSpeed());
        }
    }

    private Pair<Integer, Integer> copyField(Field nextField) {
        return new Pair<>(nextField.getX(), nextField.getY());
    }

    private Field moveGhostTroughTunnel(Ghost ghost, Pair<Integer, Integer> pair) {
        if (ghost.getY() + pair.getY() == rTunnel.getY() && ghost.getX() + pair.getX() == rTunnel.getX())
            return map[lTunnel.getY()][lTunnel.getX() - 1];
        else if (ghost.getY() + pair.getY() == lTunnel.getY() && ghost.getX() + pair.getX() == lTunnel.getX())
            return map[rTunnel.getY()][rTunnel.getX() + 1];
        else
            return null;
    }

    public boolean isMovePossible(int y, int x, Direction direction) {
        if (!isInBoundsX(x) && !isInBoundsY(y))
            return false;

        switch (direction) {
            case Up:
                if (isInBoundsY(y - 1))
                    if (!map[y - 1][x].getType().equals(FieldType.Wall))
                        return true;
                break;
            case Down:
                if (isInBoundsY(y + 1))
                    if (!map[y + 1][x].getType().equals(FieldType.Wall))
                        return true;
                break;
            case Left:
                if (isInBoundsY(x - 1))
                    if (!map[y][x - 1].getType().equals(FieldType.Wall))
                        return true;
                break;
            case Right:
                if (isInBoundsY(x + 1))
                    if (!map[y][x + 1].getType().equals(FieldType.Wall))
                        return true;
                break;
        }
        return false;
    }

    private boolean isInBoundsX(int val) {
        return val >= 0 && val < map[0].length + 1;
    } // + 1

    private boolean isInBoundsY(int val) {
        return val >= 0 && val < map.length + 1;
    } // + 1

    public void showMap() {
        String buffer = "";
        for (Field[] fields : map) {
            for (Field field : fields)
                buffer += field.getType().getType();
            System.out.println(buffer);
            buffer = "";
        }
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Field[][] getMap() {
        return map;
    }

    private Direction getCurrentDirection() {
        int up = 0, down = 0, right = 0, left = 0;
        for (Direction dir : this.lastMoves) {
            switch (dir) {
                case Up:
                    up += 1;
                    break;
                case Down:
                    down += 1;
                    break;
                case Left:
                    left += 1;
                    break;
                case Right:
                    right += 1;
                    break;
            }
        }
        if (up > down && up > left && up > right)
            return Direction.Up;
        if (down > up && down > left && down > right)
            return Direction.Down;
        if (left > down && left > up && left > right)
            return Direction.Left;
        if (right > down && right > left && right > up)
            return Direction.Right;
        return this.pacmanDir;
    }

    public Direction getPacmanDir() {
        return pacmanDir;
    }

}
