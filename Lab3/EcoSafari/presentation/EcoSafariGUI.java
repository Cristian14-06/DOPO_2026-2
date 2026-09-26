package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Interface for the EcoSafari application.
 * Renders the 2D simulation grid and provides user interaction controls.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafariGUI extends JFrame {

    /** Pixel side length for each cell in the grid */
    public static final int SIDE = 20;

    /** Total grid dimension size */
    public final int SIZE;

    /* Button triggering the simulation tic-tac step */
    private JButton ticTacButton;

    /* Panel container for controls */
    private JPanel controlPanel;

    /* Canvas panel displaying the EcoSafari grid */
    private PhotoEcoSafari photo;

    /* The EcoSafari domain model instance */
    private EcoSafari theEcoSafari;

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
        photo.repaint();
    }

    /*
     * Binds user events and listeners to actions.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ticTacButton.addActionListener(e -> ticTacButtonAction());
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

        /* Reference to the parent GUI frame */
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