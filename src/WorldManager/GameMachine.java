package WorldManager;

import controller.Field;
import ui.DataOutput;
import ui.ViewMap;

import javax.swing.*;
import java.awt.*;

public class GameMachine {
    public static void main(String[] args) {
        GameMachine game = new GameMachine(
                70,
                70,
                14,
                0.05,
                0.01
        );
        game.runMachine();
    }
    private final Field theField;
    private final ViewMap view;
    private final DataOutput data;

    public GameMachine(int width, int height, int size, double numsOfRabbits, double numsOfFoxes) {
        theField = new Field(width, height, numsOfRabbits, numsOfFoxes);
        view = new ViewMap(size, theField);
        data = new DataOutput(theField, width * size / 20, height * size);
        JFrame frame = new JFrame("Game of Fox and Rabbit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(view, BorderLayout.CENTER);
        frame.add(data, BorderLayout.EAST);
        JButton button = new JButton("Next");
        button.addActionListener(e -> runOnce());
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
    }

    public void runMachine() {
        int i = 0;
        while (i < Integer.MAX_VALUE) {
            runOnce();
            i++;
        }
    }

    public void runOnce() {
        theField.onceActions();
        data.repaint();
        view.repaint();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
