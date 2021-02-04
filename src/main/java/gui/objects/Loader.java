package gui.objects;

import body.event.Restart;
import model.objects.field.Field;
import model.objects.field.FieldType;
import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads all major graphics components
 */
public class Loader {
    private final int side = 30;
    private final List<Pair<JLabel, FieldType>> elements;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<JLabel> background;
    private final List<Fruit> fruits;

    private JLabel pointsCounter;
    private JLabel livesCounter;
    private JLabel button;

    private final Restart restart;

    //Ghosts colors
    @SuppressWarnings("SpellCheckingInspection")
    private final Color blinkyColor = Color.RED;
    private final Color pinkyColor = Color.PINK;
    private final Color inkyColor = Color.CYAN;
    private final Color clydeColor = Color.ORANGE;

    private final Color wallColor = Color.BLUE;
    private final Color backgroundColor = Color.BLACK;

    private final Color fruitColor = Color.magenta;

    private final Frame frame;

    public Loader(Frame frame, Restart restart, Field[][] grid){
        this.frame = frame;
        this.restart = restart;

        elements = new ArrayList<>();
        background = new ArrayList<>();
        fruits = new ArrayList<>();

        this.loadPacMan(grid);
        this.loadGhosts(grid);
        this.loadFruit(grid);
        this.loadBackground(grid);
        this.loadMenu(grid);
    }

    private void loadPacMan(Field[][] grid ){
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getType().equals(FieldType.PacMan)) {
                    Square pac = new Square( j * side, i * side,  Color.YELLOW);
                    elements.add(new Pair<>(pac,grid[i][j].getType()));
                    frame.add(pac);
                    return;
                }
            }
    }

    /**
     * Loads all Ghosts to the frame
     * @param grid grid map
     */
    private void loadGhosts(Field[][] grid){
        int counter = 0;
        int maxGhosts = 4; // There are 4 ghosts in pacman game
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                Color fieldCol = null;
                switch (grid[i][j].getType()){
                    case Pinky:
                        fieldCol = pinkyColor;
                        break;
                    case Blinky:
                        fieldCol = blinkyColor;
                        break;
                    case Inky:
                        fieldCol = inkyColor;
                        break;
                    case Clyde:
                        fieldCol = clydeColor;
                }
                if (fieldCol != null) {
                    Square ghost = new Square(+ j * side, i * side,  fieldCol);
                    elements.add(new Pair<>(ghost,grid[i][j].getType()));
                    frame.add(ghost);
                    counter += 1;

                    if(counter == maxGhosts)
                        return;
                }
            }
    }

    /**
     * Load background and walls to the frame
     * @param grid grid map
     */
    private void loadBackground(Field[][] grid ){
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                Square wall;
                if (grid[i][j].getType().equals(FieldType.Wall)) {
                    wall = new Square(+ j * side, i * side,  wallColor);
                    background.add(wall);
                    frame.add(wall);
                }
                wall = new Square(j * side,  i * side, backgroundColor);
                background.add(wall);
                frame.add(wall);
            }
    }

    /**
     * Load all fruits to the frame
     * @param grid grid map
     */
    private void loadFruit(Field[][] grid){
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                Fruit fruit = null;
                if (grid[i][j].getType().equals(FieldType.BigFruit)) {
                    fruit = new Fruit(j * side, i * side, fruitColor, true,j,i);
                } else if (grid[i][j].getType().equals(FieldType.Fruit)) {
                    fruit = new Fruit(j * side, i * side, fruitColor, false,j,i);
                }
                if(fruit != null){
                    frame.add(fruit);
                    fruits.add(fruit);
                }
            }
    }

    /**
     * Loads additional elements
     */
    private void loadMenu(Field[][] grid){
        JLabel points = new JLabel("POINTS", SwingConstants.CENTER);
        points.setBounds(grid.length* side + 2*side, 50, 100, 30);
        points.setBackground(Color.black);
        points.setOpaque(true);


        JLabel lives = new JLabel("Lives", SwingConstants.CENTER);
        lives.setBounds(grid.length* side + 2*side, 150, 100, 30);
        lives.setBackground(Color.black);
        lives.setOpaque(true);


        pointsCounter  = new JLabel("0", SwingConstants.CENTER);
        pointsCounter.setBounds(grid.length* side + 2*side, 100, 100, 30);
        pointsCounter.setBackground(Color.white);
        pointsCounter.setOpaque(true);


        livesCounter  = new JLabel("0", SwingConstants.CENTER);
        livesCounter.setBounds(grid.length* side + 2*side, 200, 100, 30);
        livesCounter.setBackground(Color.white);
        livesCounter.setOpaque(true);

        button  = new JLabel("Reset", SwingConstants.CENTER);
        button.setBounds(grid.length* side + 2*side, 300, 100, 30);
        button.setBackground(Color.black);
        button.setOpaque(true);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                restart.restart();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                button.setBackground(Color.white);
                button.setOpaque(true);
                button.revalidate();
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                button.setBackground(Color.black);
                button.setOpaque(true);
                button.revalidate();
                button.repaint();
            }
        });


        frame.add(points);
        frame.add(lives);
        frame.add(pointsCounter);
        frame.add(livesCounter);
        frame.add(button);
    }

    public List<Pair<JLabel, FieldType>> getElements() {
        return elements;
    }

    public List<Fruit> getFruits() {
        return fruits;
    }

    public JLabel getLivesCounter(){
        return  livesCounter;
    }

    public JLabel getPointsCounter() {
        return pointsCounter;
    }
}
