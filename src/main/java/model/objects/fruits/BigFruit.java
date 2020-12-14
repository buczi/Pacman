package model.objects.fruits;

import file.logger.Logger;
import model.objects.EventLog;
import model.objects.Field;
import model.objects.Interact;
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
    public void interact(Pacman pacman) {
        pacman.addPowerUp(power);
        pacman.collectPoint(points);
        power.endPowerUp(); //CHECK!
        Logger.generateLog(getClass().getName(),
                EventLog.interact(pacman.getClass().getName(),getClass().getName(),"eating Big fruit"),
                false, null);
        this.delete();
    }

    @Override
    public void delete() {
        this.changeToEmpty();
    }
}
