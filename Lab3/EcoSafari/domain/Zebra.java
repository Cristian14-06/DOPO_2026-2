package domain;

import java.awt.Color;

/**
 * Represents a Zebra prey animal in the prairie zone of the EcoSafari simulation.
 * It moves 2 steps over Ground tiles, feeds on Grass (+25% energy), and is hunted by Lions.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Zebra extends Animal {

    /* Ground tile occupied by the zebra */
    private Ground ground;

    /**
     * Constructs a new Zebra at the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Zebra(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
        this.ground = new Ground(habitat, row, column);
        habitat.set(this, row, column);
    }

    /**
     * Returns the color representing the zebra (dark charcoal).
     *
     * @return Color(30, 30, 30)
     */
    public final Color getColor() {
        return new Color(30, 30, 30);
    }

    /**
     * Returns the type identifier.
     *
     * @return "Zebra"
     */
    public final String type() {
        return "Zebra";
    }

    /**
     * Checks if this zebra can be eaten by the given predator (Lion).
     *
     * @param predator the entity attempting to eat this zebra
     * @return true if predator is a Lion, false otherwise
     */
    @Override
    public boolean canBeEaten(Entity predator) {
        return predator != null && predator.type().equals("Lion");
    }

    /*
     * Reproduces a new Zebra at the given position.
     *
     * @param row the newborn row
     * @param column the newborn column
     * @return new Zebra instance
     */
    @Override
    protected Animal reproduce(int row, int column) {
        return new Zebra(getHabitat(), row, column);
    }

    /**
     * Advances the zebra: moves 2 steps across Ground tiles and feeds on adjacent Grass.
     */
    public void tic() {
        moveAnimal(2);
        eat("Grass", (float) 0.25);
        setActed(true);
    }

    /**
     * Resets the zebra action state during the tac phase.
     */
    public void tac() {
        setActed(false);
    }
}