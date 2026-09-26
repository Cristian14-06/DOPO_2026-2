package domain;

/**
 * Encapsulates information about a neighboring cell in the EcoSafari,
 * including the entity present and its coordinate position.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class Neighbor {

    /* Entity located at the neighboring cell */
    private Entity entity;

    /* Row index of the neighbor */
    private int row;

    /* Column index of the neighbor */
    private int column;

    /**
     * Constructs a Neighbor object with the given entity and coordinates.
     *
     * @param entity the entity present at the neighbor cell
     * @param row the row coordinate
     * @param column the column coordinate
     */
    public Neighbor(Entity entity, int row, int column) {
        this.entity = entity;
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the entity present at the neighboring cell.
     *
     * @return the Entity, or null if cell is empty
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Returns the row coordinate of the neighbor.
     *
     * @return row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column coordinate of the neighbor.
     *
     * @return column index
     */
    public int getColumn() {
        return column;
    }
}