package gui.objects;

import gui.Frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Additional graphical objects. Class shows player input
 */
public class Extras extends JPanel {
    private final JPanel up;
    private final JPanel down;
    private final JPanel right;
    private final JPanel left;

    private final int side = 80;
    @SuppressWarnings("FieldCanBeLocal")
    private final int height = 600;

    public Extras(Frame frame, int leftBound){
    right = loadImages("right.png",leftBound + (int)(2.5*side), height);
    down = loadImages("down.png",leftBound + (int)(1.5*side), height);
    left = loadImages("left.png",leftBound + (int)(0.5*side), height);
    up = loadImages("up.png",leftBound + (int)(1.5*side), height - side);

    frame.add(right);
    frame.add(left);
    frame.add(up);
    frame.add(down);

    addKeyEvent(frame);
    }

    /**
     * Load image and resize it to fit label
     * @param path path to image
     * @param x width of image
     * @param y height of image
     * @return loaded panel with image
     */
    private JPanel loadImages(String path, int x, int y){
        JPanel current = new JPanel();
        try{
            current.setBounds(x, y, side, side);
            current.setBackground(Color.white);
            current.setOpaque(true);
            Image image = ImageIO.read(new File(new File("").getAbsolutePath() +
                    "/src/main/resources/img/" + path));
            image = image.getScaledInstance(side,side,Image.SCALE_SMOOTH);
            JLabel temp = new JLabel(new ImageIcon(image));
            current.setLayout(new BorderLayout());
            current.add(temp);
        }
        catch (IOException exception){
            System.out.println("[ERROR] Extra content failed to load. Minor error");
        }
        return current;
    }

    /**
     * Events when player is pressing keys
     */
    private void addKeyEvent(Frame frame){
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode())
                {
                    case KeyEvent.VK_A:
                            left.setBackground(Color.yellow);
                            left.setOpaque(true);
                            break;
                    case KeyEvent.VK_W:
                        up.setBackground(Color.yellow);
                        up.setOpaque(true);
                        break;
                    case KeyEvent.VK_D:
                        right.setBackground(Color.yellow);
                        right.setOpaque(true);
                        break;
                    case KeyEvent.VK_S:
                        down.setBackground(Color.yellow);
                        down.setOpaque(true);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode())
                {
                    case KeyEvent.VK_A:
                        left.setBackground(Color.white);
                        left.setOpaque(true);
                        break;
                    case KeyEvent.VK_W:
                        up.setBackground(Color.white);
                        up.setOpaque(true);
                        break;
                    case KeyEvent.VK_D:
                        right.setBackground(Color.white);
                        right.setOpaque(true);
                        break;
                    case KeyEvent.VK_S:
                        down.setBackground(Color.white);
                        down.setOpaque(true);
                        break;
                }
            }
        });
    }

}
