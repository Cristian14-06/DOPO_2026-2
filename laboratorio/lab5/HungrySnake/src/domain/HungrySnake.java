package domain;
import java.util.ArrayList;
import java.util.Random;

public class HungrySnake{
	private int tamanoTablero;
	private char actualDirection;
	private boolean inGame;
	private boolean gano;
	private ArrayList<int[]> culebra;
	private ArrayList<int[]> frutas;
	private ArrayList<int[]> obstaculos;
	
	public HungrySnake() {
		tamanoTablero = 25;
		culebra = new ArrayList<>();
		frutas = new ArrayList<>();
		obstaculos = new ArrayList<>();
		culebra.add(new int[]{tamanoTablero / 2, tamanoTablero / 2});
		inGame = true;
		gano = false;
		generarFrutas();
	}
	
	public void move(char direccion) {
		if (!inGame) return;
		Random random = new Random();
		direccion = Character.toUpperCase(direccion);
		if ((direccion == 'A' && actualDirection == 'D') ||
			(direccion == 'D' && actualDirection == 'A') ||
			(direccion == 'W' && actualDirection == 'S') ||
			(direccion == 'S' && actualDirection == 'W')) {
			direccion = actualDirection;
		}
		int[] temp;
		switch (direccion) {
		case 'A':
			temp = new int[]{culebra.get(0)[0], culebra.get(0)[1] - 1};
			break;
		case 'W':
			temp = new int[]{culebra.get(0)[0] - 1, culebra.get(0)[1]};
			break;
		case 'S':
			temp = new int[]{culebra.get(0)[0] + 1, culebra.get(0)[1]};
			break;
		case 'D':
			temp = new int[]{culebra.get(0)[0], culebra.get(0)[1] + 1};
			break;
		default:
			return;
		}
		if(isValid(temp[0], temp[1])){
			actualDirection = direccion;
			culebra.add(0, temp);
			int idxFruta = buscarPosicion(frutas, temp[0], temp[1]);
			if(idxFruta != -1) {
				frutas.remove(idxFruta);
				generarFrutas();
				int alt = random.nextInt(100);
				if(alt < 10) {
					generarObstaculos();
				}
				return;
			}
			culebra.remove(culebra.size() - 1);
		} else {
			inGame = false;
		}
	}
	
	private boolean isValid(int f, int c) {
		if (f >= tamanoTablero || f < 0 || c >= tamanoTablero || c < 0) return false;
		if (buscarPosicion(obstaculos, f, c) != -1) return false;
		if (buscarPosicion(culebra, f, c) != -1 && (culebra.get(culebra.size()-1)[0] != f || culebra.get(culebra.size()-1)[1] != c)) return false;
		return true;
	}

	private int buscarPosicion(ArrayList<int[]> lista, int f, int c) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i)[0] == f && lista.get(i)[1] == c) {
				return i;
			}
		}
		return -1;
	}
	
	private int casillasLibres() {
		int ocupadas = culebra.size() + frutas.size() + obstaculos.size();
		return tamanoTablero * tamanoTablero - ocupadas;
	}

	private void generarFrutas() {
		if (casillasLibres() == 0) {
			gano = true;
			inGame = false;
			return;
		}
		Random random = new Random();
		int status = 0;
		while(status == 0){
			int fila = random.nextInt(tamanoTablero);
			int columna = random.nextInt(tamanoTablero);
			if (buscarPosicion(culebra, fila, columna) == -1 &&
				buscarPosicion(frutas, fila, columna) == -1 &&
				buscarPosicion(obstaculos, fila, columna) == -1) {
				frutas.add(new int[] {fila, columna});
				status = 1;
			}
		}
	}
	
	private void generarObstaculos() {
		if (casillasLibres() == 0) return;
		Random random = new Random();
		int status = 0;
		while(status == 0){
			int fila = random.nextInt(tamanoTablero);
			int columna = random.nextInt(tamanoTablero);
			if (buscarPosicion(culebra, fila, columna) == -1 &&
				buscarPosicion(frutas, fila, columna) == -1 &&
				buscarPosicion(obstaculos, fila, columna) == -1) {
				obstaculos.add(new int[] {fila, columna});
				status = 1;
			}
		}		
	}

	public ArrayList<int[]> getCulebra() {
		return culebra;
	}

	public ArrayList<int[]> getFrutas() {
		return frutas;
	}

	public ArrayList<int[]> getObstaculos() {
		return obstaculos;
	}

	public boolean isInGame() {
		return inGame;
	}

	public boolean isWinner() {
		return gano;
	}

	public int getTamano() {
		return culebra.size();
	}
	
	public int getTamanoTablero() {
		return tamanoTablero;
	}
	
}
