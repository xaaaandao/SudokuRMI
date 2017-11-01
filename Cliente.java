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

            SudokuGUI sudokuGUI = new SudokuGUI();
            sudokuGUI.buildWindowSudoku(sudoku, sudoku.matrixFields(), sudoku.matrixForPlayer());

    	} catch (RemoteException re) {
        
        } catch (NotBoundException n) {
        
        } catch (Exception e) {
    	
        }
    }
}
