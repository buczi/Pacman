package model.objects.pacman;

import file.logger.Logger;
import model.objects.*;

public class Pacman  extends Field implements Movement {
    private Field field;
    private Points points;
    private PowerUp power;
    private int speed;
    private int lives;
    //TODO when lives > 0 pacman reanimates after all ghosts arrive at gatehouse
    private boolean active;

    public Pacman(Field field, int speed, int lives){
        super(field);
        this.speed = speed;
        this.lives = lives;
        this.active = true;

        this.points = new Points();
        this.power = new PowerUp();
    }

    public Pacman(){}

    @Override
    public void move(Field field) {
        Logger.generateLog(getClass().getName(),EventLog.move(getClass().getName(),getY(),getX(),field.getY(),field.getX())
                ,false,null);
        this.setX(field.getX());
        this.setY(field.getY());
        field.interact(this);
    }

    public void addPowerUp(PowerUp powerUp){
        this.power.collectPowerUp(powerUp);
    }

    public void collectPoint(int points){
        this.points.addPoints(points);
    }

    public PowerUp getPower() {
        return power;
    }

    //TODO event that will check if pacman is still alive
    public void deleteLife(){
        this.lives -= 1;
        this.active = false;
        Logger.generateLog(getClass().getName(),EventLog.takeLife(getClass().getName(),this.lives)
                ,false,null);
    }

    public boolean isActive() {
        return active;
    }
}
