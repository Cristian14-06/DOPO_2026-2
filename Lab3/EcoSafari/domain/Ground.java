package domain;

import java.awt.Color;

/**
 * Represents Ground terrain in the prairie zone of the EcoSafari simulation.
 * It allows animals to move freely across it, can generate grass with a 10% probability,
 * and facilitates animal reproduction when placed between two animals of the same species.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Ground implements Entity {

    /* Habitat where the ground tile exists */
    private EcoSafari habitat;

    /* Flag indicating if the ground has acted in the current cycle */
    private boolean hasActed;

    /**
     * Constructs a new Ground tile at the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Ground(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    /**
     * Returns the type identifier.
     *
     * @return "Ground"
     */
    public final String type() {
        return "Ground";
    }

    /**
     * Returns the color representing ground (tan).
     *
     * @return Color(210, 180, 140)
     */
    public final Color getColor() {
        return new Color(210, 180, 140);
    }

    /**
     * Returns the habitat containing this ground tile.
     *
     * @return EcoSafari habitat
     */
    public final EcoSafari getHabitat() {
        return habitat;
    }

    /**
     * Executes the ground tile's tic phase: attempts animal reproduction or generates grass with 10% probability.
     */
    public void tic() {
        if (!newAnimal()) {
            if (Math.random() < 0.10) {
                int[] actualPosition = habitat.find((Entity) this);
                if (actualPosition == null) {
                    return;
                }
                Grass newGrass = new Grass(habitat, actualPosition[0], actualPosition[1]);
            }
        }
    }

    /*
     * Checks neighboring pairs of animals to reproduce a new animal on this ground tile.
     *
     * @return true if reproduction occurred, false otherwise
     */
    private boolean newAnimal() {
        int[][] neighbors = new int[][]{{1, 1}, {1, 0}, {0, 1}};
        int[] actualPosition = habitat.find((Entity) this);
        if (actualPosition == null) {
            return false;
        }
        int row = actualPosition[0];
        int column = actualPosition[1];
        for (int[] neighbor : neighbors) {
            Entity neighbor1 = habitat.get(row + neighbor[0], column + neighbor[1]);
            Entity neighbor2 = habitat.get(row - neighbor[0], column - neighbor[1]);
            if (neighbor1 != null && neighbor2 != null) {
                if (neighbor1.type().equals(neighbor2.type()) && neighbor1.isAnimal()) {
                    Animal newAnimal = (Animal) neighbor1;
                    newAnimal.reproduce(row, column);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Resets the ground tile action state during the tac phase.
     */
    public void tac() {
        hasActed = false;
    }
}