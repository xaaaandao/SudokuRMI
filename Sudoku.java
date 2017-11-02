import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Sudoku extends UnicastRemoteObject implements SudokuInterface{

	static int [][]matrixAnswers;
	static int [][]matrixFields;
	static int [][]matrixPlayer;
	int rows = 9;
	int columns = 9;
	
	/**
	 * O construtor Sudoku() instância os três sudoku, o de campo que devem ser preenchidos
	 * o que está sendo preenchido pelos jogadores e o sudoku resposta. Além disso, gera 
	 * o sudoku de maneira aleatoriamente e as posições que devem ser preenchidas de maneira aleatória. 
	 * */
	public Sudoku() throws RemoteException {
        super();
        matrixAnswers = new int[rows][columns];
        matrixPlayer = new int[rows][columns];
        matrixFields = new int[rows][columns];
        matrixAnswers = fillMatrix();
        matrixPlayer = hidePositionsMatrix(matrixAnswers);
        copyMatrix(matrixFields, matrixPlayer);
        System.out.println("Objeto remoto instanciado");
    }
	
	/**
	 * O método copyMatrix(int[][] dest, int[][] src), recebe duas matrizes uma de origem e outra de destino,
	 * e é copiado os valores presentes na matriz de origem para um matriz de destino.
	 * @return void.
	 * */
	public void copyMatrix (int[][] dest,  int[][] src) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	/**
	 * O método countFieldsEmpty(), conta a quantidade campos vazios presentes no sudoku 
	 * de jogadores e retorna esse valor.
	 * @return count é um inteiro com a quantidade de campos que estão vazio.
	 * */
	public int countFieldsEmpty() {
		int count = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixPlayer[i][j] == 0)
					count++;
			}
		}
		return count;
	}
	
	/**
	 * O método printMatrix(int [][]matrix), imprime a variável matrix.
	 * @return void.
	 * */
	public void printMatrix(int [][]matrix) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * O método nextNumber(int number), dependendo do valor de number que está na posição
	 * do sudoku irá retornar o valor que será para utilizado na próxima posição.
	 * @return 1 é um inteiro, se o valor da posição atual é 7.
	 * @return 2 é um inteiro, se o valor da posição atual é 8.
	 * @return 3 é um inteiro, se o valor da posição atual é 9.
	 * */
	public int nextNumber(int number){
		if(number == 7)
			return 1;
		if(number == 8)
			return 2;
		if(number == 9)
			return 3;
		return number = number + 3;
	}
	
	/**
	 * O método fillFirstNumber(), gera um valor entre 1 a 9 e preenche com o valor gerado,
	 * somente a primeira coluna da matriz.
	 * @return matrix com somente a primeira coluna preenchida.
	 * */
	public int [][]fillFirstNumber() {
		int [][]matrix = new int[rows][columns];
		int randomNumber = new Random().nextInt((9 - 1) + 1) + 1;
		for(int i = 0; i < rows; i=i+3) {
			matrix[i][0] = randomNumber;
			matrix[i+1][0] = nextNumber(matrix[i][0]);
			matrix[i+2][0] = nextNumber(matrix[i+1][0]);
			randomNumber++;
			if(randomNumber == 10)
				randomNumber = 1;			
		}
		return matrix;
	}
	
	/**
	 * O método fillOtherPositions(int [][]matrix), preenche a matriz com as colunas 1 a 8.
	 * @return void.
	 * */
	public void fillOtherPositions(int [][]matrix) {
		for(int i = 0; i < rows; i++) {
			int number = matrix[i][0];
			for(int j = 1; j < columns; j++) {
				number++;
				if(number == 10)
					number = 1;
				matrix[i][j] = number;
			}	
		}
	}
	
	/**
	 * O método hidePositionsMatrix(int [][]matrixAnswers), pega a matriz de resposta
	 * que já foi gerada, copia para outra matriz e escolhe três posições aleatórias de cada linha,
	 * que serão marcadas de zero que representa que serão campos que devem ser preenchidos
	 * pelo usuário.
	 * @return matrixUser arranjo bidimensional de inteiros com as posições que devem ser preenchidas pelo jogador.
	 * */
	public int [][]hidePositionsMatrix(int [][]matrixAnswers) {
		int [][]matrixUser = new int[rows][columns];
		int numberOfHide = 3;
		for (int i = 0; i < rows; i++) {
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

	/**
	 * O método fillMatrix(), invoca os demais métodos que preenchem todo o sudoku.
	 * @return matrix arranjo bidimensional de inteiros preenchida com os valores aleatórios.
	 * */
	public int [][]fillMatrix() {
		int [][]matrix = new int[rows][columns];
		matrix = fillFirstNumber();
		fillOtherPositions(matrix);
		return matrix;
	}
	
	/**
	 * O método countHit() que pode ser invocado remotamente, e conta a quantidade 
	 * de acertos da matriz preenchida pelo usuário comparando com a matriz de resposta.
	 * @return count é um inteiro com a quantidade de acertos.
	 * */
	public int countHit() throws RemoteException {
		int hit = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixFields[i][j] == 0) {
					if(matrixPlayer[i][j] == matrixAnswers[i][j]) {
						hit++;
					}
				}
			}
		}
    	return hit;
    }

	/**
	 * O método countError() que pode ser invocado remotamente, e conta a quantidade 
	 * de erros da matriz preenchida pelo usuário comparando com a matriz de resposta.
	 * @return count é um inteiro com a quantidade de erros.
	 * */
	public int countError() throws RemoteException {
		int error = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixFields[i][j] == 0) {
					if(matrixPlayer[i][j] != matrixAnswers[i][j]) {
						error++;
					}
				}
			}
		}
    	return error;
    }
	
	/**
	 * O método matrixForPlayer() que pode ser invocado remotamente, retorna o sudoku
	 * que está sendo preenchido pelos jogadores.
	 * @return matrixPlayer arranjo bidimensional de inteiros preenchida pelos jogadores.
	 * */
	public int [][]matrixForPlayer() throws RemoteException {
    	return matrixPlayer;
    }
    
	/**
	 * O método matrixFields() que pode ser invocado remotamente, retorna o sudoku
	 * que tem os campos que devem ser preenchidos pelos jogadores.
	 * @return matrixPlayer arranjo bidimensional de inteiros que tem as posições que devem ser preenchidas pelos jogadores.
	 * */
	public int [][]matrixFields() throws RemoteException {
    	return matrixFields;
    }
	
	
	/**
	 * O método checkInput(int value, int i, int j) que pode ser invocado remotamente, verifica
	 * se a posição que o usuário deseja está vazia, se sim preenche e verifica se todos os campos
	 * estão preenchidos. Caso contrário retorna verifica se o jogador deseja ou não sobrescrever 
	 * o valor que está lá no sudoku.
	 * @return 1 é um inteiro, que signfica que todos os campos estão preenchidos.
	 * @return 2 é um inteiro, que signfica que o valor foi adiconado com suceso.
	 * @return 3 é um inteiro, que signfica que a posição deseja já está preenchida.
	 * */
	public int checkInput(int value, int i, int j) throws RemoteException{
    	/* Verifico se a posição que o usuário está tentando inserir está vazia */
    	if(matrixPlayer[i][j] == 0) {
    		/* Insiro na matriz */
    		matrixPlayer[i][j] = value;
			/* Verifico se com essa nova adição todos os campos já estão preenchidos */
    		if(countFieldsEmpty() == 0) {
    			return 1;
    		}
    		/* Adicionei o valor, mas todas as posições ainda não estão preenchidas */
    		return 2;
    	/* Caso a posição já está preenchida */
    	} else {
    		/* Verifico se eu quero sobrescrever com o meu valor */
    		/* Se sim coloco o valor que o usuário quer e devolvo para todos os meus jogadores */
    		return 3;
    	}
    }
    
	/**
	 * O método replaceValue(int value, int i, int j) sobrescreve o valor que o jogador inseriu
	 * no sudoku que é preenchido pelos usuários.
	 * @return void.
	 * */
    public void replaceValue(int value, int i, int j) throws RemoteException{
    	matrixPlayer[i][j] = value;
    }
    
	/**
	 * O método countFillFields() simplesmente retorna a quantidade de campos restante que é preenchida 
	 * pelos usuários.
	 * @return um inteiro, com a quantidade de campos que restam na matriz que preenchida pelos usuários.
	 * */
    public int countFillFields() throws RemoteException{
    	return countFieldsEmpty();
    }

}
