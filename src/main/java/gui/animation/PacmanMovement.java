package gui.animation;

import javax.swing.*;

/**
 * Animates Pacman movement in new thread
 */
public class PacmanMovement extends Thread{
    final EnhancedMovement enhancedMovement;
    private final JLabel pacman;
    private final int endX;
    private final int endY;
    private final long speed;

    public PacmanMovement(JFrame frame, JLabel target, int endX, int endY, long speed){
        this.enhancedMovement = new EnhancedMovement(frame);
        this.endY = endY;
        this.endX = endX;
        this.speed = speed;
        this.pacman = target;
        this.start();
    }

    @Override
    public void run(){
        enhancedMovement.event(pacman,endX,endY,speed);
    }
}
