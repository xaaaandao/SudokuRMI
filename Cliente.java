import java.rmi.*;
import java.rmi.registry.*;

public class Cliente {
	
	public static void printMatrix(int [][]matrix) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	public static void main(String args[]){
    	try {
            if (System.getSecurityManager() == null) {
               System.setSecurityManager(new SecurityManager());
            }

            /* Obtém a referência para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            SudokuInterface sudoku = (SudokuInterface)registry.lookup("JogoSudoku");

            SudokuGUI s = new SudokuGUI();
            s.waitSudoku();
            int [][]matrix = sudoku.matrixForUser();
            //System.out.println(matrix);
            printMatrix(matrix);
        } catch (Exception e) {
           System.out.println(e);
        }
    }
}
