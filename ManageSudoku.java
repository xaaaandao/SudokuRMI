import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ManageSudoku extends UnicastRemoteObject implements SudokuInterface{
	
	List<Sudoku> listOfSudoku;
	static int idSudoku;
	
	public ManageSudoku() throws RemoteException {
        super();
        listOfSudoku = new ArrayList<>();
        generateListOfSudoku();
        idSudoku = 0;
        System.out.println("Objeto remoto instanciado");
    }
	
	public void generateListOfSudoku() {
		int numberOfSudoku = 9;
		int numberOfHide = 0;
		for(int i = 0; i < numberOfSudoku; i++) {
			Sudoku sudoku = new Sudoku();
			if(i >= 0 && i <= 2) {
				numberOfHide = 3;
			} else if(i >= 3 && i <= 5) {
				numberOfHide = 5;
			} else {
				numberOfHide = 7;
			}
			if(i == 0) {
				sudoku = sudoku.generateSudoku(i, numberOfHide);
			} else {
				sudoku = sudoku.generateSudoku(listOfSudoku.get(listOfSudoku.size() - 1).matrixAnswers[0][0], numberOfHide);
			}
			listOfSudoku.add(sudoku);
		}
	}
	
	public int [][]matrixForPlayer(int id) throws RemoteException {
    	return listOfSudoku.get(id).matrixPlayer;
    }
    

	public int [][]matrixFields(int id) throws RemoteException {
		return listOfSudoku.get(id).matrixFields;
	}
	
	public void replaceValue(int id, int value, int i, int j) throws RemoteException{
    	listOfSudoku.get(id).matrixPlayer[i][j] = value;
    }
    
	public int countFillFields(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countFieldsEmpty(listOfSudoku.get(id).matrixPlayer);
    }
	
	public int numberOfHits(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countHits(listOfSudoku.get(id).matrixFields, listOfSudoku.get(id).matrixPlayer, listOfSudoku.get(id).matrixAnswers);
    }
	
	public int numberOfErrors(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countErrors(listOfSudoku.get(id).matrixFields, listOfSudoku.get(id).matrixPlayer, listOfSudoku.get(id).matrixAnswers);
    }
	
	public int checkInput(int id, int value, int i, int j) throws RemoteException{
    	/* Verifico se a posição que o usuário está tentando inserir está vazia */
    	if(listOfSudoku.get(id).matrixPlayer[i][j] == 0) {
    		/* Insiro na matriz */
    		listOfSudoku.get(id).matrixPlayer[i][j] = value;
    		return 1;
    	/* Caso a posição já está preenchida */
    	} else {
    		/* Verifico se eu quero sobrescrever com o meu valor */
    		/* Se sim coloco o valor que o usuário quer e devolvo para todos os meus jogadores */
    		if(listOfSudoku.get(id).matrixPlayer[i][j] == value){
        		return 2;	
    		}
    		return 3;
    	}
	}
	
	public int getOldValue(int id, int i, int j) throws RemoteException{
		return listOfSudoku.get(id).matrixPlayer[i][j];
	}	
	
}
