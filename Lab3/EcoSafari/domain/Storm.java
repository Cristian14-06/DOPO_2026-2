package domain;

import java.awt.Color;

/**
 * Represents a Storm in the EcoSafari simulation.
 * Its center moves diagonally in a northeast direction with toroidal wrap-around,
 * destroys anything caught directly in its center, and damages surrounding organisms by -30 energy.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Storm implements Entity {

    /* Habitat where the storm occurs */
    private final EcoSafari habitat;

    /* Flag indicating if the storm has acted in the current cycle */
    private boolean hasActed;

    /**
     * Constructs a new Storm at the specified habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Storm(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        this.hasActed = false;
        habitat.set(this, row, column);
    }

    /**
     * Returns the EcoSafari habitat of the storm.
     *
     * @return EcoSafari habitat
     */
    @Override
    public EcoSafari getHabitat() {
        return habitat;
    }

    /**
     * Returns the color representing the storm center (dark slate / near black).
     *
     * @return Color(40, 40, 50)
     */
    @Override
    public Color getColor() {
        return new Color(40, 40, 50);
    }

    /**
     * Returns the type identifier.
     *
     * @return "Storm"
     */
    @Override
    public String type() {
        return "Storm";
    }

    /**
     * Advances the storm: moves northeast, destroys the center cell occupant,
     * and reduces energy of all organisms in the 3x3 surrounding area by 30.
     */
    @Override
    public void tic() {
        if (!hasActed) {
            int[] pos = habitat.find(this);
            if (pos != null) {
                int r = pos[0];
                int c = pos[1];
                int size = habitat.getSize();

                int newR = (r - 1 + size) % size;
                int newC = (c + 1) % size;

                Entity entityInCenter = habitat.get(newR, newC);
                if (entityInCenter != null && entityInCenter != this) {
                    entityInCenter.disappear();
                }

                habitat.set(null, r, c);
                habitat.set(this, newR, newC);

                affectSurroundingArea(newR, newC, size);
            }
            hasActed = true;
        }
    }

    /*
     * Damages organisms within a 3x3 surrounding diameter by reducing 30 energy units.
     *
     * @param centerR center row coordinate
     * @param centerC center column coordinate
     * @param size habitat grid size for toroidal wrapping
     */
    private void affectSurroundingArea(int centerR, int centerC, int size) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int nr = (centerR + dr + size) % size;
                int nc = (centerC + dc + size) % size;

                Entity neighbor = habitat.get(nr, nc);
                if (neighbor != null && neighbor.isOrganism()) {
                    ((Organism) neighbor).changeEnergy(-30);
                }
            }
        }
    }

    /**
     * Resets the storm's action state during the tac phase.
     */
    @Override
    public void tac() {
        hasActed = false;
    }
}
