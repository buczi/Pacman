package model.objects.pacman;

import file.logger.Logger;
import model.events.Running;
import model.events.EventLog;
import util.Alarm;
import util.Counter;

public class PowerUp implements Alarm {
    private int enhancedSpeed;
    private boolean dread;
    private float powerUpTime;
    private Running runningEvent;
    public PowerUp(){
        this.dread = false;
    }

    public PowerUp(int enhancedSpeed, boolean dread, float powerUpTime){
        this();
        this.enhancedSpeed = enhancedSpeed;
        this.dread = dread;
        this.powerUpTime = powerUpTime;
    }

    public void collectPowerUp(PowerUp powerUp, Running runningEvent){
        this.runningEvent = runningEvent;
        this.runningEvent.startRunning();
        this.enhancedSpeed = powerUp.enhancedSpeed;
        this.dread = powerUp.dread;
        this.powerUpTime = powerUp.powerUpTime;
        Logger.generateLog(getClass().getName(), EventLog.startPowerUp(this.enhancedSpeed,this.dread,this.powerUpTime)
                ,false,null);
        new Counter(powerUpTime * 1000,this);
    }

    public void endPowerUp(){
        this.enhancedSpeed = 0;
        this.dread = false;
        this.powerUpTime = 0;
    }

    public boolean isDread(){
        return dread;
    }


    @Override
    public void counterNotify() {
        endPowerUp();
        this.runningEvent.stopRunning();
    }

    public int getEnhancedSpeed() {
        return enhancedSpeed;
    }
}
