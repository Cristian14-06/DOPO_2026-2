package test;

import domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Unit tests for EcoSafari persistence (saveAs, open, exportAs and import_).
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafariPersistenceTest {

    private EcoSafari safari;
    private File testFile;
    private ArrayList<File> textFiles;

    @Before
    public void setUp() throws IOException {
        safari = new EcoSafari();
        new Elephant(safari, 5, 5);
        new Shrub(safari, 6, 6);
        new Storm(safari, 0, 20);
        new Ground(safari, 20, 2);
        new Zebra(safari, 21, 2);
        new Lion(safari, 22, 2);
        testFile = File.createTempFile("test_safari", ".dat");
        textFiles = new ArrayList<>();
    }

    @After
    public void tearDown() {
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
        for (File f : textFiles) {
            f.delete();
        }
    }

    /*
     * Creates a temporary .txt file with the given content; it is deleted in tearDown.
     */
    private File createTextFile(String content) throws IOException {
        File f = File.createTempFile("test_safari", ".txt");
        Files.write(f.toPath(), content.getBytes(StandardCharsets.UTF_8));
        textFiles.add(f);
        return f;
    }

    /*
     * Builds a text snapshot of the board (first letter of each type, '.' for empty cells).
     */
    private String snapshot(EcoSafari s) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < s.getSize(); r++) {
            for (int c = 0; c < s.getSize(); c++) {
                Entity e = s.get(r, c);
                sb.append(e == null ? "." : e.getClass().getSimpleName());
            }
            sb.append("/");
        }
        return sb.toString();
    }

    /*
     * Asserts that importing the given content fails with a message containing every expected fragment.
     */
    private void assertImportFails(String content, String... expected) throws IOException {
        File f = createTextFile(content);
        try {
            safari.import_(f);
            fail("Expected EcoSafariException importing: " + content);
        } catch (EcoSafariException e) {
            for (String fragment : expected) {
                assertTrue("Message '" + e.getMessage() + "' should contain '" + fragment + "'",
                        e.getMessage().contains(fragment));
            }
        }
    }

    /**
     * Verifies that saving a safari creates the file on disk with content.
     */
    @Test
    public void shouldCreateFileOnDiskWhenSaved() throws EcoSafariException {
        safari.saveAs(testFile);
        assertTrue("File should exist on disk after saveAs", testFile.exists());
        assertTrue("Saved file size should be greater than 0 bytes", testFile.length() > 0);
    }

    /**
     * Verifies that the state after 2 simulation cycles (tic-tac) is accurately saved and restored.
     */
    @Test
    public void shouldSaveAndRestoreStateAfterTwoTicks() throws EcoSafariException {
        safari.ticTac();
        safari.ticTac();

        // Capture snapshot of types and non-null positions
        String[][] snapshot = new String[safari.getSize()][safari.getSize()];
        for (int r = 0; r < safari.getSize(); r++) {
            for (int c = 0; c < safari.getSize(); c++) {
                Entity e = safari.get(r, c);
                snapshot[r][c] = (e != null) ? e.type() : null;
            }
        }

        safari.saveAs(testFile);

        EcoSafari restoredSafari = new EcoSafari();
        restoredSafari.open(testFile);

        for (int r = 0; r < safari.getSize(); r++) {
            for (int c = 0; c < safari.getSize(); c++) {
                Entity restored = restoredSafari.get(r, c);
                String restoredType = (restored != null) ? restored.type() : null;
                assertEquals("Cell (" + r + "," + c + ") type should match saved state",
                        snapshot[r][c], restoredType);
            }
        }
    }

    /**
     * Verifies that opening a non-existent file throws an EcoSafariException.
     */
    @Test
    public void shouldThrowExceptionWhenOpeningNonExistentFile() {
        File nonExistent = new File("archivo_que_no_existe_12345.dat");
        try {
            safari.open(nonExistent);
            fail("Expected EcoSafariException when opening non-existent file");
        } catch (EcoSafariException e) {
            assertTrue("Exception message should indicate non-existent file",
                    e.getMessage().toLowerCase().contains("no existe"));
        }
    }

    /**
     * Verifies that saving to an invalid file path throws an EcoSafariException.
     */
    @Test
    public void shouldThrowExceptionWhenSavingToInvalidPath() {
        File invalidTarget = new File("invalido.png");
        try {
            safari.saveAs(invalidTarget);
            fail("Expected EcoSafariException when saving to a non-dat file");
        } catch (EcoSafariException e) {
            assertTrue("Exception message should indicate invalid extension",
                    e.getMessage().toLowerCase().contains("no es un .dat"));
        }
    }

    /**
     * Verifies that exportAs produces a valid text file and import_ successfully restores the entities.
     */
    @Test
    public void shouldExportAndImportTextSuccessfully() throws IOException, EcoSafariException {
        File txtFile = File.createTempFile("test_safari", ".txt");
        try {
            safari.exportAs(txtFile);
            assertTrue("Exported file should exist", txtFile.exists());
            assertTrue("Exported file should not be empty", txtFile.length() > 0);

            EcoSafari importedSafari = new EcoSafari();
            importedSafari.import_(txtFile);

            for (int r = 0; r < safari.getSize(); r++) {
                for (int c = 0; c < safari.getSize(); c++) {
                    Entity original = safari.get(r, c);
                    Entity imported = importedSafari.get(r, c);
                    if (original == null) {
                        assertNull("Expected null cell at (" + r + "," + c + ")", imported);
                    } else {
                        assertNotNull("Expected non-null cell at (" + r + "," + c + ")", imported);
                        assertEquals("Entity type at (" + r + "," + c + ") should match",
                                original.type(), imported.type());
                    }
                }
            }
        } finally {
            if (txtFile.exists()) {
                txtFile.delete();
            }
        }
    }

    /**
     * Verifies that importing a non-existent text file throws EcoSafariException.
     */
    @Test
    public void shouldThrowExceptionWhenImportingInvalidTextFile() {
        File nonExistent = new File("archivo_inexistente_9999.txt");
        try {
            safari.import_(nonExistent);
            fail("Expected EcoSafariException when importing non-existent text file");
        } catch (EcoSafariException e) {
            assertTrue("Exception message should indicate non-existent file",
                    e.getMessage().toLowerCase().contains("no existe"));
        }
    }

    /**
     * Verifies that importing a file with errors throws minicompiler detailed exceptions with line, token and cause.
     */
    @Test
    public void shouldReportMinicompilerErrorsOnInvalidFile() throws IOException {
        assertImportFails("Dragon 5, 5\nElephant 10, abc\nLion 50, 50\nBush 15, 15\nStorm 5, 5\n",
                "Línea 1", "Dragon", "Causa");
    }

    /**
     * Verifies that importing two entities in the same cell reports the duplicated position.
     */
    @Test
    public void shouldReportErrorWhenImportingDuplicatedCell() throws IOException {
        assertImportFails("Elephant 5, 5\nLion 5, 5\n", "Línea 2", "5, 5", "ocupada");
    }

    /**
     * Verifies that a non numeric row is reported with its line and token.
     */
    @Test
    public void shouldReportErrorWhenRowIsNotNumeric() throws IOException {
        assertImportFails("Elephant 1, 1\nLion x, 3\n", "Línea 2", "'x'", "fila");
    }

    /**
     * Verifies that a non numeric column is reported with its line and token.
     */
    @Test
    public void shouldReportErrorWhenColumnIsNotNumeric() throws IOException {
        assertImportFails("Elephant 10, abc\n", "Línea 1", "'abc'", "columna");
    }

    /**
     * Verifies that coordinates outside the board are rejected.
     */
    @Test
    public void shouldReportErrorWhenCoordinatesAreOutOfBounds() throws IOException {
        assertImportFails("Lion 50, 50\n", "Línea 1", "50, 50", "fuera de los límites");
    }

    /**
     * Verifies that a line with only the type is rejected as incomplete.
     */
    @Test
    public void shouldReportErrorWhenLineIsIncomplete() throws IOException {
        assertImportFails("Elephant\n", "Línea 1", "Elephant", "incompleto");
    }

    /**
     * Verifies that a line with a single coordinate is rejected.
     */
    @Test
    public void shouldReportErrorWhenCoordinatesAreIncomplete() throws IOException {
        assertImportFails("Elephant 5\n", "Línea 1", "coordenadas incompletas");
    }

    /**
     * Verifies that an unknown class name is reported.
     */
    @Test
    public void shouldReportErrorWhenClassDoesNotExist() throws IOException {
        assertImportFails("Elephant 1, 1\n\nDragon 2, 2\n", "Línea 3", "Dragon", "no existe");
    }

    /**
     * Verifies that an existing class that cannot be built from the file (abstract) is reported.
     */
    @Test
    public void shouldReportErrorWhenClassCannotBeInstantiated() throws IOException {
        assertImportFails("Animal 1, 1\n", "Línea 1", "Animal", "constructor");
    }

    /**
     * Verifies that a failed import leaves the previous board untouched.
     */
    @Test
    public void shouldKeepBoardWhenImportFails() throws IOException {
        String before = snapshot(safari);
        assertImportFails("Elephant 1, 1\nLion 2, 2\nDragon 3, 3\n", "Línea 3");
        assertEquals("Board should be unchanged after a failed import", before, snapshot(safari));
    }

    /**
     * Verifies that a correct import replaces the previous board with the file content.
     */
    @Test
    public void shouldReplaceBoardWhenImportSucceeds() throws IOException, EcoSafariException {
        safari.import_(createTextFile("Zebra 3, 4\nShrub 7, 8\n"));
        assertEquals("Zebra", safari.get(3, 4).getClass().getSimpleName());
        assertEquals("Shrub", safari.get(7, 8).getClass().getSimpleName());
        assertNull("Previous entities should be removed", safari.get(5, 5));
    }

    /**
     * Verifies that importing a file without .txt extension is rejected.
     */
    @Test
    public void shouldRejectImportWithoutTxtExtension() {
        try {
            safari.import_(testFile);
            fail("Expected EcoSafariException importing a .dat file");
        } catch (EcoSafariException e) {
            assertTrue(e.getMessage().contains("no es un .txt"));
        }
    }

    /**
     * Verifies that exporting to a file without .txt extension is rejected.
     */
    @Test
    public void shouldRejectExportWithoutTxtExtension() {
        try {
            safari.exportAs(testFile);
            fail("Expected EcoSafariException exporting to a .dat file");
        } catch (EcoSafariException e) {
            assertTrue(e.getMessage().contains("no es un .txt"));
        }
    }

    /**
     * Verifies that exportAs writes one line per entity with the format "Type row, column".
     */
    @Test
    public void shouldExportOneLinePerEntityWithFormat() throws IOException, EcoSafariException {
        File f = createTextFile("");
        safari.exportAs(f);
        java.util.List<String> lines = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
        assertEquals(6, lines.size());
        assertTrue(lines.contains("Elephant 5, 5"));
        assertTrue(lines.contains("Storm 0, 20"));
    }

    /**
     * Verifies that opening a .dat file with invalid content reports corruption.
     */
    @Test
    public void shouldThrowExceptionWhenOpeningCorruptedFile() throws IOException {
        Files.write(testFile.toPath(), "esto no es un safari".getBytes(StandardCharsets.UTF_8));
        try {
            safari.open(testFile);
            fail("Expected EcoSafariException opening a corrupted file");
        } catch (EcoSafariException e) {
            assertTrue(e.getMessage().contains("corrupción"));
        }
    }

    /**
     * Verifies that opening a file without .dat extension is rejected.
     */
    @Test
    public void shouldRejectOpenWithoutDatExtension() throws IOException {
        try {
            safari.open(createTextFile("Elephant 1, 1\n"));
            fail("Expected EcoSafariException opening a .txt file");
        } catch (EcoSafariException e) {
            assertTrue(e.getMessage().contains("no es un .dat"));
        }
    }

    /**
     * Verifies that a directory cannot be used as save target.
     */
    @Test
    public void shouldRejectSaveToDirectory() throws IOException {
        File dir = Files.createTempDirectory("safari_dir.dat").toFile();
        try {
            safari.saveAs(dir);
            fail("Expected EcoSafariException saving to a directory");
        } catch (EcoSafariException e) {
            assertTrue(e.getMessage().contains("directorio"));
        } finally {
            dir.delete();
        }
    }

    /**
     * Verifies that save/open keeps the internal state (energy) while export/import only keeps type and position.
     */
    @Test
    public void shouldKeepEnergyWithOpenButNotWithImport() throws IOException, EcoSafariException {
        ((Organism) safari.get(5, 5)).changeEnergy(-30);

        safari.saveAs(testFile);
        EcoSafari opened = new EcoSafari();
        opened.open(testFile);
        assertEquals(70, ((Organism) opened.get(5, 5)).getEnergy());

        File txt = createTextFile("");
        safari.exportAs(txt);
        EcoSafari imported = new EcoSafari();
        imported.import_(txt);
        assertEquals(100, ((Organism) imported.get(5, 5)).getEnergy());
    }
}
