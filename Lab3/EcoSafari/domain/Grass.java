package domain;

import java.awt.Color;

/**
 * Represents Grass in the prairie zone of the EcoSafari simulation.
 * It serves as food for Zebras and changes color as its energy decreases.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Grass extends Organism implements Entity {

    /* Habitat where the grass grows */
    private EcoSafari habitat;

    /* Flag indicating if the grass has acted in the current cycle */
    private boolean hasActed;

    /**
     * Constructs a new Grass organism at the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Grass(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    /**
     * Returns the type identifier.
     *
     * @return "Grass"
     */
    public final String type() {
        return "Grass";
    }

    /**
     * Checks if this grass can be consumed by the given predator (Zebra).
     *
     * @param predator the entity attempting to eat the grass
     * @return true if predator is a Zebra, false otherwise
     */
    @Override
    public boolean canBeEaten(Entity predator) {
        return predator != null && predator.type().equals("Zebra");
    }

    /**
     * Returns the color of the grass based on its energy level.
     *
     * @return bright lawn green if energy >= 40, dark khaki otherwise
     */
    public final Color getColor() {
        return (getEnergy() >= 40 ? new Color(124, 252, 0) : new Color(189, 183, 107));
    }

    /**
     * Returns the habitat containing this grass.
     *
     * @return EcoSafari habitat
     */
    public final EcoSafari getHabitat() {
        return habitat;
    }

    /**
     * Reduces grass energy by 5 units during the tic phase.
     */
    public void tic() {
        changeEnergy(-5);
        hasActed = true;
    }

    /**
     * Resets the grass action state during the tac phase.
     */
    public final void tac() {
        hasActed = false;
    }
}