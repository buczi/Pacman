package gui;

import javax.swing.*;

import body.event.Restart;
import gui.animation.EnhancedMovement;
import gui.animation.PacmanMovement;
import gui.end.EndPanel;
import gui.events.UI;
import gui.objects.Extras;
import gui.objects.Fruit;
import gui.objects.Loader;
import model.*;
import model.events.Move;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.thread.MovementSignal;
import util.Alarm;
import util.Counter;
import util.Direction;
import util.Pair;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Frame extends JFrame implements Alarm, UI {
    @SuppressWarnings("FieldCanBeLocal")
    private final int width = 1200;
    @SuppressWarnings("FieldCanBeLocal")
    private final int height = 900;
    private final int side = 30;
    private List<Pair<JLabel, FieldType>> elements;
    private List<Fruit>fruits;
    private Move move;
    private boolean blocker;

    private final Restart restart;
    private MovementSignal signal;

    private Loader loader;
    private int mapWidth;
    /**
     * Initializes graphical elements
     * @param map interface used to inform model about input
     */
    public void init(Map map){
        Field[][] grid = map.getMap();
        move = map;

        new Extras(this,grid.length* side + 2*side);
        loader = new Loader(this,restart,grid);

        elements = loader.getElements();
        blocker = false;
        fruits = loader.getFruits();
        mapWidth = grid.length;

        this.addMovement();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }
    public void initEvent(MovementSignal signal){
        this.signal = signal;
    }

    public Frame(Restart restart) {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(null);
        this.restart = restart;

    }

    /**
     * Bounds keys with pacman movement
     */
    private void addMovement(){
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if(blocker)
                    return;
                long speed = move.getSpeed();
                switch (keyEvent.getKeyCode())
                {
                    case KeyEvent.VK_A:
                        if(move.movePacman(Direction.Left)){
                            JLabel label = find(FieldType.PacMan);
                            if(label != null)
                                if(move.isInTunnel())
                                    label.setBounds(label.getX() + side* mapWidth, label.getY(), label.getWidth(), label.getHeight());
                                else{
                                    new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX() - side, label.getY(),speed);
                                    new Counter(speed,(Frame)keyEvent.getSource());
                                    blocker = true;
                                }


                        }

                        break;
                    case KeyEvent.VK_W:
                        if(move.movePacman(Direction.Up)) {
                            JLabel label = find(FieldType.PacMan);
                            if (label != null){
                                new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX(), label.getY() - side,speed);
                                new Counter(speed,(Frame)keyEvent.getSource());
                                blocker = true;
                            }

                        }
                        break;
                    case KeyEvent.VK_D:
                        if(move.movePacman(Direction.Right)) {
                            JLabel label = find(FieldType.PacMan);
                            if (label != null)
                                if(move.isInTunnel())
                                    label.setBounds(label.getX() - side* mapWidth, label.getY(), label.getWidth(), label.getHeight());
                                else{
                                    new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX() + side, label.getY(),speed);
                                    new Counter(speed,(Frame)keyEvent.getSource());
                                    blocker = true;
                                }

                        }
                        break;
                    case KeyEvent.VK_S:
                        if(move.movePacman(Direction.Down)) {
                            JLabel label = find(FieldType.PacMan);
                            if (label != null){
                                new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX(), label.getY() + side,speed);
                                new Counter(speed,(Frame)keyEvent.getSource());
                                blocker = true;
                            }
                        }
                        break;
                }

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(blocker)
                    return;
                long speed = move.getSpeed();
                switch (keyEvent.getKeyCode())
                    {
                        case KeyEvent.VK_A:
                            if(move.movePacman(Direction.Left)){
                                JLabel label = find(FieldType.PacMan);
                                if(label != null)
                                    if(move.isInTunnel())
                                        label.setBounds(label.getX() + side* mapWidth, label.getY(), label.getWidth(), label.getHeight());
                                    else{
                                        new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX() - side, label.getY(),speed);
                                        new Counter(speed,(Frame)keyEvent.getSource());
                                        blocker = true;
                                    }
                            }

                            break;
                        case KeyEvent.VK_W:
                            if(move.movePacman(Direction.Up)) {
                                JLabel label = find(FieldType.PacMan);
                                if (label != null){
                                    new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX(), label.getY() - side,speed);
                                    new Counter(speed,(Frame)keyEvent.getSource());
                                    blocker = true;
                                }

                            }
                            break;
                        case KeyEvent.VK_D:
                            if(move.movePacman(Direction.Right)) {
                                JLabel label = find(FieldType.PacMan);
                                if (label != null)
                                    if(move.isInTunnel())
                                        label.setBounds(label.getX() - side* mapWidth, label.getY(), label.getWidth(), label.getHeight());
                                    else{
                                        new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX() + side, label.getY(),speed);
                                        new Counter(speed,(Frame)keyEvent.getSource());
                                        blocker = true;
                                    }

                            }
                            break;
                        case KeyEvent.VK_S:
                            if(move.movePacman(Direction.Down)) {
                                JLabel label = find(FieldType.PacMan);
                                if (label != null){
                                    new PacmanMovement((Frame)keyEvent.getSource(),label,label.getX(), label.getY() + side,speed);
                                    new Counter(speed,(Frame)keyEvent.getSource());
                                    blocker = true;
                                }
                            }
                            break;
                        case KeyEvent.VK_SPACE:
                            restart.pause();
                            break;
                    }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    /**
     * Finds element in elements list
     * @param field of elements list of special type
     */
    public JLabel find(FieldType field){
        for(Pair<JLabel,FieldType>sqr : this.elements)
            if( sqr.getY().equals(field))
                return sqr.getX();
            return null;
    }
    @Override
    public void removeFruit(int i, int j) {
        for(Fruit fruit : fruits)
            if(fruit.compare(i,j))
                this.remove(fruit);
        fruits.removeIf(fruit -> fruit.compare(i, j));
        this.revalidate();
        this.repaint();
    }

    @Override
    public void moveGhost(int i, int j, FieldType type, long speed) {
        JLabel label = find(type);
        EnhancedMovement move = new EnhancedMovement(this);
        if (label != null){
            if(i > 1 || i < -1)
                label.setBounds(label.getX() + i * side, label.getY() + j * side, label.getWidth(), label.getHeight());
            else
                move.event(label,label.getX() + i * side, label.getY() + j * side, speed );
        }

            //label.setBounds(label.getX() + i * side, label.getY() + j * side, label.getWidth(), label.getHeight());
    }

    @Override
    public void counterNotify() {
        blocker = false;
    }

    @Override
    public void updatePoints(int points) {
        this.loader.getPointsCounter().setText(Integer.toString(points));
        this.repaint();
        this.revalidate();
    }

    @Override
    public void updateLives(int lives) {
        this.loader.getLivesCounter().setText(Integer.toString(lives));
        this.repaint();
        this.revalidate();
    }

    @Override
    public void respawn(int i, int j, FieldType fieldType){
        JLabel label = find(fieldType);
        if(!fieldType.equals(FieldType.PacMan))
            signal.resetMovement(fieldType);

        if(label != null)
            label.setBounds(i*side,j*side, label.getWidth(), label.getHeight());

    }

    @Override
    public void generateEndPanel(int score) {
        new EndPanel(this, restart, score);
    }
}
