package domain;

import java.awt.Color;

/**
 * Represents a Chameleon animal in the EcoSafari simulation.
 * It changes color depending on its energy level and moves horizontally.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Chameleon extends Animal {

    /**
     * Constructs a new Chameleon in the given habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the initial row
     * @param column the initial column
     */
    public Chameleon(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
    }

    /**
     * Returns the color of the chameleon (green if energy >= 50, orange otherwise).
     *
     * @return the current Color of the chameleon
     */
    @Override
    public Color getColor() {
        return (getEnergy() >= 50) ? new Color(0, 230, 115) : new Color(255, 165, 0);
    }

    /**
     * Returns the shape of the chameleon.
     *
     * @return Entity.ROUND
     */
    @Override
    public int shape() {
        return Entity.ROUND;
    }

    /**
     * Returns the entity type name.
     *
     * @return "Chameleon"
     */
    @Override
    public String type() {
        return "Chameleon";
    }

    /*
     * Creates a new Chameleon at the specified position.
     *
     * @param row the row for the new chameleon
     * @param column the column for the new chameleon
     * @return a new Chameleon instance
     */
    @Override
    protected Animal reproduce(int row, int column) {
        return new Chameleon(getHabitat(), row, column);
    }

    /**
     * Executes the chameleon's action during the tic phase: moves east and consumes energy.
     */
    @Override
    public void tic() {
        if (!getActed()) {
            move(0, 1);
            changeEnergy(-5);
            if (getEnergy() <= 0) {
                disappear();
            }
            setActed(true);
        }
    }
}
