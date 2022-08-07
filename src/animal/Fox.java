package animal;

import action.Action;
import controller.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Fox extends Animal implements Action {
    public Fox() {
        this(0);
    }

    public Fox(double random) {
        super((int) (random * 15),
                15,
                Color.RED,
                0.7,
                0.3,
                -0.5
        );
    }

    @Override
    public void draw(Graphics g, int x, int y, int size) {
        g.setColor(new Color(red, green, blue, getBrightness()));
        g.fillRect(x, y, size, size);
    }

    @Override
    public Location hunt(HashMap<Location, Animal> neighbors) {
        if (neighbors.isEmpty()) {
            return null;
        }
        // 筛选出neighbours中的兔子
        HashMap<Location, Animal> rabbits = new HashMap<>();
        for (Location location : neighbors.keySet()) {
            if (neighbors.get(location) instanceof Rabbit) {
                rabbits.put(location, neighbors.get(location));
            }
        }
        if (rabbits.isEmpty()) {
            return null;
        }
        // 寻找活力最低的兔子
        double min = 1;
        Location theMinLocation = null;
        for (Location location : rabbits.keySet()) {
            if (rabbits.get(location).getVitality() < min) {
                min = rabbits.get(location).getVitality();
                theMinLocation = location;
            }
        }
        // 获得体力
        if (theMinLocation != null) {
            double energy = rabbits.get(theMinLocation).getVitality();
            this.addEnergy(energy);
            return theMinLocation;
        }
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
