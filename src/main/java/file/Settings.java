package file;

/**
 * Object containing all information about game objects properties
 */
public class Settings {
    //Pacman
    private int speed;
    private final int lives;

    //Time
    private final float maxTime;

    //Ghosts
    private final int bSpeed;
    private final int cSpeed;
    private final int iSpeed;
    private final int pSpeed;

    //Fruit
    private final int points;

    //BigFruit
    private final int bPoints;
    private final int enhancedSpeed;
    private final boolean dread;
    private final float time;

    public int getPoints() {
        return points;
    }

    public int getBPoints() {
        return bPoints;
    }

    public int getEnhancedSpeed() {
        return enhancedSpeed;
    }

    public boolean isDread() {
        return dread;
    }

    public float getTime() {
        return time;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLives() {
        return lives;
    }


    public int getBSpeed() {
        return bSpeed;
    }


    public int getCSpeed() {
        return cSpeed;
    }

    public int getISpeed() {
        return iSpeed;
    }


    public Settings(int speed, int lives, float maxTime, int bSpeed,
                    int cSpeed, int iSpeed, int pSpeed, int points,
                    int bPoints, int enhancedSpeed, boolean dread, float time) {
        this.speed = speed;
        this.lives = lives;
        this.maxTime = maxTime;
        this.bSpeed = bSpeed;
        this.cSpeed = cSpeed;
        this.iSpeed = iSpeed;
        this.pSpeed = pSpeed;
        this.points = points;
        this.bPoints = bPoints;
        this.enhancedSpeed = enhancedSpeed;
        this.dread = dread;
        this.time = time;
    }

    public int getPSpeed() {
        return pSpeed;
    }


    @Override
    public String toString() {
        return this.speed + "\n" + this.lives + "\n" + this.maxTime + "\n";
    }
}
