package domain;

import java.awt.Color;

/**
 * Interface representing any active or passive element inhabiting the EcoSafari grid.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public interface Entity {

    /** Square shape representation constant */
    public static final int SQUARE = 2;

    /** Round shape representation constant */
    public static final int ROUND = 1;

    /**
     * Executes the primary action phase of the entity.
     */
    public void tic();

    /**
     * Executes the secondary cleanup/reset phase of the entity.
     */
    public default void tac() {
    }

    /**
     * Returns the geometric shape used to draw the entity.
     *
     * @return SQUARE or ROUND
     */
    public default int shape() {
        return SQUARE;
    }

    /**
     * Returns the string identifier of the entity type.
     *
     * @return type name
     */
    public abstract String type();

    /**
     * Returns the display color of the entity.
     *
     * @return Color
     */
    public abstract Color getColor();

    /**
     * Indicates whether the entity is a living organism with energy.
     *
     * @return true if organism, false otherwise
     */
    public default boolean isOrganism() {
        return false;
    }

    /**
     * Indicates whether the entity is an animal.
     *
     * @return true if animal, false otherwise
     */
    public default boolean isAnimal() {
        return false;
    }

    /**
     * Checks if this entity can be consumed by the given predator.
     *
     * @param predator the entity attempting to consume this one
     * @return true if edible by predator, false otherwise
     */
    public default boolean canBeEaten(Entity predator) {
        return false;
    }

    /**
     * Returns the EcoSafari habitat containing this entity.
     *
     * @return EcoSafari habitat
     */
    public abstract EcoSafari getHabitat();

    /**
     * Removes the entity from the habitat grid.
     *
     * @return true if successfully removed, false otherwise
     */
    public default boolean disappear() {
        boolean ok = false;
        int[] position = this.getHabitat().find(this);
        if (position != null) {
            getHabitat().set(null, position[0], position[1]);
            ok = true;
        }
        return ok;
    }

    /**
     * Moves the entity by the specified row and column offsets.
     *
     * @param deltaRows row offset
     * @param deltaColumns column offset
     * @return true if move was successful, false otherwise
     */
    public default boolean move(int deltaRows, int deltaColumns) {
        int[] position = getHabitat().find(this);
        EcoSafari habitat = getHabitat();
        boolean ok = false;
        if (position != null) {
            int r = position[0];
            int c = position[1];
            if (habitat.isInside(r + deltaRows, c + deltaColumns)) {
                habitat.set(null, r, c);
                habitat.set(this, r + deltaRows, c + deltaColumns);
                ok = true;
            }
        }
        return ok;
    }
}
