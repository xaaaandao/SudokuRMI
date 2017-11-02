import java.rmi.RemoteException;
import java.util.*;

public class SudokuUpdate extends TimerTask {
	
	int rows = 9;
	int columns = 9;
	SudokuInterface s;
	int [][]matrixUpdate;
	
	public void copyMatrix(int[][] dest,  int[][] src) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	public SudokuUpdate(SudokuInterface sudoku) {
		s = sudoku;
		matrixUpdate = new int[rows][columns];
	}
	
	public void printMatrix(int [][]matrix) {
		System.out.println("Classe a parte");
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
    public void run() {
    	try {
    		printMatrix(s.matrixForPlayer());
    		setMatrix(s.matrixForPlayer());
    	} catch (RemoteException re) {
    		
    	}
     }
    
    public void setMatrix(int [][]matrix){
    	copyMatrix(matrixUpdate, matrix);
    }
    
    public int[][] getMatrix(){
    	return matrixUpdate;
    }
    
    
}
