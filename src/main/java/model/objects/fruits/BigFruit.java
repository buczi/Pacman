package model.objects.fruits;

import file.logger.Logger;
import gui.events.Destruction;
import model.events.EventLog;
import model.objects.field.Field;
import model.objects.field.event.Interact;
import model.objects.pacman.Pacman;
import model.objects.pacman.PowerUp;

public class BigFruit extends Field implements Interact {
    private final int points;
    private final PowerUp power;

    public BigFruit(Field field,int points, PowerUp power){
        super(field);
        this.power = power;
        this.points = points;
    }
    @Override
    public void interact(Pacman pacman, Destruction destruction) {
        pacman.addPowerUp(power);
        pacman.collectPoint(points);
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"eating Big fruit"),
                false, null);
        this.delete();
        destruction.removeFruit(this.getX(),this.getY());
    }

    @Override
    public void delete() {
        this.changeToEmpty();
    }
}
