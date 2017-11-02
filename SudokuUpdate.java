import java.rmi.RemoteException;
import java.util.*;

public class SudokuUpdate extends TimerTask {
	
	int rows = 9;
	int columns = 9;
	SudokuInterface s;
	int [][]matrixUpdate;
	
	/**
	 * O método copyMatrix(int[][] dest, int[][] src), recebe duas matrizes uma de origem e outra de destino,
	 * e é copiado os valores presentes na matriz de origem para um matriz de destino.
	 * @return void.
	 * */
	public void copyMatrix(int[][] dest,  int[][] src) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	/**
	 * O construtor SudokuUpdate(SudokuInterface sudoku), atribui o valor sudoku como sendo seu
	 * atributo de s e instância a matriz que irá receber a matriz que está sendo preenchida pelos jogadores
	 * no servidor.
	 * */
	public SudokuUpdate(SudokuInterface sudoku) {
		s = sudoku;
		matrixUpdate = new int[rows][columns];
	}
	
	
	/**
	 * O método printMatrix(int [][]matrix), imprime a variável matrix.
	 * @return void.
	 * */
	public void printMatrix(int [][]matrix) {
		System.out.println("Classe a parte");
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * O método run(), obtêm a matriz que está sendo preenchida
	 * pelos jogadores no servidor e atribui ela como sendo um atributo seu.
	 * @return void.
	 * */
    public void run() {
    	try {
    		//printMatrix(s.matrixForPlayer());
    		setMatrix(s.matrixForPlayer());
    	} catch (RemoteException re) {
    		
    	}
     }
    
	/**
	 * O método setMatrix(int [][]matrix), atribui a matriz que está sendo preenchida pelos jogadores como sendo um atributo seu.
	 * @return void.
	 * */
    public void setMatrix(int [][]matrix){
    	copyMatrix(matrixUpdate, matrix);
    }
    
	/**
	 * O método getMatrix(), retorna o seu atributo matrixUpdate.
	 * @return matrixUpdate arranjo bidimensional de inteiros com a matriz que está sendo preenchida pelos jogadores
	 * */
    public int[][] getMatrix(){
    	return matrixUpdate;
    }
    
    
}
