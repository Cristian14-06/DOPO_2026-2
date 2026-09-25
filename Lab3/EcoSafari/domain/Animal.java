package domain;
import java.awt.Color;


/**
 * Write a description of class Animal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Animal extends Organism implements Entity{
    private EcoSafari habitat;
    private boolean hasActed;
    
    public Animal(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasActed = false;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    public abstract Color getColor();
    
    public abstract void tic();
    
    protected void moveAnimal(int steps){
            int[] posicion_inicial = habitat.find(this);
            int fila = posicion_inicial[0];
            int columna = posicion_inicial[1];
        
            if(habitat.isInside(fila - steps, columna)
                && habitat.get(fila - steps, columna) != null
                && habitat.get(fila - steps, columna).type().equals("Ground")
                && !hasActed){
        
                Entity tierra = habitat.get(fila - steps, columna);
                move(-1, 0);
                habitat.set(tierra, fila, columna);
                changeEnergy(-10);
        
                if(getEnergy() == 0){
                    disappear();
                }
        
            }else if(habitat.isInside(fila + 1, columna)
                && habitat.get(fila + steps, columna) != null
                && habitat.get(fila + steps, columna).type().equals("Ground")
                && !hasActed){
        
                Entity tierra = habitat.get(fila + 1, columna);
                move(steps, 0);
                habitat.set(tierra, fila, columna);
                changeEnergy(-10);
        
                if(getEnergy() == 0){
                    disappear();
                }
        
            }else if(habitat.isInside(fila, columna - 1)
                && habitat.get(fila, columna - steps) != null
                && habitat.get(fila, columna - steps).type().equals("Ground")
                && !hasActed){
        
                Entity tierra = habitat.get(fila, columna - steps);
                move(0, -steps);
                habitat.set(tierra, fila, columna);
                changeEnergy(-10);
        
                if(getEnergy() == 0){
                    disappear();
                }
        
            }else if(habitat.isInside(fila, columna + steps)
                && habitat.get(fila, columna + steps) != null
                && habitat.get(fila, columna + steps).type().equals("Ground")
                && !hasActed){
        
                Entity tierra = habitat.get(fila, columna + steps);
                move(0, steps);
                habitat.set(tierra, fila, columna);
                changeEnergy(-10);
        
                if(getEnergy() == 0){
                    disappear();
                }
            }
    }
    
    protected void eat(String food, float porcentage){
        int[] posicion_inicial = habitat.find(this);
        int fila = posicion_inicial[0];
        int columna = posicion_inicial[1];
        int [][]vecinos = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
        for(int[] vecino : vecinos){
            int temp_row = fila + vecino[0];
            int temp_column = columna + vecino[1];
            Entity entity = habitat.get(temp_row, temp_column);
            if(habitat.isInside(temp_row, temp_column)){
                if(entity.type().equals(food)){
                    entity.disappear();
                    changeEnergy(porcentage);
                    
                    
                }
            
            }
        }
    }
    
    public void tac(){
        hasActed = false;
    }
}
