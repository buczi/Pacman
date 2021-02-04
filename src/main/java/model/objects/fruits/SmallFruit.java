package model.objects.fruits;

import file.logger.Logger;
import gui.events.Destruction;
import model.events.EventLog;
import model.objects.field.Field;
import model.objects.pacman.Pacman;

public class SmallFruit extends Field {
    private final int points;

    public SmallFruit(Field field,int points){
        super(field);
        this.points = points;
    }

    @Override
    public void interact(Pacman pacman, Destruction destruction) {
        pacman.collectPoint(this.points);
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"eating Small fruit"),
                false, null);
        this.delete();
        destruction.removeFruit(this.getX(), this.getY());
    }

    @Override
    public void delete() {
        this.changeToEmpty();
    }


}
