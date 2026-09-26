package domain;

import java.awt.Color;

/**
 * Represents a Shrub in the EcoSafari forest.
 * It is green when young and turns yellow upon aging (after 4 tics).
 * When young, it moves with priority: North, South, East, West.
 * It can be consumed by Elephants.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Shrub extends Organism implements Entity {

    /* Habitat where the shrub is located */
    private final EcoSafari habitat;

    /* Flag indicating if the shrub has acted in the current cycle */
    private boolean hasActed;

    /**
     * Constructs a new Shrub at the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Shrub(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    /**
     * Returns the color of the shrub (forest green when young > 60 energy, dark goldenrod when aged).
     *
     * @return Color
     */
    public final Color getColor() {
        return (getEnergy() > 60 ? new Color(34, 139, 34) : new Color(204, 153, 0));
    }

    /**
     * Returns the type identifier.
     *
     * @return "Shrub"
     */
    public final String type() {
        return "Shrub";
    }

    /**
     * Checks if this shrub can be eaten by the given predator (Elephant).
     *
     * @param predator the entity attempting to consume this shrub
     * @return true if predator is an Elephant, false otherwise
     */
    @Override
    public boolean canBeEaten(Entity predator) {
        return predator != null && predator.type().equals("Elephant");
    }

    /**
     * Executes the shrub's actions during the tic phase:
     * moves with North-South-East-West priority when eligible, ages, and consumes energy.
     */
    public void tic() {
        int[] initialPosition = habitat.find(this);
        if (initialPosition == null) {
            return;
        }
        int row = initialPosition[0];
        int column = initialPosition[1];

        if (getEnergy() <= 80 && getEnergy() >= 60) {
            if (habitat.isInside(row - 1, column) && habitat.get(row - 1, column) == null && !hasActed && move(-1, 0)) {
                changeEnergy(-10);
            } else if (habitat.isInside(row + 1, column) && habitat.get(row + 1, column) == null && !hasActed && move(1, 0)) {
                changeEnergy(-10);
            } else if (habitat.isInside(row, column + 1) && habitat.get(row, column + 1) == null && !hasActed && move(0, 1)) {
                changeEnergy(-10);
            } else if (habitat.isInside(row, column - 1) && habitat.get(row, column - 1) == null && !hasActed && move(0, -1)) {
                changeEnergy(-10);
            } else {
                changeEnergy(-10);
            }
        } else {
            changeEnergy(-10);
        }

        if (getEnergy() == 0) {
            disappear();
        }
        hasActed = true;
    }

    /**
     * Resets the shrub's action state during the tac phase.
     */
    public void tac() {
        hasActed = false;
    }

    /**
     * Returns the habitat containing this shrub.
     *
     * @return EcoSafari habitat
     */
    public EcoSafari getHabitat() {
        return habitat;
    }
}