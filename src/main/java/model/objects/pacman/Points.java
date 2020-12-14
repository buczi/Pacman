package model.objects.pacman;

import file.logger.Logger;
import model.objects.EventLog;

public class Points {
    int points;
    public Points(){
        this.points = 0;
    }

    public void addPoints(int points){
        this.points += points;
        Logger.generateLog(getClass().getName(), EventLog.addPoints(points,this.points)
                ,false,null);
    }

    public int getPoints() {
        return points;
    }
}
