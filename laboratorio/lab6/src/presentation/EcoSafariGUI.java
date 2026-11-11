package presentation;

import domain.*;
import java.awt.*;

import javax.swing.*;

/**
 * Graphical User Interface for the EcoSafari application.
 * Renders the 2D simulation grid and provides user interaction controls.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafariGUI extends JFrame{

    public static final int SIDE = 20;
    public final int SIZE;
    private JButton ticTacButton;
    private JPanel controlPanel;
    private PhotoEcoSafari photo;
    private EcoSafari theEcoSafari;
    private JMenuBar menuBar;
    private JMenu menuArchivo;

    private JMenuItem itemNuevo;
    private JMenuItem itemAbrir;
    private JMenuItem itemSalvarComo;
    private JMenuItem itemImportar;
    private JMenuItem itemExportarComo;
    private JMenuItem itemSalir;

    /*
     * Private constructor initializing the EcoSafari GUI.
     */
    private EcoSafariGUI() {
        theEcoSafari = new EcoSafari();
        SIZE = theEcoSafari.getSize();
        prepareElements();
        prepareActions();
    }

    /*
     * Prepares GUI visual elements and layout.
     */
    private void prepareElements() {
        setTitle("EcoSafari");
        photo = new PhotoEcoSafari(this);
        ticTacButton = new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);
        add(ticTacButton, BorderLayout.SOUTH);
        setSize(new Dimension(SIDE * SIZE + 15, SIDE * SIZE + 72));
        setResizable(false);
        prepareViewMenu();
        photo.repaint();
    }

    /*
     * Binds user events and listeners to actions.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ticTacButton.addActionListener(e -> ticTacButtonAction());
        prepareControllerMenu();
    }

    /*
     * Builds the Archivo menu with the options New, Open, Save as, Import,
     * Export as and Exit, separated by groups.
     */
    private void prepareViewMenu(){
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");

        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemSalvarComo = new JMenuItem("Salvar como");
        itemImportar = new JMenuItem("Importar");
        itemExportarComo = new JMenuItem("Exportar como");
        itemSalir = new JMenuItem("Salir");

        menuArchivo.add(itemNuevo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemSalvarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemImportar);
        menuArchivo.add(itemExportarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    /*
     * Binds each Archivo menu option to its controller method.
     */
    private void prepareControllerMenu(){
        itemNuevo.addActionListener(e -> optionNew());
        itemAbrir.addActionListener(e -> optionOpen());
        itemSalvarComo.addActionListener(e -> optionSaveAs());
        itemImportar.addActionListener(e -> optionImport());
        itemExportarComo.addActionListener(e -> optionExportAs());
        itemSalir.addActionListener(e -> optionExit());
    }

    /*
     * Creates a new empty EcoSafari and refreshes the view.
     */
    private void optionNew(){
        theEcoSafari = new EcoSafari();
        photo.repaint();
    }

    /*
     * Asks for a .dat file and loads the saved EcoSafari, showing any error found.
     */
    private void optionOpen(){
        JFileChooser chooser = new JFileChooser();
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try {
                theEcoSafari.open(chooser.getSelectedFile());
                photo.repaint();
            } catch (EcoSafariException ese) {
                JOptionPane.showMessageDialog(this, ese.getMessage());
            }
        }
        
    }

    /*
     * Asks for a .dat file and saves the current EcoSafari, showing any error found.
     */
    private void optionSaveAs(){
        JFileChooser chooser = new JFileChooser();
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try {
                theEcoSafari.saveAs(chooser.getSelectedFile());
            } catch (EcoSafariException ese) {
                JOptionPane.showMessageDialog(this, ese.getMessage());
            }
        }
    }

    /*
     * Asks for a .txt file and imports its entities, showing any error found.
     */
    private void optionImport(){
        JFileChooser chooser = new JFileChooser();
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try {
                theEcoSafari.import_(chooser.getSelectedFile());
                photo.repaint();
            } catch (EcoSafariException ese) {
                JOptionPane.showMessageDialog(this, ese.getMessage());
            }
        }
    }

    /*
     * Asks for a .txt file and exports the current EcoSafari, showing any error found.
     */
    private void optionExportAs(){
        JFileChooser chooser = new JFileChooser();
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try {
                theEcoSafari.exportAs(chooser.getSelectedFile());
            } catch (EcoSafariException ese) {
                JOptionPane.showMessageDialog(this, ese.getMessage());
            }
        }
    }

    /*
     * Ends the application.
     */
    private void optionExit(){
        System.exit(0);
    }

    /*
     * Action handler invoked when the Tic-tac button is clicked.
     */
    private void ticTacButtonAction() {
        theEcoSafari.ticTac();
        photo.repaint();
    }

    /**
     * Returns the underlying EcoSafari domain simulation.
     *
     * @return the EcoSafari model
     */
    public EcoSafari getTheEcoSafari() {
        return theEcoSafari;
    }

    /**
     * Main entry point for launching the EcoSafari application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        EcoSafariGUI cg = new EcoSafariGUI();
        cg.setVisible(true);
    }

    /**
     * Custom JPanel for rendering the visual representation of the EcoSafari grid.
     */
    class PhotoEcoSafari extends JPanel {

        private EcoSafariGUI gui;

        /**
         * Constructs a PhotoEcoSafari canvas panel.
         *
         * @param gui parent frame
         */
        public PhotoEcoSafari(EcoSafariGUI gui) {
            this.gui = gui;
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(EcoSafariGUI.SIDE * gui.SIZE + 10, EcoSafariGUI.SIDE * gui.SIZE + 10));
        }

        /**
         * Paints the EcoSafari grid lines and all living entities.
         *
         * @param g the Graphics context
         */
        public void paintComponent(Graphics g) {
            EcoSafari theEcoSafari = gui.getTheEcoSafari();
            super.paintComponent(g);

            for (int c = 0; c <= theEcoSafari.getSize(); c++) {
                g.drawLine(c * EcoSafariGUI.SIDE, 0, c * EcoSafariGUI.SIDE, theEcoSafari.getSize() * EcoSafariGUI.SIDE);
            }
            for (int r = 0; r <= theEcoSafari.getSize(); r++) {
                g.drawLine(0, r * EcoSafariGUI.SIDE, theEcoSafari.getSize() * EcoSafariGUI.SIDE, r * EcoSafariGUI.SIDE);
            }
            for (int r = 0; r < theEcoSafari.getSize(); r++) {
                for (int c = 0; c < theEcoSafari.getSize(); c++) {
                    if (theEcoSafari.get(r, c) != null) {
                        g.setColor(theEcoSafari.get(r, c).getColor());
                        if (theEcoSafari.get(r, c).shape() == Entity.SQUARE) {
                            g.fillRoundRect(EcoSafariGUI.SIDE * c + 1, EcoSafariGUI.SIDE * r + 1, EcoSafariGUI.SIDE - 2, EcoSafariGUI.SIDE - 2, 2, 2);
                        } else {
                            g.fillOval(EcoSafariGUI.SIDE * c + 1, EcoSafariGUI.SIDE * r + 1, EcoSafariGUI.SIDE - 2, EcoSafariGUI.SIDE - 2);
                        }
                        if (theEcoSafari.get(r, c).isOrganism()) {
                            g.setColor(Color.RED);
                            if (((Organism) theEcoSafari.get(r, c)).getEnergy() >= 50) {
                                g.drawString("+", EcoSafariGUI.SIDE * c + 6, EcoSafariGUI.SIDE * r + 15);
                            } else {
                                g.drawString("~", EcoSafariGUI.SIDE * c + 6, EcoSafariGUI.SIDE * r + 17);
                            }
                        }
                    }
                }
            }
        }

    }
}
