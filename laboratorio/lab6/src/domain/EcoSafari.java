package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Represents the main habitat grid for the EcoSafari simulation.
 * It manages entities, time steps (tic-tac), and neighbor queries.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafari implements Serializable{

    private static final long serialVersionUID = 1L;

    private static final int SIZE = 25;
    private Entity[][] cells;

    /**
     * Constructs a new empty EcoSafari habitat.
     * Entities are added through the import option.
     */
    public EcoSafari() {
        cells = new Entity[SIZE][SIZE];
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

    /**
     * Mock-up version of open: always propagates an under construction exception.
     *
     * @param file the file to open
     * @throws EcoSafariException always, indicating the option is under construction
     */
    public void open00(File file) throws EcoSafariException {
        String fileName = (file != null) ? file.getName() : "";
        throw new EcoSafariException("Opción open en construcción. Archivo " + fileName);
    }

    /**
     * Mock-up version of exportAs: always propagates an under construction exception.
     *
     * @param file the file to export to
     * @throws EcoSafariException always, indicating the option is under construction
     */
    public void exportAs00(File file) throws EcoSafariException {
        String fileName = (file != null) ? file.getName() : "";
        throw new EcoSafariException("Opción exportAs en construcción. Archivo " + fileName);
    }

    /**
     * Mock-up version of import_: always propagates an under construction exception.
     *
     * @param file the file to import from
     * @throws EcoSafariException always, indicating the option is under construction
     */
    public void import00(File file) throws EcoSafariException {
        String fileName = (file != null) ? file.getName() : "";
        throw new EcoSafariException("Opción import_ en construcción. Archivo " + fileName);
    }

    /**
     * Mock-up version of saveAs: always propagates an under construction exception.
     *
     * @param file the file to save to
     * @throws EcoSafariException always, indicating the option is under construction
     */
    public void saveAs00(File file) throws EcoSafariException {
        String fileName = (file != null) ? file.getName() : "";
        throw new EcoSafariException("Opción saveAs en construcción. Archivo " + fileName);
    }

    /**
     * First version of open: loads a serialized EcoSafari state from a file
     * with a general error message.
     *
     * @param file the .dat file to read
     * @throws EcoSafariException if the file cannot be read
     */
    public void open01(File file) throws EcoSafariException{
        try(ObjectInputStream open = new ObjectInputStream(new FileInputStream(file))){
            EcoSafari cargado = (EcoSafari) open.readObject();
            this.cells = cargado.cells;

        }catch(IOException | ClassNotFoundException e){
            throw new EcoSafariException("el siguiente archivo no ha sido posible abrirlo " + file.getName());
        }
    }

    /**
     * First version of exportAs: writes one line per entity with the format
     * "Type row, column" with a general error message.
     *
     * @param file the text file to write
     * @throws EcoSafariException if the file cannot be written
     */
    public void exportAs01(File file) throws EcoSafariException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (cells[i][j] != null) {
                        out.println(cells[i][j].getClass().getSimpleName() + " " + i + ", " + j);
                    }
                }
            }
        } catch (IOException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible exportarlo " + file.getName());
        }
    }

    /**
     * First version of saveAs: serializes the current EcoSafari state into a file
     * with a general error message.
     *
     * @param file the .dat file to write
     * @throws EcoSafariException if the file cannot be written
     */
    public void saveAs01(File file) throws EcoSafariException {
        try(ObjectOutputStream saveAs = new ObjectOutputStream(new FileOutputStream(file))){
             saveAs.writeObject(this);

        }catch(IOException e){
            throw new EcoSafariException("el siguiente archivo no ha sido posible guardarlo " + file.getName());
        }
    }

    /**
     * First version of import_: replaces the board with the entities described in
     * a text file with a general error message.
     *
     * @param file the text file to read
     * @throws EcoSafariException if the file cannot be read or has an invalid line
     */
    public void import01(File file) throws EcoSafariException {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String linea;
            cells = new Entity[SIZE][SIZE];
            while ((linea = in.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                String[] particion = linea.split(" ", 2);
                String tipo = particion[0].trim();
                String[] posiciones = particion[1].split(",");
                int i = Integer.parseInt(posiciones[0].trim());
                int j = Integer.parseInt(posiciones[1].trim());

                switch (tipo) {
                    case "Elephant":
                        new Elephant(this, i, j);
                        break;
                    case "SavannaElephant":
                        new SavannaElephant(this, i, j);
                        break;
                    case "Lion":
                        new Lion(this, i, j);
                        break;
                    case "Zebra":
                        new Zebra(this, i, j);
                        break;
                    case "Chameleon":
                        new Chameleon(this, i, j);
                        break;
                    case "Shrub":
                        new Shrub(this, i, j);
                        break;
                    case "Storm":
                        new Storm(this, i, j);
                        break;
                    case "Grass":
                        new Grass(this, i, j);
                        break;
                    case "Ground":
                        new Ground(this, i, j);
                        break;
                    default:
                        throw new EcoSafariException("el siguiente archivo no ha sido posible importarlo " + file.getName());
                }
            }
        } catch (Exception e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible importarlo " + file.getName());
        }
    }

    /**
     * Loads a previously saved EcoSafari state from a .dat file,
     * replacing the current board.
     *
     * @param file the .dat file to read
     * @throws EcoSafariException if the file is null, does not exist, is a directory, is not a .dat file or is corrupted
     */
    public void open(File file) throws EcoSafariException {
        if (file == null) throw new EcoSafariException("No se ha especificado ningún archivo para abrir.");
        if (!file.exists()) throw new EcoSafariException("el siguiente archivo no existe: " + file.getName());
        if (file.isDirectory()) throw new EcoSafariException("el siguiente archivo en realidad es un directorio: " + file.getName());
        if (!file.getName().endsWith(".dat")) throw new EcoSafariException("el siguiente archivo no es un .dat: " + file.getName());

        try (ObjectInputStream open = new ObjectInputStream(new FileInputStream(file))) {
            EcoSafari cargado = (EcoSafari) open.readObject();
            this.cells = cargado.cells;
        } catch (IOException | ClassNotFoundException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible abrirlo por corrupción o incompatibilidad con EcoSafari: " + file.getName());
        }
    }

    /**
     * Saves the current EcoSafari state (including the internal state of each entity)
     * into a .dat file.
     *
     * @param file the .dat file to write
     * @throws EcoSafariException if the file is null, is a directory, is not a .dat file or cannot be written
     */
    public void saveAs(File file) throws EcoSafariException {
        if (file == null) throw new EcoSafariException("No se ha especificado ningún archivo para guardar.");
        if (file.exists() && file.isDirectory()) throw new EcoSafariException("el siguiente archivo en realidad es un directorio: " + file.getName());
        if (!file.getName().endsWith(".dat")) throw new EcoSafariException("el siguiente archivo no es un .dat: " + file.getName());

        try (ObjectOutputStream saveAs = new ObjectOutputStream(new FileOutputStream(file))) {
            saveAs.writeObject(this);
        } catch (IOException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible guardarlo por error de escritura o permisos: " + file.getName());
        }
    }

    /**
     * Exports the current board to a text file, one line per entity with the format
     * "Type row, column". Only type and position are kept.
     *
     * @param file the .txt file to write
     * @throws EcoSafariException if the file is null, is a directory, is not a .txt file or cannot be written
     */
    public void exportAs(File file) throws EcoSafariException {
        if (file == null) throw new EcoSafariException("No se ha especificado ningún archivo para guardar.");
        if (file.exists() && file.isDirectory()) throw new EcoSafariException("el siguiente archivo en realidad es un directorio: " + file.getName());
        if (!file.getName().endsWith(".txt")) throw new EcoSafariException("el siguiente archivo no es un .txt: " + file.getName());
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (cells[i][j] != null) {
                        out.println(cells[i][j].getClass().getSimpleName() + " " + i + ", " + j);
                    }
                }
            }
        } catch (IOException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible exportarlo " + file.getName());
        }
    }

    /**
     * Second version of import_: validates the file and reports detailed
     * errors (incomplete data, non numeric coordinates, unknown entity).
     *
     * @param file the .txt file to read
     * @throws EcoSafariException if the file is invalid or contains an error
     */
    public void import02(File file) throws EcoSafariException {
        if (file == null) throw new EcoSafariException("No se ha especificado ningún archivo para abrir.");
        if (!file.exists()) throw new EcoSafariException("el siguiente archivo no existe: " + file.getName());
        if (file.isDirectory()) throw new EcoSafariException("el siguiente archivo en realidad es un directorio: " + file.getName());
        if (!file.getName().endsWith(".txt")) throw new EcoSafariException("el siguiente archivo no es un .txt: " + file.getName());
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String linea;
            cells = new Entity[SIZE][SIZE];
            while ((linea = in.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                String[] particion = linea.split(" ", 2);
                if (particion.length < 2) throw new EcoSafariException("alguno de los datos están incompletos");

                String tipo = particion[0].trim();
                String[] posiciones = particion[1].split(",");
                if (posiciones.length < 2) throw new EcoSafariException("alguno de las posiciones están incompletas");
                int i = Integer.parseInt(posiciones[0].trim());
                int j = Integer.parseInt(posiciones[1].trim());

                switch (tipo) {
                    case "Elephant":
                        new Elephant(this, i, j);
                        break;
                    case "SavannaElephant":
                        new SavannaElephant(this, i, j);
                        break;
                    case "Lion":
                        new Lion(this, i, j);
                        break;
                    case "Zebra":
                        new Zebra(this, i, j);
                        break;
                    case "Chameleon":
                        new Chameleon(this, i, j);
                        break;
                    case "Shrub":
                    case "Bush":
                        new Shrub(this, i, j);
                        break;
                    case "Storm":
                        new Storm(this, i, j);
                        break;
                    case "Grass":
                        new Grass(this, i, j);
                        break;
                    case "Ground":
                        new Ground(this, i, j);
                        break;
                    default:
                        throw new EcoSafariException("el archivo contiene una Entity no reconocida: " + tipo);
                }
            }
        } catch (IOException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible importarlo " + file.getName());
        } catch (NumberFormatException n) {
            throw new EcoSafariException("alguna de las coordenadas no es numerica " + file.getName());
        }
    }

    /**
     * Third version of import_ (minicompiler): reports the line number, the
     * wrong word and the cause of the first error found in the file.
     *
     * @param file the .txt file to read
     * @throws EcoSafariException if the file is invalid or contains an error, with line, word and cause
     */
    public void import03(File file) throws EcoSafariException {
        if (file == null) throw new EcoSafariException("No se ha especificado ningún archivo para abrir.");
        if (!file.exists()) throw new EcoSafariException("el siguiente archivo no existe: " + file.getName());
        if (file.isDirectory()) throw new EcoSafariException("el siguiente archivo en realidad es un directorio: " + file.getName());
        if (!file.getName().endsWith(".txt")) throw new EcoSafariException("el siguiente archivo no es un .txt: " + file.getName());

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String linea;
            cells = new Entity[SIZE][SIZE];
            int numeroLinea = 0;

            while ((linea = in.readLine()) != null) {
                numeroLinea++;
                String lineaTrim = linea.trim();
                if (lineaTrim.isEmpty()) {
                    continue;
                }

                String[] particion = lineaTrim.split(" ", 2);
                if (particion.length < 2) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + lineaTrim + "'. Causa: formato de línea incompleto (se esperaba 'Tipo fila, columna').");
                }

                String tipo = particion[0].trim();
                String[] posiciones = particion[1].split(",");
                if (posiciones.length < 2) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + particion[1].trim() + "'. Causa: coordenadas incompletas (se esperaban dos coordenadas separadas por coma).");
                }

                String strFila = posiciones[0].trim();
                String strColumna = posiciones[1].trim();
                int i;
                int j;

                try {
                    i = Integer.parseInt(strFila);
                } catch (NumberFormatException e) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + strFila + "'. Causa: la coordenada de fila no es un número entero válido.");
                }

                try {
                    j = Integer.parseInt(strColumna);
                } catch (NumberFormatException e) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + strColumna + "'. Causa: la coordenada de columna no es un número entero válido.");
                }

                if (!isInside(i, j)) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + i + ", " + j + "'. Causa: coordenadas fuera de los límites del tablero (0 a " + (SIZE - 1) + ").");
                }

                switch (tipo) {
                    case "Elephant":
                        new Elephant(this, i, j);
                        break;
                    case "SavannaElephant":
                        new SavannaElephant(this, i, j);
                        break;
                    case "Lion":
                        new Lion(this, i, j);
                        break;
                    case "Zebra":
                        new Zebra(this, i, j);
                        break;
                    case "Chameleon":
                        new Chameleon(this, i, j);
                        break;
                    case "Shrub":
                    case "Bush":
                        new Shrub(this, i, j);
                        break;
                    case "Storm":
                        new Storm(this, i, j);
                        break;
                    case "Grass":
                        new Grass(this, i, j);
                        break;
                    case "Ground":
                        new Ground(this, i, j);
                        break;
                    default:
                        throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + tipo + "'. Causa: tipo de entidad no reconocido en EcoSafari.");
                }
            }
        } catch (IOException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible importarlo: " + file.getName());
        }
    }

    /**
     * Replaces the board with the entities described in a text file, one per line
     * with the format "Type row, column". Entities are created by reflection from
     * their class name, so any future Entity with the constructor (EcoSafari, int, int)
     * is supported. If any line is wrong, the previous board is kept.
     * Errors are reported as a minicompiler: line number, wrong word and cause.
     *
     * @param file the .txt file to read
     * @throws EcoSafariException if the file is invalid or contains an error, with line, word and cause
     */
    public void import_(File file) throws EcoSafariException {
        if (file == null) throw new EcoSafariException("No se ha especificado ningún archivo para abrir.");
        if (!file.exists()) throw new EcoSafariException("el siguiente archivo no existe: " + file.getName());
        if (file.isDirectory()) throw new EcoSafariException("el siguiente archivo en realidad es un directorio: " + file.getName());
        if (!file.getName().endsWith(".txt")) throw new EcoSafariException("el siguiente archivo no es un .txt: " + file.getName());
        Entity[][] cellsTemporal = cells;
        cells = new Entity[SIZE][SIZE];
        boolean exito = false;
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String linea;

            int numeroLinea = 0;

            while ((linea = in.readLine()) != null) {
                numeroLinea++;
                String lineaTrim = linea.trim();
                if (lineaTrim.isEmpty()) {
                    continue;
                }

                String[] particion = lineaTrim.split(" ", 2);
                if (particion.length < 2) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + lineaTrim + "'. Causa: formato de línea incompleto (se esperaba 'Tipo fila, columna').");
                }

                String tipo = particion[0].trim();
                String[] posiciones = particion[1].split(",");
                if (posiciones.length < 2) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + particion[1].trim() + "'. Causa: coordenadas incompletas (se esperaban dos coordenadas separadas por coma).");
                }

                String strFila = posiciones[0].trim();
                String strColumna = posiciones[1].trim();
                int i;
                int j;

                try {
                    i = Integer.parseInt(strFila);
                } catch (NumberFormatException e) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + strFila + "'. Causa: la coordenada de fila no es un número entero válido.");
                }

                try {
                    j = Integer.parseInt(strColumna);
                } catch (NumberFormatException e) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + strColumna + "'. Causa: la coordenada de columna no es un número entero válido.");
                }

                if (!isInside(i, j)) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + i + ", " + j + "'. Causa: coordenadas fuera de los límites del tablero (0 a " + (SIZE - 1) + ").");
                }

                if (cells[i][j] != null) {
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + i + ", " + j + "'. Causa: la celda ya está ocupada por " + cells[i][j].getClass().getSimpleName() + ".");
                }

                try{
                    Class.forName("domain."+tipo).getConstructor(EcoSafari.class, int.class, int.class).newInstance(this, i, j);
                }catch(ClassNotFoundException ce ){
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + tipo + "'. Causa: la clase descrita en el archivo no existe.");
                }catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                    throw new EcoSafariException("Línea " + numeroLinea + ": error en '" + tipo + "'. Causa: la clase descrita tiene inconvenientes en su constructor.");
                }
            }
            exito = true;
        } catch (IOException e) {
            throw new EcoSafariException("el siguiente archivo no ha sido posible importarlo: " + file.getName());
        } finally{
            if(!exito) cells = cellsTemporal;        }
        
    }

}
