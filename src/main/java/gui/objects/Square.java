package gui.objects;

import javax.swing.*;
import java.awt.*;

/**
 * Universal class representing image on frame
 */
public class Square extends JLabel {
    private static final int side = 30;
    public Square(int x, int y, Color col){
        this.setBounds(x,y,side,side);
        this.setBackground(col);
        this.setOpaque(true);
        repaint();
        revalidate();
    }
}
