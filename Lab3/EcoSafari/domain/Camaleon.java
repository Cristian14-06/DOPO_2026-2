package domain;

import java.awt.Color;

public class Camaleon extends Organism implements Entity {
    private final EcoSafari habitat;
    private boolean hasActed;

    public Camaleon(EcoSafari habitat, int row, int column) {
        super();
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
        return (getEnergy() >= 50) ? Color.GREEN : Color.YELLOW;
    }

    @Override
    public int shape() {
        return Entity.ROUND;
    }

    @Override
    public void tic() {
        if (!hasActed) {
            move(0, 1);
            
            changeEnergy(-5);
            
            if (getEnergy() <= 0) {
                disappear();
            }
            hasActed = true;
        }
    }

    @Override
    public void tac() {
        hasActed = false;
    }
}