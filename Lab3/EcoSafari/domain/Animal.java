package domain;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Abstract class Animal representing living moving creatures in the EcoSafari.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public abstract class Animal extends Organism implements Entity {

    /* Habitat where the animal lives */
    private EcoSafari habitat;

    /* Flag indicating if the animal has already acted in the current cycle */
    private boolean hasActed;

    /**
     * Constructs a new Animal in the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the initial row
     * @param column the initial column
     */
    public Animal(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    /**
     * Returns the habitat of the animal.
     *
     * @return the EcoSafari habitat
     */
    public EcoSafari getHabitat() {
        return habitat;
    }

    /*
     * Returns whether the animal has acted in this turn.
     *
     * @return true if it has acted, false otherwise
     */
    protected boolean getActed() {
        return hasActed;
    }

    /**
     * Confirms that this entity is an animal.
     *
     * @return true
     */
    public final boolean isAnimal() {
        return true;
    }

    /*
     * Creates a new animal of the same species at the given position.
     *
     * @param row the row where the newborn is placed
     * @param column the column where the newborn is placed
     * @return the newly born Animal
     */
    protected abstract Animal reproduce(int row, int column);

    /*
     * Sets whether the animal has acted in this cycle.
     *
     * @param state the acted state
     */
    protected void setActed(boolean state) {
        hasActed = state;
    }

    /**
     * Returns the color representing the animal.
     *
     * @return the Color of the animal
     */
    public abstract Color getColor();

    /**
     * Advances the animal's actions during the tic phase of the cycle.
     */
    public abstract void tic();

    /*
     * Moves the animal a given number of steps across ground cells in the prairie.
     *
     * @param steps number of steps to move
     */
    protected void moveAnimal(int steps) {
        int[] initialPosition = habitat.find(this);
        if (initialPosition == null) {
            return;
        }
        int row = initialPosition[0];
        int column = initialPosition[1];

        if (habitat.isInside(row - steps, column)
                && habitat.get(row - steps, column) != null
                && habitat.get(row - steps, column).type().equals("Ground")
                && !hasActed) {

            Entity ground = habitat.get(row - steps, column);
            move(-steps, 0);
            habitat.set(ground, row, column);
            changeEnergy(-10);

            if (getEnergy() == 0) {
                disappear();
                new Ground(habitat, row - steps, column);
            }

        } else if (habitat.isInside(row + steps, column)
                && habitat.get(row + steps, column) != null
                && habitat.get(row + steps, column).type().equals("Ground")
                && !hasActed) {

            Entity ground = habitat.get(row + steps, column);
            move(steps, 0);
            habitat.set(ground, row, column);
            changeEnergy(-10);

            if (getEnergy() == 0) {
                disappear();
                new Ground(habitat, row + steps, column);
            }

        } else if (habitat.isInside(row, column - steps)
                && habitat.get(row, column - steps) != null
                && habitat.get(row, column - steps).type().equals("Ground")
                && !hasActed) {

            Entity ground = habitat.get(row, column - steps);
            move(0, -steps);
            habitat.set(ground, row, column);
            changeEnergy(-10);

            if (getEnergy() == 0) {
                disappear();
                new Ground(habitat, row, column - steps);
            }

        } else if (habitat.isInside(row, column + steps)
                && habitat.get(row, column + steps) != null
                && habitat.get(row, column + steps).type().equals("Ground")
                && !hasActed) {

            Entity ground = habitat.get(row, column + steps);
            move(0, steps);
            habitat.set(ground, row, column);
            changeEnergy(-10);

            if (getEnergy() == 0) {
                disappear();
                new Ground(habitat, row, column + steps);
            }
        }
    }

    /*
     * Attempts to eat adjacent prey and increases energy.
     *
     * @param food name of the food type
     * @param percentage percentage of energy gained
     */
    protected void eat(String food, float percentage) {
        int[] initialPosition = habitat.find(this);
        if (initialPosition == null) {
            return;
        }
        int row = initialPosition[0];
        int column = initialPosition[1];
        ArrayList<Neighbor> neighbors = habitat.getNeighbors(row, column);

        for (Neighbor neighbor : neighbors) {
            Entity entity = neighbor.getEntity();
            if (entity != null && entity.canBeEaten(this)) {
                entity.disappear();
                new Ground(habitat, neighbor.getRow(), neighbor.getColumn());
                changeEnergy(percentage);
            }
        }
    }

    /**
     * Resets the animal's acted state during the tac phase of the cycle.
     */
    public void tac() {
        hasActed = false;
    }
}
