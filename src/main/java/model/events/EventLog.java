package model.events;

/**
 * Creates templates for all events occurred
 */
public interface EventLog {

    static String move(String className,int fromY, int fromX, int toY, int toX){
        return className + " move from: " + Integer.toString(fromY) + "[Y]:" +
                Integer.toString(fromX) + "[X] to " + Integer.toString(toY) + "[Y]:" +
                Integer.toString(toX) + "[X]";
    }
    static String takeLife(String className, int livesAfter){
        return className + " lost life " + livesAfter + " [Now] -> state changed to inactive";
    }

    static String interact(String activeObj, String passiveObj, String event){
        return "Object " + activeObj + " interacted with " + passiveObj + "\nInteraction type: " + event;
    }

    static String addPoints(int deltaPoints, int allPoints){
        return "Pacman collected points: +" + deltaPoints + " : " + allPoints + "[POINTS]";
    }

    static String startPowerUp(int speed, boolean dread, float time){
        return "Pacman collected power up : " + speed + "[speed] " + dread + " [dread] " + time + "[lastingTime]";
    }
}
