package model.objects.pacman;

import file.logger.Logger;
import gui.events.Destruction;
import gui.events.Update;
import model.events.EventLog;
import model.events.MapEvents;
import model.events.Respawn;
import model.events.Running;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.field.event.Movement;
import util.Alarm;
import util.Counter;
import util.Pair;
/**
 * Represents player on map
 */
public class Pacman  extends Field implements Movement, Alarm
{
    private Points points;
    private PowerUp power;
    private int speed;
    private int lives;
    private boolean inTunnel = false;
    private boolean active;
    private Update update;
    private Respawn respawn;
    private Pair<Integer,Integer> baseCords;
    private boolean invulnerable = false;
    private Running runningEvent;

    public Pacman(Field field, int speed, int lives, Update update){
        super(field);
        this.speed = speed;
        this.lives = lives;
        this.active = true;
        this.points = new Points();
        this.power = new PowerUp();
        this.update = update;
        this.baseCords = new Pair<>(field.getX(), field.getY());
    }

    public Pacman(Pacman pacman){
        super(pacman);
        this.speed = pacman.speed;
        this.lives = pacman.lives;
        this.active = pacman.active;
        this.points = pacman.points;
        this.power = pacman.power;
        this.update = pacman.update;
        this.respawn = pacman.respawn;
        this.inTunnel = pacman.inTunnel;
        this.baseCords = pacman.baseCords;
        this.invulnerable = pacman.invulnerable;
        this.runningEvent = pacman.runningEvent;
    }

    public Pacman(){}

    @Override
    public void move(Field field, Destruction destruction) {
        Logger.generateLog(getClass().getName(), EventLog.move(getClass().getName(),getY(),getX(),field.getY(),field.getX())
                ,false,null);
        this.setX(field.getX());
        this.setY(field.getY());
        field.interact(this, destruction);
    }

    public void addPowerUp(PowerUp powerUp){
        this.power.collectPowerUp(powerUp,runningEvent);
    }

    public void collectPoint(int points){
        this.points.addPoints(points);
        this.update.updatePoints(this.points.getPoints());
    }

    public PowerUp getPower() {
        return power;
    }

    public synchronized void deleteLife(){
        if(invulnerable || power.isDread())
            return;
        this.invulnerable = true;
        this.lives -= 1;
        this.update.updateLives(this.lives);

        if(lives <= 0){
            respawn.endGame();
            return;
        }

        Field field = new Field(this.getX(),this.getY(), FieldType.Empty);
        if(getX() == baseCords.getX() && getY() == baseCords.getY())
            this.respawn.resetGhosts();
        this.setX(baseCords.getX());
        this.setY(baseCords.getY());

        this.respawn.respawn(field);
        Logger.generateLog(getClass().getName(),EventLog.takeLife(getClass().getName(),this.lives)
                ,false,null);
        new Counter(2000,this::endVulnerability);
    }

    public void initLives(){
        this.update.updateLives(this.lives);
    }

    public void initRespawn(MapEvents events){
        this.respawn = events;
        this.runningEvent = events;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isInTunnel() {
        return inTunnel;
    }

    public void setInTunnel(boolean inTunnel) {
        this.inTunnel =inTunnel;
    }

    @Override
    public void counterNotify() {
            inTunnel = false;
    }

    public void endVulnerability(){
        invulnerable = false;
    }

    public Points getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    public Respawn getRespawn() {
        return respawn;
    }

    public int getSpeed() {
        return speed;
    }

    public void turnActive(){
        active = !active;
    }

}
