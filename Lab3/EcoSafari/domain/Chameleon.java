package domain;

import java.awt.Color;

public class Chameleon extends Animal {

    public Chameleon(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
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
    public String type() {
        return "Chameleon";
    }

    @Override
    public void tic() {
        if (!getActed()) {
            move(0, 1);
            changeEnergy(-5);
            if (getEnergy() <= 0) {
                disappear();
            }
            setActed(true);
        }
    }
}
