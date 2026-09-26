package domain;

import java.awt.Color;

/**
 * Represents an Elephant in the EcoSafari simulation.
 * It moves diagonally, consumes energy per step, and feeds on shrubs.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Elephant extends Animal {

    /**
     * Constructs a new Elephant at the given habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the initial row
     * @param column the initial column
     */
    public Elephant(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
    }

    /**
     * Returns the color of the elephant based on its energy level.
     *
     * @return dark slate gray if energy >= 80, light gray otherwise
     */
    public final Color getColor() {
        return (getEnergy() >= 80 ? new Color(112, 128, 144) : new Color(190, 190, 195));
    }

    /**
     * Returns the type identifier for Elephant.
     *
     * @return "Elephant"
     */
    public final String type() {
        return "Elephant";
    }

    /**
     * Returns the shape representation for Elephant.
     *
     * @return Entity.ROUND
     */
    public final int shape() {
        return Entity.ROUND;
    }

    /*
     * Reproduces an Elephant at the specified position.
     *
     * @param row the row for the newborn
     * @param column the column for the newborn
     * @return new Elephant instance
     */
    @Override
    protected Animal reproduce(int row, int column) {
        return new Elephant(getHabitat(), row, column);
    }

    /*
     * Moves the elephant diagonally by steps in both axes.
     *
     * @param steps number of steps to advance diagonally
     */
    @Override
    protected void moveAnimal(int steps) {
        if (!getActed() && move(steps, steps)) {
            changeEnergy(-10);
            if (getEnergy() == 0) {
                disappear();
            }
        }
    }

    /**
     * Executes the elephant's actions during the tic phase: moves 1 step and eats shrubs.
     */
    public void tic() {
        moveAnimal(1);
        eat("Shrub", (float) 0.10);
        setActed(true);
    }

    /**
     * Resets the elephant's action state during the tac phase.
     */
    public void tac() {
        setActed(false);
    }
}
