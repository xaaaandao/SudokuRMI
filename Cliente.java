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

<<<<<<< HEAD
            SudokuGUI s = new SudokuGUI();
            s.buildWindowSudoku(sudoku, sudoku.matrixForUser());

    	} catch (RemoteException re) {
           System.out.println(re);
        } catch (NotBoundException n) {
            System.out.println(n);
        } catch (Exception e) {
        	System.out.println(e);
=======
            SudokuGUI sudokuGUI = new SudokuGUI();
            sudokuGUI.buildWindowSudoku(sudoku, sudoku.matrixForUser());

    	} catch (RemoteException re) {
        
        } catch (NotBoundException n) {
        
        } catch (Exception e) {
    	
>>>>>>> xandao
        }
    }
}
