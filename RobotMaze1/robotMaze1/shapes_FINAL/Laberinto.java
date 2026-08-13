import java.util.Scanner;
/**
 * Write a description of class Laberinto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laberinto
{
    // instance variables - replace the example below with your own
    private int tamano;
    private int[][] mapeo;
    private int[] entradaSalida;
    private Robot robot;
    private Pared[] paredes;
    /**
     * Constructor for objects of class Laberinto
     */
    public Laberinto(int tamano)
    {
        this.tamano = tamano;
        entradaSalida = randombordes();
        mapeo = new int[tamano][tamano];
        paredes = new Pared[20];
    }
    
    private int[] randombordes(){

        while (true){
            int xDato = (int)(Math.random()*tamano);
            int yDato = (int)(Math.random()*tamano);
            int aux = (int)(Math.random()*tamano);
            if ((xDato == 0 || xDato == tamano-1)){
                int[] retorno = {xDato,yDato,(int)(Math.abs(xDato-(tamano-1))),aux};
                return retorno;
                
            }else if(yDato == 0 || yDato == tamano-1){
                int[] retorno2 = {xDato,yDato,aux,(int)(Math.abs(yDato-(tamano-1)))};
                return retorno2;    
            }
       }
    }
    
    private char directionEntrada(){
        char direccion = '0';
        if(entradaSalida[0] == 0){
            direccion = 'O';
        }else if(entradaSalida[0] == (tamano-1)){
            direccion = 'E';
        }else if(entradaSalida[1] == 0){
            direccion = 'S';
        }else if(entradaSalida[1] == (tamano-1)){
            direccion = 'N';
        }
        
        return direccion;
    }
    

    
    private void iniciarEntradaSalida(){
        Rectangle entrada = new Rectangle(entradaSalida[0]*20, entradaSalida[1]*20, 25);
        Rectangle salida = new  Rectangle(entradaSalida[2]*20, entradaSalida[3]*20, 25);
        entrada.makeVisible();
        entrada.changeColor("green");
        salida.makeVisible();
        salida.changeColor("red");
        
    }
    
    private void iniciarRobot(){
        robot = new Robot(entradaSalida[0], entradaSalida[1], directionEntrada());
        robot.makeVisible();
        robot.color();
        
        
    }
    
    private void rellenarPared(int posX, int posY, int longitud, char orientacion){
        int relleno;
        if(orientacion == 'V'){
            for(relleno = 0; relleno < longitud; relleno++){
                mapeo[posX][posY+relleno] = 1;
            }
        }else if(orientacion == 'H'){
            for(relleno = 0; relleno < longitud; relleno++){
                mapeo[posX+relleno][posY] = 1;
            }            
        }
        
    }
    
    private void manejoColisiones(){
        robot.colision();
    }
    
    
    public void jugar(){
        String tecla = " ";
        Scanner tecladoJ = new Scanner(System.in);
        Scanner entradaParedes = new Scanner(System.in);
        int contador = 0;
        Canvas pantalla = Canvas.getCanvas();
        iniciarEntradaSalida();
        iniciarRobot();
            
        
        
        while(!tecla.equals("J")){
            tecla = tecladoJ.next();

            if(tecla.equals("P")){
                int posicionx = entradaParedes.nextInt();
                int posiciony = entradaParedes.nextInt();
                int longitud = entradaParedes.nextInt();
                char orientacion = entradaParedes.next().charAt(0);
                paredes[contador] = new Pared(posicionx, posiciony, longitud, orientacion);
                paredes[contador].dibujarPared();
                paredes[contador].cColor("black");
                rellenarPared(posicionx, posiciony, longitud, orientacion);
                contador+=1;
                
                
            }
        }
        
        
        
        
        
        
        while(true){
            tecla = tecladoJ.next();
            if(tecla.equals("W")){
                if(robot.direction != 'N'){
                    robot.direction = 'N';
                }else{
                    if(mapeo[robot.coordinates()[0]][robot.coordinates()[1]-1] == 0){
                        robot.move();
                    
                    }else{
                        manejoColisiones();
                    }
            }
        }
            
            if(tecla.equals("A")){
                if(robot.direction != 'W'){
                    robot.direction = 'W';
                }else{
                        if(mapeo[robot.coordinates()[0]-1][robot.coordinates()[1]] == 0){
                            robot.move();
                        
                        }else{
                            manejoColisiones();
                        }
            }
        }
            
            if(tecla.equals("S")){
                if(robot.direction != 'S'){
                    robot.direction = 'S';
                }else{
                        if(mapeo[robot.coordinates()[0]][robot.coordinates()[1]+1] == 0){
                            robot.move();
                        
                        }else{
                            manejoColisiones();
                        }
                }
            }
            
            if(tecla.equals("D")){
                if(robot.direction != 'E'){
                    robot.direction = 'E';
                }else{
                        if(mapeo[robot.coordinates()[0]+1][robot.coordinates()[1]] == 0){
                            robot.move();
                        
                        }else{
                              manejoColisiones();
                        }
        }
    }
    } 
}
}



    
    
    
