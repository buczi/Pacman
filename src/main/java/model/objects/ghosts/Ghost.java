package model.objects.ghosts;

import file.logger.Logger;
import gui.events.Destruction;
import model.events.EventLog;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.pacman.Pacman;
import util.Counter;
import util.Direction;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Ghost extends Field {
    private Field lastField;
    private final int points = 20;
    private boolean active;
    protected boolean running;
    protected Pair<Integer,Integer> baseCords;
    protected boolean retreatMode;
    private boolean invulnerable = false;
    private long speed = 400;
    private long sSpeed = 0;

    public Ghost(Field field) {
        super(field);
        this.lastField = new Field(field.getX(), field.getY(), FieldType.Empty);
        this.active = true;
        this.retreatMode = false;
        this.running = false;
    }

    /**
     * Move to next field
     */
    public void move(Field nextField){
        Logger.generateLog(getClass().getName(), EventLog.move(getClass().getName(),getY(),getX(),nextField.getY(),nextField.getX())
                ,false,null);
        this.lastField = nextField;
        this.setX(nextField.getX());
        this.setY(nextField.getY());
    }

    //TODO event which will inform map that the ghost should be removed

    /**
     * Remove ghost from map
     */
    public void die(Pacman pacman){
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"die from attack"),
                false, null);

        pacman.collectPoint(this.points);
    }

    @Override
    public synchronized void interact(Pacman pacman, Destruction destruction){
        if(invulnerable)
            return;

        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"attacked by pacman"),
                false, null);
        if(pacman.getPower().isDread()){
            setSpeed(speed - (long)(sSpeed/2));
            die(pacman);
            pacman.getRespawn().resetGhost(this.getType());
            invulnerable = true;
            new Counter(3000,this::endVulnerability);
        }
        else
            pacman.deleteLife();
    }

    public void endVulnerability(){
        System.out.println("GHOST IS MORTAL");
        invulnerable = false;
    }
    /**
     * Get route to pacman position
     * @param direction where pacman is going
     * @return list of nodes from ghost position to pacman position
     */
    public List<Field> getRoute(Field[][] map, Pacman pacman, Direction direction){
        if(retreatMode && getX() == baseCords.getX() && getY() == baseCords.getY()){
            setSpeed(speed + (long)(sSpeed/2));
            retreatMode = false;
        }
        return new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }

    public void retreat(){
        this.retreatMode = true;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        if(this.sSpeed == 0)
            this.sSpeed = speed;
        this.speed = speed;
    }

    public void stopRunning(){
        this.running = false;
    }

    public void startRunning(){
        this.running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void turnActive(){
        active = !active;
    }
}
