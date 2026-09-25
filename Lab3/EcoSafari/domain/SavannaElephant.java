package domain;


/**
 * Write a description of class SavannaElephant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SavannaElephant extends Elephant{
    public SavannaElephant(EcoSafari habitat, int row, int column){
        super(habitat, row, column);
    }
    
    @Override
    public void tic(){
        if ((!getHasActed()) && (move(1, 1))) {
            changeEnergy(-5);
            if (getEnergy()==0){
                disappear();
            }
        }
        setHasActed(true);
        EcoSafari habitat = getHabitat();
        int[] posicion_actual = habitat.find(this);
        int fila;
        int columna;
        
        if(posicion_actual != null){
            fila = posicion_actual[0];
            columna = posicion_actual[1];
    
            if(habitat.isInside(fila - 1, columna)){
                Entity vecino1 = habitat.get(fila - 1, columna);
                if(vecino1 != null && vecino1.isShrub()){
                    changeEnergy(10);
                }
            }
    
            if(habitat.isInside(fila + 1, columna)){
                Entity vecino2 = habitat.get(fila + 1, columna);
                if(vecino2 != null && vecino2.isShrub()){
                    changeEnergy(10);
                }
            }
    
            if(habitat.isInside(fila, columna - 1)){
                Entity vecino3 = habitat.get(fila, columna - 1);
                if(vecino3 != null && vecino3.isShrub()){
                    changeEnergy(10);
                }
            }
    
            if(habitat.isInside(fila, columna + 1)){
                Entity vecino4 = habitat.get(fila, columna + 1);
                if(vecino4 != null && vecino4.isShrub()){
                    changeEnergy(10);
                }
            }
        }
    }
}