package model.thread;

import body.event.Reset;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.ghosts.Ghost;
import model.Map;
import util.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages ghost threads
 */
@SuppressWarnings({"ALL", "FieldCanBeLocal"})
public class Manager implements  MovementSignal{
    private final boolean stop;
    private final Map map;

    private final long blinkyDelay = 600;
    private final long inkyDelay = 1200;
    private final long clydeDelay = 2200;
    private final long pinkyDelay = 3000;

    /**
     * Moves ghosts around the map in endless loop
     */
    @SuppressWarnings("FieldCanBeLocal")
    class GhostThread extends Thread{
        protected final Ghost ghost;
        private List<Field>road;
        private int roadLen;
        protected  boolean sig;
        public GhostThread(Ghost ghost){
            road = new ArrayList<>();
            this.ghost = ghost;
            this.sig = false;
        }
        @Override
        public void run(){
            this.slowDown();
            while(true){
                if(Reset.checkKillSwitch())
                    return;
                road = ghost.getRoute(map.getMap(),map.getPacman(),map.getPacmanDir());
                roadLen = 0;
                    for(Field field : road){
                        if(stop || sig)
                            break;
                        moveGhost(ghost,Direction.getDirection(-ghost.getX() + field.getX(),
                                -ghost.getY() + field.getY()));
                        roadLen++;
                        if(roadLen > (int)Math.ceil((float)road.size()/2))
                            break;
                    }
                    sig = false;
                    roadLen = 0;
                    road.clear();
            }
        }

        private void slowDown(){
            try{
                if (ghost.getType() == FieldType.Blinky)
                    sleep(blinkyDelay);
                else if (ghost.getType() == FieldType.Inky)
                        sleep(inkyDelay);
                else if (ghost.getType() == FieldType.Pinky)
                    sleep(pinkyDelay);
                else if (ghost.getType() == FieldType.Clyde)
                    sleep(clydeDelay);
            }
            catch (InterruptedException exception){
                System.out.println("Nothing to worry about. Ghost will have small advantage");
            }
        }

        protected synchronized void setSig(){
            road.clear();
            sig = true;
        }
    }

    private final List<GhostThread>threads;

    @Override
    public void  resetMovement(FieldType ghost){
        threads.forEach(x -> {
            if(x.ghost.getType().equals(ghost))
                x.setSig();
        });
    }
    public Manager(Map map){
        this.threads = new ArrayList<>();
        this.map = map;
        this.stop = false;
        map.getGhosts().forEach(x -> threads.add(new GhostThread(x)));
        threads.forEach(Thread::start);
        threads.forEach(x -> map.getMap()[x.ghost.getY()][x.ghost.getX()]
                = new Field(x.ghost.getX(),x.ghost.getY(), FieldType.Empty));
    }

    private void moveGhost(Ghost ghost, Direction direction){
        map.moveGhost(ghost,direction);
    }
}
