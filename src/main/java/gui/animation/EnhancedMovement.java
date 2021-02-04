package gui.animation;

import body.event.Reset;

import javax.swing.*;

import static java.lang.Thread.sleep;

/**
 * Fills the gap between key frames by linear interpolation
 */
public class EnhancedMovement{
    private final JFrame frame;
    private JLabel label;
    private int deltaX;
    private int deltaY;

    public EnhancedMovement(JFrame frame)
    {
        this.frame = frame;
        this.label = new JLabel();
        this.deltaX= 0;
        this.deltaY=0;
    }

    /**
     * Move object from point A to point B in amount of frames
     * @param target what will be animated
     * @param endX final position on X axis
     * @param endY final position on Y axis
     * @param speed objects speed (time between key frames)
     */
    public void event(JLabel target, int endX, int endY, long speed){
        int frames = 10;
        this.deltaX = (int)((endX - target.getX())/ frames);
        this.deltaY = (int)((endY - target.getY())/ frames);
        this.label = target;

            try{
                for(int i = 0; i < frames; i++){
                    if(Reset.checkKillSwitch())
                        return;
                    if(i != 0){
                        sleep(speed/ frames);
                    }

                    label.setBounds(label.getX() + deltaX,
                            label.getY() + deltaY, label.getWidth(),
                            label.getHeight());
                    label.repaint();
                    label.revalidate();
                    frame.repaint();
                    frame.revalidate();
                }
            }
            catch(InterruptedException | IllegalArgumentException e){
                label.setBounds(endX,
                        endY, label.getWidth(),
                        label.getHeight());
                System.out.println("[ERROR] Animation error. Self handled.");
            }
    }
}
