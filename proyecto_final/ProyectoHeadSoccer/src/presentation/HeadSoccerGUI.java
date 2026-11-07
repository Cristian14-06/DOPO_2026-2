package presentacion;

import domain.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Ventana GUI principal del juego POOBSoccer.
 * Conforme al diagrama UML de presentacion:
 * - game : HeadSoccer
 * - photo : PhotoHeadSoccer
 * + HeadSoccerGUI()
 * + main(args : String[])
 * + keyPressed(e : KeyEvent)
 * + actionPerformed(e : ActionEvent)
 */
public class HeadSoccerGUI extends JFrame implements KeyListener, ActionListener {
    private HeadSoccer game;
    private PhotoHeadSoccer photo;
    private Timer gameLoop;
    private Set<Integer> activeKeys;

    public HeadSoccerGUI() {
        super("POOBSoccer 2026-2 - Head Soccer Arcade");
        this.game = new HeadSoccer();
        this.photo = new PhotoHeadSoccer(game);
        this.activeKeys = new HashSet<>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(photo, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.addKeyListener(this);
        this.setFocusable(true);

        // Bucle a 50 FPS (20 ms por frame)
        this.gameLoop = new Timer(20, this);
        this.gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        processContinuousKeys();
        game.tick();
        photo.repaint();
    }

    private void processContinuousKeys() {
        if (game.getState() != GameStatus.PLAYING && game.getState() != GameStatus.GOLDEN_GOAL) return;

        // Jugador 1
        if (activeKeys.contains(KeyEvent.VK_A)) {
            game.getPlayer1().moveLeft();
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            game.getPlayer1().moveRight();
        }

        // Jugador 2
        if (activeKeys.contains(KeyEvent.VK_LEFT)) {
            game.getPlayer2().moveLeft();
        }
        if (activeKeys.contains(KeyEvent.VK_RIGHT)) {
            game.getPlayer2().moveRight();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        activeKeys.add(code);

        // Opciones de control general
        if (code == KeyEvent.VK_P) {
            game.togglePause();
        } else if (code == KeyEvent.VK_R) {
            game.reset();
        }

        if (game.getState() == GameStatus.PLAYING || game.getState() == GameStatus.GOLDEN_GOAL) {
            // Jugador 1
            if (code == KeyEvent.VK_W) {
                game.getPlayer1().jump();
            } else if (code == KeyEvent.VK_S) {
                game.getPlayer1().kick();
            } else if (code == KeyEvent.VK_Q) {
                game.getPlayer1().usePower(game.getBall(), game.getPlayer2());
            }

            // Jugador 2
            if (code == KeyEvent.VK_UP) {
                game.getPlayer2().jump();
            } else if (code == KeyEvent.VK_DOWN) {
                game.getPlayer2().kick();
            } else if (code == KeyEvent.VK_CONTROL) {
                game.getPlayer2().usePower(game.getBall(), game.getPlayer1());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        activeKeys.remove(code);

        if (code == KeyEvent.VK_S) {
            if (game.getPlayer1() != null) game.getPlayer1().stopKick();
        }
        if (code == KeyEvent.VK_DOWN) {
            if (game.getPlayer2() != null) game.getPlayer2().stopKick();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HeadSoccerGUI ventana = new HeadSoccerGUI();
            ventana.setVisible(true);
        });
    }
}
