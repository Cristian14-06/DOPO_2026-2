package presentation;
import domain.HungrySnake;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.Timer;
import java.awt.Graphics;

import java.awt.GridLayout;

public class HungrySnakeGUI extends JFrame{

   private JMenuBar menuBar;
   private JMenuItem itemNuevo;
   private JMenuItem itemAbrir;
   private JMenuItem itemSalvar;
   private JMenuItem itemSalir;
   private JLabel itemInformativo;
   private JPanel tablero;
   private JButton colorButton;
   private JButton resetButton;
   private Color colorSerpiente;
   private HungrySnake game;
   private Timer timer;
   private char ultimaTecla = ' ';

  public HungrySnakeGUI(){
	 game = new HungrySnake();
     setTitle("HungrySnake");
     prepareView();
     prepareController();     
  }


  public static void main(String[] args){
     HungrySnakeGUI ventana = new HungrySnakeGUI();
     ventana.setVisible(true);
  }

  private void prepareView(){
    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel paraBotones = new JPanel(new GridLayout(1, 2));
    itemInformativo = new JLabel("Tamano de serpiente");
    colorButton = new JButton("cambiar color");
    resetButton = new JButton("resetear");
    colorButton.setFocusable(false);
    resetButton.setFocusable(false);
    setFocusable(true);
    colorSerpiente = Color.BLUE;
    
    
    
    
    int ancho = pantalla.width / 2;
    int alto = pantalla.height / 2;
    
    setSize(ancho, alto);
    setLocationRelativeTo(null);
    prepareViewMenu();
    prepareViewBoard();
    add(itemInformativo, BorderLayout.NORTH);
    paraBotones.add(colorButton);
    paraBotones.add(resetButton);
    add(paraBotones, BorderLayout.SOUTH);
    refresh();

  }

  private void prepareViewMenu() {
        menuBar = new JMenuBar();
 
 
        JMenu menuArchivo = new JMenu("Archivo");
 
        
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemSalvar = new JMenuItem("Salvar");
        itemSalir = new JMenuItem("Salir");
 
       
        menuArchivo.add(itemNuevo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemSalvar);
       
        menuArchivo.addSeparator(); 
       
        menuArchivo.add(itemSalir);
 
        
        menuBar.add(menuArchivo);
 
        
        setJMenuBar(menuBar);
    }
  
  private void prepareViewBoard() {
	  tablero = new JPanel() {
		  @Override
		  protected void paintComponent(Graphics g) {
			  super.paintComponent(g);
			  int n = game.getTamanoTablero();
			  int celda = Math.min(getWidth(), getHeight()) / n;
			  int x0 = (getWidth() - celda * n) / 2;
			  int y0 = (getHeight() - celda * n) / 2;
			  
              g.setColor(Color.WHITE);
              g.fillRect(x0, y0, celda * n, celda * n);                

              g.setColor(colorSerpiente);
              for (int[] p : game.getCulebra())
                      g.fillRect(x0 + p[1] * celda, y0 + p[0] * celda, celda, celda);

              g.setColor(Color.RED);
              for (int[] p : game.getFrutas())
                      g.fillRect(x0 + p[1] * celda, y0 + p[0] * celda, celda, celda);

              g.setColor(Color.BLACK);
              for (int[] p : game.getObstaculos())
                      g.fillRect(x0 + p[1] * celda, y0 + p[0] * celda, celda, celda);

              Toolkit.getDefaultToolkit().sync();
		  }
	  };
      tablero.setBackground(Color.DARK_GRAY);
      add(tablero, BorderLayout.CENTER);
  }

  private void prepareController(){
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter(){
       @Override
       public void windowClosing(WindowEvent e){
           exit();
       }
    });
    
    addKeyListener(new KeyAdapter () {
    	@Override
    	public void keyPressed(KeyEvent k) {
    		char tecla = Character.toUpperCase(k.getKeyChar());
    		if (tecla == 'W' || tecla == 'A' || tecla == 'S' || tecla == 'D') {
    			ultimaTecla = tecla;
    		}
    	}
    });

    timer = new Timer(55, new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		game.move(ultimaTecla);
    		refresh();
    	}
    });
    timer.start();
    
   colorButton.addActionListener(new ActionListener() {
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 changeColor();
	 }
   });
   
    
    prepareControllerMenu();
  }
  
  private void prepareControllerMenu() {
    itemSalir.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           exit();
       }
    });
    
    itemAbrir.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            open();
        }
    });
    
    itemSalvar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }
    });
  }

  private void exit(){
    int opcion = JOptionPane.showConfirmDialog(this, "¿Quiere cerrar la aplicacion?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
    if(opcion == JOptionPane.YES_OPTION){
       System.exit(0);
    }
    requestFocus();
  }
  
  private void open() {
	  JFileChooser selector = new JFileChooser();
	  int respuesta = selector.showOpenDialog(this);
	  if(respuesta == JFileChooser.APPROVE_OPTION) {
		String nombre = selector.getSelectedFile().getName();
		JOptionPane.showMessageDialog(null, "el archivo: " + nombre + " no ha sido posible abrirlo, el funcionamiento esta en construccion");
		
	  }
	  requestFocus();
  }

  private void save() {
	  JFileChooser selector = new JFileChooser();
	  int respuesta = selector.showSaveDialog(this);
	  if(respuesta == JFileChooser.APPROVE_OPTION) {
		String nombre = selector.getSelectedFile().getName();
		JOptionPane.showMessageDialog(null, "el archivo: " + nombre + " no ha sido posible guardarlo, el funcionamiento esta en construccion");
	  }
	  requestFocus();
  }
  
  private void refresh() {
	 tablero.repaint();
     if (ultimaTecla == ' ') {
         itemInformativo.setText("Presiona W, A, S o D para empezar");
     } else {
         itemInformativo.setText("Tamano de serpiente: " + game.getTamano());
     }
     if (!game.isInGame() && timer != null && timer.isRunning()) {
             timer.stop();
             if (game.isWinner()) {
                 JOptionPane.showMessageDialog(this, "¡Ganaste!");
             } else {
                 JOptionPane.showMessageDialog(this, "te moriste");
             }
     }
  }
  
  private void changeColor() {
	  Color temp = JColorChooser.showDialog(this, "cambio de color de la serpiente", colorSerpiente);
	  requestFocus();
	  if(temp == null) {
		  return;
	  }
	  colorSerpiente = temp;
	  refresh();
	  
  }
  
}




