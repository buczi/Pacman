package model.objects.ghosts;

import model.objects.field.Field;

import model.objects.ghosts.movement.Road;
import model.objects.pacman.Pacman;
import util.Direction;
import util.Pair;

import java.util.List;

import static model.objects.ghosts.movement.MovementBehaviour.endPoint;

/**
 * Ghost which uses random algorithm to find its way trough maze
 */
@SuppressWarnings("SpellCheckingInspection")
public class Blinky extends Ghost{

    public Blinky(Field field) {
        super(field);
        this.baseCords = new Pair<>(12, 12);
    }

    public List<Field> getRoute(Field[][] map, Pacman pacman, Direction direction) {
        super.getRoute(map,pacman,direction);
        if (retreatMode)
            return new Road(map, this, baseCords.getX(), baseCords.getY()).findPathTo();
        Pair<Integer, Integer> pair = endPoint(map, pacman,this);
        return new Road(map, this, pair.getX(), pair.getY()).findPathTo();
    }
}
