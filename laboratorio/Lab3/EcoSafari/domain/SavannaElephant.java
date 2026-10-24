package domain;

/**
 * Represents a Savanna Elephant, a specialized type of Elephant that moves
 * two steps per tick and recovers 40% energy when feeding on shrubs.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class SavannaElephant extends Elephant {

    /**
     * Constructs a new SavannaElephant at the given habitat and position.
     *
     * @param habitat the EcoSafari habitat
     * @param row the initial row
     * @param column the initial column
     */
    public SavannaElephant(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
    }

    /*
     * Reproduces a new SavannaElephant at the specified position.
     *
     * @param row the newborn row
     * @param column the newborn column
     * @return new SavannaElephant instance
     */
    @Override
    protected Animal reproduce(int row, int column) {
        return new SavannaElephant(getHabitat(), row, column);
    }

    /**
     * Advances the savanna elephant: moves 2 steps and recovers 40% energy when eating shrubs.
     */
    @Override
    public void tic() {
        moveAnimal(2);
        eat("Shrub", (float) 0.40);
        setActed(true);
    }
}