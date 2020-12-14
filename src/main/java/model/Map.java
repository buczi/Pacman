package model;

import file.Settings;
import file.logger.Logger;
import model.objects.*;
import model.objects.ghosts.*;
import model.objects.pacman.*;
import util.*;

import java.util.*;

//TODO ruch duchow taki aby nie zjadaly owockow jakas historia w mapie jak duch wchodzi na pole to zapisuje sie co na nim bylo chyba ze pole bylo puste
public class Map {
    private Field[][] map;
    private List<Ghost> ghosts;
    private Pacman pacman;

    public Map(String[] fileMap, Settings settings) {
        Pair<Field[][], List<Ghost>> pair = MapFactory.createMap(fileMap,settings);
        pacman = MapFactory.getPacman(pair.getX());
        map = pair.getX();
        ghosts = pair.getY();
    }

    public void movePacman(Direction direction){
        //TODO should rotate pacman if the direction differ
        if(!isMovePossible(pacman.getY(),pacman.getX(),direction))
            return;

        Pair<Integer,Integer>pair = Direction.getNext(direction);
        map[pacman.getY()][pacman.getX()] = new Field(pacman.getX(), pacman.getY(),FieldType.Empty);
        pacman.move(map[pacman.getY() + pair.getY()][pacman.getX() +pair.getX()]);
        if(pacman.isActive())
            map[pacman.getY()][pacman.getX()] = pacman;

    }

    public void moveGhost(Ghost ghost, Direction direction){
        //TODO should rotate pacman if the direction differ
        if(!isMovePossible(ghost.getY(),ghost.getX(),direction))
            return;

        Pair<Integer,Integer>pair = Direction.getNext(direction);
        Field nextField = map[ghost.getY() + pair.getY()][ghost.getX() +pair.getX()];

        map[ghost.getY()][ghost.getX()] = ghost.leaveField();

        if(nextField.getType().equals(FieldType.PacMan)){
            Logger.generateLog(ghost.getClass().getName(),EventLog.move(ghost.getClass().getName(),ghost.getY(),
                    ghost.getX(),nextField.getY(),nextField.getX())
                    ,false,null);
            ghost.interact(pacman);
        }
        if(ghost.isAlive()){
            ghost.move(nextField);
            map[ghost.getY()][ghost.getX()] = ghost;
        }
    }

    //TODO IT should throw exception or some type of log
    public boolean isMovePossible(int y, int x, Direction direction) {
        if (!isInBoundsX(x) && !isInBoundsY(y))
            return false;

        if (map[y][x].isImmovable())
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

    //TODO IT should throw exception from null pointer
    private boolean isInBoundsX(int val) {
        return val > 0 && val < map[0].length;
    }

    //TODO IT should throw exception from null pointer
    private boolean isInBoundsY(int val) {
        return val > 0 && val < map.length;
    }

    public void showMap(){
        String buffer = "";
        for(Field[] fields : map){
            for(Field field: fields)
                buffer += field.getType().getType();
            System.out.println(buffer);
            buffer = "";
        }
    }

    public List<Ghost>getGhosts(){
        return  ghosts;
    }

    public Pacman getPacman() {
        return pacman;
    }
}
