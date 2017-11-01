import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Sudoku extends UnicastRemoteObject implements SudokuInterface{

	static int [][]matrixAnswers;
	static int [][]matrixSended;
	static int [][]matrixPlayer;
	int row = 9;
	int columns = 9;
	
	public Sudoku() throws RemoteException {
        super();
        matrixAnswers = new int[row][columns];
        matrixPlayer = new int[row][columns];
        matrixSended = new int[row][columns];
        matrixAnswers = fillMatrix();
        matrixPlayer = hidePositionsMatrix(matrixAnswers);
        copyMatrix(matrixSended, matrixPlayer);
        System.out.println("Objeto remoto instanciado");
    }
	
	public void copyMatrix(int[][] dest,  int[][] src) {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	public int countFieldsEmpty() {
		int count = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixPlayer[i][j] == 0)
					count++;
			}
		}
		return count;
	}
	
	public void printMatrix(int [][]matrix) {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	public int nextNumber(int number){
		if(number == 7)
			return 1;
		if(number == 8)
			return 2;
		if(number == 9)
			return 3;
		return number = number + 3;
	}
	
	public int [][]fillFirstNumber() {
		int [][]matrix = new int[row][columns];
		int randomNumber = new Random().nextInt((9 - 1) + 1) + 1;
		for(int i = 0; i < row; i=i+3) {
			matrix[i][0] = randomNumber;
			matrix[i+1][0] = nextNumber(matrix[i][0]);
			matrix[i+2][0] = nextNumber(matrix[i+1][0]);
			randomNumber++;
			if(randomNumber == 10)
				randomNumber = 1;			
		}
		return matrix;
	}
	
	public int [][]fillOtherPositions(int [][]matrix) {
		for(int i = 0; i < row; i++) {
			int number = matrix[i][0];
			for(int j = 1; j < columns; j++) {
				number++;
				if(number == 10)
					number = 1;
				matrix[i][j] = number;
			}	
		}
		return matrix;
	}
	
	public int [][]hidePositionsMatrix(int [][]matrixAnswers) {
		int [][]matrixUser = new int[row][columns];
		int numberOfHide = 3;
		for (int i = 0; i < row; i++) {
			for(int j = 0; j < columns; j++) {
				matrixUser[i][j] = matrixAnswers[i][j];
			}
            String posix = "012345678";
            for (int j = 0; j < numberOfHide; j++) {
                int randomNumber = new Random().nextInt((posix.length() - 0) + 0) + 0;
                matrixUser[i][Character.getNumericValue(posix.charAt(randomNumber))] = 0;
                posix = posix.substring(0, randomNumber) + posix.substring(randomNumber+1);
            }
        }
		return matrixUser;
	}

	public int [][]fillMatrix() {
		int [][]matrix = new int[row][columns];
		matrix = fillFirstNumber();
		matrix = fillOtherPositions(matrix);
		return matrix;
	}
	
	public int countHit() throws RemoteException {
		System.out.println("count hit");
        System.out.println("====================");
        printMatrix(matrixPlayer);
        System.out.println("====================");
        printMatrix(matrixSended);
		int hit = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixSended[i][j] == 0) {
					if(matrixPlayer[i][j] == matrixAnswers[i][j]) {
						hit++;
					}
				}
			}
		}
    	return hit;
    }

	public int countError() throws RemoteException {
		System.out.println("count errors");
        System.out.println("====================");
        printMatrix(matrixPlayer);
        System.out.println("====================");
        printMatrix(matrixSended);
		int error = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixSended[i][j] == 0) {
					if(matrixPlayer[i][j] != matrixAnswers[i][j]) {
						error++;
					}
				}
			}
		}
    	return error;
    }
	
	public int [][]matrixForUser() throws RemoteException {
    	return matrixPlayer;
    }
    
    /**
     * return 1 -> Todos os campos estão preenchidos (Pergunto para o cliente se ele deseja conferir a resposta)
     * return 2 -> Valor adicionado com sucesso
     * return 3 -> Verifico se o usuário que sobrescrever o valor daquela posição
     **/
    public int checkInput(int value, int i, int j) throws RemoteException{
    	/* Verifico se a posição que o usuário está tentando inserir está vazia */
    	if(matrixPlayer[i][j] == 0) {
    		/* Insiro na matriz */
    		matrixPlayer[i][j] = value;
			/* Verifico se com essa nova adição todos os campos já estão preenchidos */
    		if(countFieldsEmpty() == 0) {
    			return 1;
    		}
    		return 2;
    	/* Caso a posição já está preenchida */
    	} else {
    		/* Verifico se eu quero sobrescrever com o meu valor */
    		/* Se sim coloco o valor que o usuário quer e devolvo para todos os meus jogadores */
    		return 3;
    	}
    }

}
