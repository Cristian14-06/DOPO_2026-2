package domain;

/**
 * Abstract class representing living organisms in the EcoSafari with an energy level.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public abstract class Organism {

    /* Energy level of the organism, maintained between 0 and 100 */
    private int energy;

    /**
     * Constructs a new Organism initialized with 100 units of energy.
     */
    public Organism() {
        energy = 100;
    }

    /**
     * Changes the organism's energy level by an absolute integer value, clamped to [0, 100].
     *
     * @param value the amount to add or subtract
     */
    public final void changeEnergy(int value) {
        energy += value;
        energy = (energy < 0 ? 0 : (energy > 100 ? 100 : energy));
    }

    /**
     * Changes the organism's energy level by a given percentage of its current energy.
     *
     * @param percentage the percentage to apply (e.g. 0.25 for +25%)
     */
    public final void changeEnergy(float percentage) {
        changeEnergy((int) Math.ceil(energy * percentage));
    }

    /**
     * Returns the current energy level of the organism.
     *
     * @return energy level (0-100)
     */
    public final int getEnergy() {
        return energy;
    }

    /**
     * Confirms that this entity is an organism.
     *
     * @return true
     */
    public final boolean isOrganism() {
        return true;
    }
}
