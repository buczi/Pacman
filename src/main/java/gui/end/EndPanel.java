package gui.end;

import body.event.Restart;
import gui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Initializes end panel where player can add his/hers score to table and replay
 */
public class EndPanel {
    private final JLabel button;
    private final JTextField field;
    private final Restart restart;
    private final int score;
    private final Color mainButton = Color.white;

    public EndPanel(Frame frame, Restart restart,int score){
        final Color mainBackground = Color.black;
        final Color labelBackground = Color.white;
        final Color fieldBackground = Color.gray;

        final int width = 400;
        final int height = 200;

        final int labelX = 100;
        final int labelY = 20;
        final int labelWidth = 200;
        final int labelHeight = 30;

        final int fieldX = 125;
        final int fieldY = 60;
        final int fieldWidth = 150;
        final int fieldHeight = 30;

        final int buttonX = 170;
        final int buttonY = 100;
        final int buttonWidth = 60;
        final int buttonHeight = 30;

        this.restart = restart;
        this.score = score;

        JPanel panel = new JPanel();
        panel.setBounds((int)((frame.getWidth()/2) - (width/2)), (int)((frame.getHeight()/2) - (height/2)), width, height);
        panel.setBackground(mainBackground);
        panel.setOpaque(true);
        panel.repaint();
        panel.setLayout(null);

        JLabel label = new JLabel("Enter your name:", SwingConstants.CENTER);
        label.setBounds(labelX, labelY, labelWidth, labelHeight);
        label.setBackground(labelBackground);
        label.setOpaque(true);

        panel.add(label);

        field = new JTextField("");
        field.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);
        field.setBackground(fieldBackground);
        field.setOpaque(true);


        button = new JLabel("Save", SwingConstants.CENTER);
        button.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBackground(mainButton);
        button.setOpaque(true);

        initButtons();

        panel.add(button);
        panel.add(field);

        frame.getLayeredPane().add(panel);


    }

    private void initButtons(){
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                saveScore();
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
                button.setBackground(Color.darkGray);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                button.setBackground(mainButton);
            }
        });
    }

    /**
     * Saves score of current player to file
     */
    private void saveScore(){
        try(FileWriter fw = new FileWriter(new File("").getAbsolutePath() + "/src/main/resources/score.txt",true)){
            fw.write(field.getText() +": " + score + "\n");
        }
        catch (IOException exception){
            System.out.println("Failed to load score");
        }
    }
}
