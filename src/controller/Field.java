package controller;

import action.Action;
import animal.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Field {
    private final int width;
    private final int height;
    private final Action[][] theField;

    public Field(int width, int height, double numsOfRabbits, double numsOfFoxes) {
        this.width = width;
        this.height = height;
        theField = new Action[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double random = Math.random();
                if (random < numsOfRabbits) {
                    theField[i][j] = new Rabbit(Math.random());
                } else if (random < numsOfRabbits + numsOfFoxes) {
                    theField[i][j] = new Fox(Math.random());
                } else {
                    theField[i][j] = null;
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Action getAction(Location L) {
        return theField[L.x()][L.y()];
    }

    public void clearAnimal(Location L) {
        theField[L.x()][L.y()] = null;
    }

    public void putAnimal(Location L, Action action) {
        theField[L.x()][L.y()] = action;
    }

    public ArrayList<Location> getEmptyLocations(Location L) {
        ArrayList<Location> emptyLocations = new ArrayList<>();
        for (int i = L.x() - 1; i <= L.x() + 1; i++) {
            for (int j = L.y() - 1; j <= L.y() + 1; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height && !(i == L.x() && j == L.y())) {
                    if (theField[i][j] == null) {
                        emptyLocations.add(new Location(i, j));
                    }
                }
            }
        }
        return emptyLocations;
    }

    public HashMap<Location, Animal> getNeighbors(Location L) {
        HashMap<Location, Animal> neighbors = new HashMap<>();
        for (int i = L.x() - 1; i <= L.x() + 1; i++) {
            for (int j = L.y() - 1; j <= L.y() + 1; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height && !(i == L.x() && j == L.y())) {
                    Location location = new Location(i, j);
                    neighbors.put(location, (Animal) getAction(location));
                }
            }
        }
        return neighbors;
    }

    public void onceActions() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (theField[i][j] != null) {
                    stepEveryone(new Location(i, j));
                }
            }
        }
    }

    public void stepEveryone(Location L) {
        Animal animal = (Animal) theField[L.x()][L.y()];
        if (animal.grow()) {
            clearAnimal(L);
            return;
        }
        huntAction(L);
        breedAction(L);
        moveAction(L);
    }

    public void huntAction(Location L) {
        Action action = getAction(L);
        HashMap<Location, Animal> neighbors = getNeighbors(L);
        Location target = action.hunt(neighbors);
        if (target != null) {
            clearAnimal(target);
        }
    }

    public void breedAction(Location L) {
        Action action = getAction(L);
        ArrayList<Location> emptyLocations = getEmptyLocations(L);
        Location newLocation = action.breed(emptyLocations);
        if (newLocation == null) {
            return;
        }

        if (getAction(L) instanceof Rabbit) {
            putAnimal(newLocation, new Rabbit());
        } else if (getAction(L) instanceof Fox) {
            putAnimal(newLocation, new Fox());
        }
    }

    public void moveAction(Location L) {
        Action action = getAction(L);
        ArrayList<Location> emptyLocations = getEmptyLocations(L);
        Location target = action.move(emptyLocations);
        if (target == null) {
            return;
        }
        putAnimal(target, action);
        clearAnimal(L);
    }
}
