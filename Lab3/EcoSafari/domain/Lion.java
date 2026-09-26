package domain;

import java.awt.Color;

/**
 * Represents a Lion predator in the prairie zone of the EcoSafari simulation.
 * It moves 1 step over Ground tiles, hunts Zebras, and gains 50% energy when eating.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Lion extends Animal {

    /* Ground tile occupied by the lion */
    private Ground ground;

    /**
     * Constructs a new Lion at the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Lion(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
        this.ground = new Ground(habitat, row, column);
        habitat.set(this, row, column);
    }

    /**
     * Returns the color representing the lion (goldenrod).
     *
     * @return Color(218, 165, 32)
     */
    public final Color getColor() {
        return new Color(218, 165, 32);
    }

    /**
     * Returns the type identifier.
     *
     * @return "Lion"
     */
    public final String type() {
        return "Lion";
    }

    /*
     * Reproduces a new Lion at the given position.
     *
     * @param row the newborn row
     * @param column the newborn column
     * @return new Lion instance
     */
    @Override
    protected Animal reproduce(int row, int column) {
        return new Lion(getHabitat(), row, column);
    }

    /**
     * Executes the lion's actions during the tic phase: moves 1 step and hunts Zebras.
     */
    public void tic() {
        moveAnimal(1);
        eat("Zebra", (float) 0.50);
        setActed(true);
    }

    /**
     * Resets the lion's action state during the tac phase.
     */
    public void tac() {
        setActed(false);
    }
}