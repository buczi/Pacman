package gui.objects;

import util.Pair;

import javax.swing.*;
import java.awt.*;

/** Visual representation of fruits
 */
public class Fruit extends JLabel {
    private static final int bigSize = 20;
    private static final int smallSize = 10;
    private static final int offset = 5;
    public final Pair<Integer,Integer>pair;


    public Fruit(int x, int y, Color col, boolean big, int i, int j){
        pair = new Pair<>(i,j);
        if(big)
            this.setBounds(x + offset ,y + offset,bigSize,bigSize);
        else
            this.setBounds(x + offset*2 ,y + offset*2,smallSize,smallSize);

        this.setBackground(col);
        this.setOpaque(true);
        repaint();
        revalidate();
    }

    public boolean compare(int i, int j){
        return pair.getX().equals(i) && pair.getY().equals(j);
    }
}
