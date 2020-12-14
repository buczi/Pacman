package model.objects.pacman;

import file.logger.Logger;
import model.objects.EventLog;

public class PowerUp {
    private int enhancedSpeed;
    private boolean dread;
    private float powerUpTime;

    private boolean active;
    public PowerUp(){
        this.active = false;
    }

    public PowerUp(int enhancedSpeed, boolean dread, float powerUpTime){
        this();
        this.enhancedSpeed = enhancedSpeed;
        this.dread = dread;
        this.powerUpTime = powerUpTime;
    }

    public void collectPowerUp(PowerUp powerUp){
        this.active = true;
        this.enhancedSpeed = powerUp.enhancedSpeed;
        this.dread = powerUp.dread;
        this.powerUpTime = powerUp.powerUpTime;
        Logger.generateLog(getClass().getName(), EventLog.startPowerUp(this.enhancedSpeed,this.dread,this.powerUpTime)
                ,false,null);
    }

    public void endPowerUp(){
        this.active = false;
        this.enhancedSpeed = 0;
        this.dread = false;
        this.powerUpTime = 0;
    }

    public boolean isDread(){
        return dread;
    }
}
