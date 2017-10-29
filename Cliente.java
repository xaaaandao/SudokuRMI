import java.rmi.*;
import java.rmi.registry.*;

public class Cliente {
	public static void main(String args[]){
    	try {
            if (System.getSecurityManager() == null) {
               System.setSecurityManager(new SecurityManager());
            }

            /* Obtém a referência para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            SudokuInterface sudoku = (SudokuInterface)registry.lookup("JogoSudoku");

            SudokuGUI s = new SudokuGUI();
            s.buildWindowSudoku(sudoku.matrixForUser());
            System.out.println("tessste");
        } catch (Exception e) {
           System.out.println(e);
        }
    }
}
