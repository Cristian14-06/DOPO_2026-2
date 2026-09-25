package domain;
import java.awt.Color;


/**
 * Write a description of class Zebra here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zebra extends Organism implements Entity{
    private EcoSafari habitat;
    private boolean hasActed;
    
    public Zebra(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasActed = false;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    public final Color getColor(){
        return Color.BLACK;
    }
    
    public void tic(){
        int[] posicion_inicial = habitat.find(this);
        int fila = posicion_inicial[0];
        int columna = posicion_inicial[1];
    
        if(habitat.isInside(fila - 2, columna)
            && habitat.get(fila - 2, columna) != null
            && habitat.get(fila - 2, columna).type().equals("Tierra")
            && !hasActed){
    
            Entity tierra = habitat.get(fila - 2, columna);
            move(-2, 0);
            habitat.set(tierra, fila, columna);
            changeEnergy(-10);
    
            if(getEnergy() == 0){
                disappear();
            }
    
        }else if(habitat.isInside(fila + 2, columna)
            && habitat.get(fila + 2, columna) != null
            && habitat.get(fila + 2, columna).type().equals("Tierra")
            && !hasActed){
    
            Entity tierra = habitat.get(fila + 2, columna);
            move(2, 0);
            habitat.set(tierra, fila, columna);
            changeEnergy(-10);
    
            if(getEnergy() == 0){
                disappear();
            }
    
        }else if(habitat.isInside(fila, columna - 2)
            && habitat.get(fila, columna - 2) != null
            && habitat.get(fila, columna - 2).type().equals("Tierra")
            && !hasActed){
    
            Entity tierra = habitat.get(fila, columna - 2);
            move(0, -2);
            habitat.set(tierra, fila, columna);
            changeEnergy(-10);
    
            if(getEnergy() == 0){
                disappear();
            }
    
        }else if(habitat.isInside(fila, columna + 2)
            && habitat.get(fila, columna + 2) != null
            && habitat.get(fila, columna + 2).type().equals("Tierra")
            && !hasActed){
    
            Entity tierra = habitat.get(fila, columna + 2);
            move(0, 2);
            habitat.set(tierra, fila, columna);
            changeEnergy(-10);
    
            if(getEnergy() == 0){
                disappear();
            }
        }
        posicion_inicial = habitat.find(this);
        fila = posicion_inicial[0];
        columna = posicion_inicial[1];
        int [][]vecinos = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
        for(int[] vecino : vecinos){
            int temp_row = fila + vecino[0];
            int temp_column = columna + vecino[1];
            Entity entity = habitat.get(temp_row, temp_column);
            if(habitat.isInside(temp_row, temp_column)){
                if(entity.type().equals("Pasto")){
                    entity.disappear();
                    changeEnergy(0.25f);
                    //inicializar una tierra en la posicion comida;
                    
                }
            
            }
        }
    
        hasActed = true;

    }
    
    public void tac(){
        hasActed = false;
    }
    
    
}