package domain;

import java.awt.Color;

public class Tormenta implements Entity {
    private final EcoSafari habitat;
    private boolean hasActed;

    public Tormenta(EcoSafari habitat, int row, int column) {
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
        return Color.BLACK; // Su centro es negro
    }

    @Override
    public int shape() {
        return Entity.SQUARE;
    }

    @Override
    public void tic() {
        if (!hasActed) {
            int[] pos = habitat.find(this);
            if (pos != null) {
                int r = pos[0];
                int c = pos[1];
                int size = habitat.getSize();

                // (i) Desplazamiento circular diagonal Noreste (r-1, c+1)
                int newR = (r - 1 + size) % size;
                int newC = (c + 1) % size;

                // (iii) Destruye lo que encuentra en su centro de destino
                Entity entityInCenter = habitat.get(newR, newC);
                if (entityInCenter != null && entityInCenter != this) {
                    entityInCenter.disappear();
                }

                // Mueve la tormenta al nuevo centro
                habitat.set(null, r, c);
                habitat.set(this, newR, newC);

                // (ii) & (iv) Afecta el área de diámetro 3 (3x3 alrededor del centro)
                afectarAreaCircundante(newR, newC, size);
            }
            hasActed = true;
        }
    }

    private void afectarAreaCircundante(int centerR, int centerC, int size) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Excluir el centro

                int nr = (centerR + dr + size) % size;
                int nc = (centerC + dc + size) % size;

                Entity neighbor = habitat.get(nr, nc);
                if (neighbor != null && neighbor.isOrganism()) {
                    // Reduce energía a organismos para alterar su estado/color
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