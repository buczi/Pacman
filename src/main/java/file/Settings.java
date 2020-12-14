package file;

public class Settings {
    //Pacman
    private int speed;
    private int lives;

    //Time
    private float maxTime;

    //Ghosts
    private int bSpeed;
    private int cSpeed;
    private int iSpeed;
    private int pSpeed;

    //Fruit
    private int points;

    //BigFruit
    private int bPoints;
    private int enhancedSpeed;
    private boolean dread;
    private float time;

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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public float getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public int getBSpeed() {
        return bSpeed;
    }

    public void setBSpeed(int bSpeed) {
        this.bSpeed = bSpeed;
    }

    public int getCSpeed() {
        return cSpeed;
    }

    public void setCSpeed(int cSpeed) {
        this.cSpeed = cSpeed;
    }

    public int getISpeed() {
        return iSpeed;
    }

    public void setISpeed(int iSpeed) {
        this.iSpeed = iSpeed;
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

    public void setPSpeed(int pSpeed) {
        this.pSpeed = pSpeed;
    }
}
