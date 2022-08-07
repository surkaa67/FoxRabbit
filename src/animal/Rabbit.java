package animal;

import action.Action;
import controller.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Rabbit extends Animal implements Action {

    public Rabbit() {
        this(0);
    }

    public Rabbit(double random) {
        super((int) (random * 10),
                10,
                Color.YELLOW,
                0.3,
                0.2,
                -0.1
        );
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(new Color(red, green, blue, getBrightness()));
        g.fillRect(x, y, size, size);
    }

    @Override
    public Location hunt(HashMap<Location, Animal> neighbors) {
        addEnergy((double) neighbors.size() / 8);
        return null;
    }

    @Override
    public Location breed(ArrayList<Location> emptyNeighbors) {
        int size = emptyNeighbors.size();
        if (isEnoughSpaceForBreed(size) || getEnergy() < leastEnergyForBreed || Math.random() > getVitality()) {
            return null;
        }
        clearEnergy();
        return emptyNeighbors.get((int) (Math.random() * size));
    }

    @Override
    public Location move(ArrayList<Location> emptyNeighbors) {
        int size = emptyNeighbors.size();
        if (isEnoughSpaceForMove(size) || getEnergy() < leastEnergyForMove || Math.random() > getVitality()) {
            return null;
        }
        addEnergy(energyForMoveAction);
        return emptyNeighbors.get((int) (Math.random() * size));
    }
}
