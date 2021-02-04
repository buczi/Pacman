package model.objects.ghosts;

import model.objects.field.Field;
import model.objects.ghosts.movement.Road;
import model.objects.pacman.Pacman;
import util.Direction;
import util.Pair;

import java.util.List;
import static model.objects.ghosts.movement.MovementBehaviour.endPoint;

/**
 * Follow pacman
 */
public class Inky extends Ghost {
    public Inky(Field field) {
        super(field);
        this.baseCords = new Pair<>(14,12);
    }

    @Override
    public List<Field> getRoute(Field[][] map, Pacman pacman, Direction direction) {
        super.getRoute(map,pacman,direction);
        if(running){
            Pair<Integer, Integer> pair = endPoint(map, pacman,this);
            return new Road(map, this, pair.getX(), pair.getY()).findPathTo();
        }
        else if(retreatMode)
            return new Road(map, this, baseCords.getX(), baseCords.getY()).findPathTo();
        return new Road(map, this, pacman.getX(), pacman.getY()).findPathTo();

    }

}
