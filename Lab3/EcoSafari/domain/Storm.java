package domain;

import java.awt.Color;

public class Storm implements Entity {
    private final EcoSafari habitat;
    private boolean hasActed;

    public Storm(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        this.hasActed = false;
        habitat.set(this, row, column);
    }

    @Override
    public EcoSafari getHabitat() {
        return habitat;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public String type() {
        return "Storm";
    }

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

                afectarAreaCircundante(newR, newC, size);
            }
            hasActed = true;
        }
    }

    private void afectarAreaCircundante(int centerR, int centerC, int size) {
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

    @Override
    public void tac() {
        hasActed = false;
    }
}
