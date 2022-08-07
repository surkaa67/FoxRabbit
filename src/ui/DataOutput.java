package ui;

import animal.*;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DataOutput extends JPanel {
    private final Field theField;
    private final File file = new File("src/res/data.txt");
    private final int Width;
    private final int Height;

    public DataOutput(Field theField, int width, int height) {
        this.theField = theField;
        this.Height = height;
        this.Width = width;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int numOfFoxes = 0;
        int numOfRabbits = 0;
        int[] numsOfAgeFoxes = new int[new Fox().getAgeLimit()];
        int[] numsOfAgeRabbits = new int[new Rabbit().getAgeLimit()];
        // 统计数据
        for (int i = 0; i < theField.getWidth(); i++) {
            for (int j = 0; j < theField.getHeight(); j++) {
                Animal animal = (Animal) theField.getAction(new Location(i, j));
                if (animal != null) {
                    if (animal instanceof Rabbit) {
                        numOfRabbits++;
                        numsOfAgeRabbits[animal.getAge()]++;
                    } else if (animal instanceof Fox) {
                        numOfFoxes++;
                        numsOfAgeFoxes[animal.getAge()]++;
                    }
                }
            }
        }
        // 把数据写入data文件
        writeToFile(numOfFoxes, numOfRabbits, numsOfAgeFoxes, numsOfAgeRabbits);
        // 可视化数据
        double rabbitPercent = (double) numOfRabbits / (numOfRabbits + numOfFoxes);
        g.setColor(Color.RED);
        g.fillRect(0, 0, Width, Height);
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, Width, (int) (Height * rabbitPercent));
        g.setColor(Color.BLACK);
        g.drawLine(0, Height / 4, Width, Height / 4);
        g.drawLine(0, Height / 2, Width, Height / 2);
        g.drawLine(0, Height * 3 / 4, Width, Height * 3 / 4);
    }

    private void writeToFile(int numsOfFoxes, int numsOfRabbits, int[] numsOfAgeFoxes, int[] numsOfAgeRabbits) {
        StringBuilder strDataOfRabbit = new StringBuilder();
        StringBuilder strDataOfFox = new StringBuilder();
        for (int num : numsOfAgeRabbits) {
            strDataOfRabbit.append(num).append(" ");
        }
        for (int num : numsOfAgeFoxes) {
            strDataOfFox.append(num).append(" ");
        }
        try {
            java.io.FileWriter fw = new java.io.FileWriter(file, true);
            fw.write("\n");
            fw.write("" + numsOfRabbits + " " + strDataOfRabbit);
            fw.write("" + numsOfFoxes + " " + strDataOfFox);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Width, Height);
    }

}
