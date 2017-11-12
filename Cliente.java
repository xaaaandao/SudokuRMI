import java.rmi.*;
import java.rmi.registry.*;

/**
 * Descrição: o cliente obtêm a referência remota, solicita duas matrizes uma que contém os campos,
 * e uma matriz que é preenchida pelo usuário, a partir delas ele gera a interface gráfica.
 * Autor: Alexandre Yuji Kajihara
 * Data de criação: 02/11/2017
 * Data de atualização: 12/11/2017
 * */

public class Cliente {
	
	/**
	 * O método main(String args[]) inicializa solicita o objeto remoto, e recebe duas matrizes e montas elas
	 * em uma interface gráfica.
	 * @return void.
	 * */
	public static void main(String args[]){
    	try {
            if (System.getSecurityManager() == null) {
               System.setSecurityManager(new SecurityManager());
            }

            /* Obtém a referência para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            SudokuInterface sudoku = (SudokuInterface)registry.lookup("JogoSudoku");

            /* Monta a interface gráfica a partir dos sudokus recebidos do servidor */
            SudokuGUI sudokuGUI = new SudokuGUI(sudoku);
            /* 0 refere que eu quero o primeiro sudoku da lista presente na lista do servidor */
            int level = 0;
            sudokuGUI.buildWindowSudoku(sudoku, level, sudoku.matrixFields(level), sudoku.matrixForPlayer(level));
            
    	} catch (RemoteException r) {
        
        } catch (NotBoundException n) {
        
        } catch (Exception e) {
    	
        }
    }
}
