package model.objects.fruits;

import file.logger.Logger;
import model.objects.EventLog;
import model.objects.Field;
import model.objects.pacman.Pacman;

public class SmallFruit extends Field {
    private final int points;

    public SmallFruit(Field field,int points){
        super(field);
        this.points = points;
    }

    @Override
    public void interact(Pacman pacman) {
        pacman.collectPoint(this.points);
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"eating Small fruit"),
                false, null);
        this.delete();
    }

    @Override
    public void delete() {
        this.changeToEmpty();
    }


}
