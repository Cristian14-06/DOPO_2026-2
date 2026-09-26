package domain;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the main habitat grid for the EcoSafari simulation.
 * It manages entities, time steps (tic-tac), and neighbor queries.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafari {

    /* Fixed grid size for the EcoSafari */
    private static final int SIZE = 25;

    /* Grid storing entities placed in the safari */
    private Entity[][] cells;

    /**
     * Constructs a new EcoSafari habitat and populates it with initial entities.
     */
    public EcoSafari() {
        cells = new Entity[SIZE][SIZE];
        someEntities();
    }

    /**
     * Populates the EcoSafari with initial entities representing all simulation cycles.
     */
    public void someEntities() {
        Elephant dumbo = new Elephant(this, 5, 5);
        Elephant babar = new Elephant(this, 10, 10);

        Shrub mopane = new Shrub(this, 6, 6);
        Shrub acacia = new Shrub(this, 11, 11);

        Storm thor = new Storm(this, 0, 20);
        Storm tempest = new Storm(this, 15, 23);

        SavannaElephant savanna = new SavannaElephant(this, 8, 8);

        Chameleon juan = new Chameleon(this, 2, 0);
        Chameleon cristian = new Chameleon(this, 4, 0);

        for (int r = 18; r < 25; r++) {
            for (int c = 0; c < 8; c++) {
                new Ground(this, r, c);
            }
        }
        new Grass(this, 19, 2);
        new Grass(this, 21, 5);
        new Zebra(this, 20, 2);
        new Lion(this, 22, 2);
    }

    /**
     * Returns the size of the EcoSafari grid.
     *
     * @return grid size
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Determines whether a given coordinate is inside the EcoSafari boundaries.
     *
     * @param r the row
     * @param c the column
     * @return true if inside, false otherwise
     */
    public boolean isInside(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Returns the entity located at a specified position.
     *
     * @param r the row
     * @param c the column
     * @return the entity at (r, c) or null if empty or out of bounds
     */
    public Entity get(int r, int c) {
        return (isInside(r, c) ? cells[r][c] : null);
    }

    /**
     * Places an entity directly at a specified position if inside the boundaries.
     *
     * @param e the entity
     * @param r the row
     * @param c the column
     */
    public void set(Entity e, int r, int c) {
        if (isInside(r, c)) {
            cells[r][c] = e;
        }
    }

    /**
     * Safely places an entity at a specified position verifying limits and occupancy.
     *
     * @param e the entity to place
     * @param r the row
     * @param c the column
     * @throws EcoSafariException if position is out of bounds or already occupied
     */
    public void addEntity(Entity e, int r, int c) throws EcoSafariException {
        if (!isInside(r, c)) {
            throw new EcoSafariException(EcoSafariException.OUT_OF_BOUNDS);
        }
        if (cells[r][c] != null) {
            throw new EcoSafariException(EcoSafariException.CELL_OCCUPIED);
        }
        cells[r][c] = e;
    }

    /**
     * Finds the position of an entity or throws an exception if not found.
     *
     * @param e the entity
     * @return an array {row, column}
     * @throws EcoSafariException if the entity is not in the habitat
     */
    public int[] getPosition(Entity e) throws EcoSafariException {
        int[] position = find(e);
        if (position == null) {
            throw new EcoSafariException(EcoSafariException.ENTITY_NOT_FOUND);
        }
        return position;
    }

    /**
     * Finds the position of a specified entity.
     *
     * @param e the entity
     * @return an array {row, column} if the entity is found, null otherwise
     */
    public int[] find(Entity e) {
        int[] position = null;
        for (int r = 0; r < SIZE && position == null; r++) {
            for (int c = 0; c < SIZE && position == null; c++) {
                if (cells[r][c] == e) {
                    position = new int[]{r, c};
                }
            }
        }
        return position;
    }

    /**
     * Advances the simulation by one discrete time step (tic then tac).
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Entity e = cells[r][c];
                if (e != null) {
                    e.tic();
                }
            }
        }

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Entity e = cells[r][c];
                if (e != null) {
                    e.tac();
                }
            }
        }
    }

    /**
     * Returns the 8 Moore neighbors surrounding the specified coordinate.
     *
     * @param row the central row
     * @param column the central column
     * @return list of Neighbor objects
     */
    public ArrayList<Neighbor> getNeighbors(int row, int column) {
        ArrayList<Neighbor> neighbors = new ArrayList<>();
        int[][] positions = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};

        for (int[] position : positions) {
            if (isInside(row + position[0], column + position[1])) {
                Neighbor neighbor = new Neighbor(get(row + position[0], column + position[1]), row + position[0], column + position[1]);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Returns the 8 Moore neighbors surrounding a specified entity.
     *
     * @param entity the central entity
     * @return list of Neighbor objects, or empty list if the entity is not found
     */
    public ArrayList<Neighbor> getNeighbors(Entity entity) {
        int[] position = find(entity);
        if (position == null) {
            return new ArrayList<>();
        }
        return getNeighbors(position[0], position[1]);
    }
}
