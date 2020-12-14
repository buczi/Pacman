package model.objects.ghosts;

import file.logger.Logger;
import model.objects.*;
import model.objects.pacman.Pacman;

public class Ghost extends Field {
    private Field lastField;
    private int points;
    private boolean alive;

    public Ghost(Field field) {
        super(field);
        this.lastField = new Field(field.getX(), field.getY(),FieldType.Empty);
        this.alive = true;
    }

    public void move(Field nextField){
        Logger.generateLog(getClass().getName(),EventLog.move(getClass().getName(),getY(),getX(),nextField.getY(),nextField.getX())
                ,false,null);
        this.lastField = nextField;
        this.setX(nextField.getX());
        this.setY(nextField.getY());
    }

    public Field leaveField(){
        Field temp = lastField;
        lastField = null;
            return temp;
    }

    //TODO event which will inform map that the ghost should be removed
    public void die(Pacman pacman){
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"die from attack"),
                false, null);
        this.alive = false;
        if(this.lastField != null)
            this.lastField.interact(pacman);
        pacman.collectPoint(this.points);
    }

    @Override
    public void interact(Pacman pacman){
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"attacked by pacman"),
                false, null);
        if(pacman.getPower().isDread())
            die(pacman);
        else
            pacman.deleteLife();
    }

    public boolean isAlive() {
        return alive;
    }
}
