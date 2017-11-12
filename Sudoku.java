import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import com.sun.glass.ui.Robot;

public class Sudoku{

	int [][]matrixAnswers;
	int [][]matrixFields;
	int [][]matrixPlayer;
	int rows = 9;
	int columns = 9;
	
	/**
	 * O método copyMatrix(int[][] dest, int[][] src), recebe duas matrizes uma de origem e outra de destino,
	 * e é copiado os valores presentes na matriz de origem para um matriz de destino.
	 * @param dest arranjo bidimensional de inteiros que terá valores iguais aos valores da matriz de origem.
	 * @param src arranjo bidimensional de inteiros terá seus valores copiados para uma outra matriz.
	 * @return void.
	 * */
	public void copyMatrix (int[][] dest,  int[][] src) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	/**
	 * O método printMatrix(int [][]matrix), imprime a variável matrix.
	 * @param matrix arranjo bidimensional de inteiros que será impresso na tela.
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
	 * O método countFieldsEmpty(), conta a quantidade campos vazios presentes no sudoku 
	 * de jogadores e retorna esse valor.
	 * @param matrix arranjo bidimensional com os valores que será verificados quandos valores ainda que não foram preenchidos.
	 * @return count é um inteiro com a quantidade de campos que estão vazio.
	 * */
	public int countFieldsEmpty(int [][]matrix) {
		int count = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrix[i][j] == 0)
					count++;
			}
		}
		return count;
	}
	
	/**
	 * O método nextNumber(int number), dependendo do valor de number que está na posição
	 * do sudoku irá retornar o valor que será para utilizado na próxima posição.
	 * @param number é um inteiro que será verificado qual valores será retornado.
	 * @return 1 é um inteiro, se o valor da posição atual é 7.
	 * @return 2 é um inteiro, se o valor da posição atual é 8.
	 * @return 3 é um inteiro, se o valor da posição atual é 9.
	 * @return number + 3 é um inteiro com o valor que foi passado por parâmetro mais três.
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
	 * O método fillFirstNumber(int currentValue, int [][]matrix), gera um valor entre 1 a 
	 * 9 e preenche com o valor gerado, somente a primeira coluna da matriz.
	 * @param currentValue é um inteiro com o valor atual que será verificado qual será seu 
	 * próximo que deve ser preenchido na primeira coluna.
	 * @param matrix arranjo bidimensional de inteiros com os valores que foram preenchidos.
	 * @return void.
	 * */
	public void fillFirstColumnMatrix(int currentValue, int [][]matrix){
		int randomNumber;
		if(currentValue == 0) {
			randomNumber = new Random().nextInt((9 - 1) + 1) + 1;
		} else {
			randomNumber = currentValue;
			randomNumber++;
			if(randomNumber == 10)
				randomNumber = 1;
		}
		for(int i = 0; i < rows; i=i+3) {
			matrix[i][0] = randomNumber;
			matrix[i+1][0] = nextNumber(matrix[i][0]);
			matrix[i+2][0] = nextNumber(matrix[i+1][0]);
			randomNumber++;
			if(randomNumber == 10)
				randomNumber = 1;			
		}
	}
	
	
	
	/**
	 * O método fillOtherPositions(int [][]matrix), preenche a matriz com as colunas 1 a 8.
	 * @param matrix arranjo bidimensional com os valores que será preenchido nas demais colunas.
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
	 * @param dst arranjo bidimensional de inteiros que possui os mesmos valores do arranjo bidimensional de inteiros src.
	 * @param src arranjo bidimensional de inteiros que terá os valores que copiados para outro arranjo bidimensional.
	 * @param numberOfHide é um inteiro com a quantidade de campos que queremos que sejam preenchidos pelo jogador.
	 * @return void.
	 * */
	public void hidePositionsMatrix(int [][]dst, int [][]src, int numberOfHide) {
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				dst[i][j] = src[i][j];
			}
            String posix = "012345678";
            for (int j = 0; j < numberOfHide; j++) {
                int randomNumber = new Random().nextInt((posix.length() - 0) + 0) + 0;
                dst[i][Character.getNumericValue(posix.charAt(randomNumber))] = 0;
                posix = posix.substring(0, randomNumber) + posix.substring(randomNumber+1);
            }
        }
	}
	
	/**
	 * O método countHit((int [][]sudokuFields, int [][]sudokuPlayer, int [][]sudokuAnswers) que pode ser 
	 * invocado remotamente, e conta a quantidade de acertos da matriz preenchida pelo usuário comparando
	 * com a matriz de resposta.
	 * @param sudokuFields arranjo bidimensional de inteiros com os campos que devem ser preenchidos pelo jogador.
	 * @param sudokuPlayer arranjo bidimensional de inteiros com os campos que já foram preenchidos pelo jogador.
	 * @param sudokuAnswers arranjo bidimensional de inteiros com os valores que esperamos dos usuários.
	 * @return count é um inteiro com a quantidade de acertos.
	 * */
	public int countHits(int [][]sudokuFields, int [][]sudokuPlayer, int [][]sudokuAnswers){
		int hit = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(sudokuFields[i][j] == 0) {
					if(sudokuPlayer[i][j] == sudokuAnswers[i][j]) {
						hit++;
					}
				}
			}
		}
    	return hit;
    }

	/**
	 * O método countError(int [][]sudokuFields, int [][]sudokuPlayer, int [][]sudokuAnswers) que pode ser invocado remotamente, e conta a quantidade 
	 * de erros da matriz preenchida pelo usuário comparando com a matriz de resposta.
	 * @param sudokuFields arranjo bidimensional de inteiros com os campos que devem ser preenchidos pelo jogador.
	 * @param sudokuPlayer arranjo bidimensional de inteiros com os campos que já foram preenchidos pelo jogador.
	 * @param sudokuAnswers arranjo bidimensional de inteiros com os valores que esperamos dos usuários.
	 * @return error é um inteiro com a quantidade de erros.
	 * */
	public int countErrors(int [][]sudokuFields, int [][]sudokuPlayer, int [][]sudokuAnswers){
		int error = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(sudokuFields[i][j] == 0) {
					if(sudokuPlayer[i][j] != sudokuAnswers[i][j]) {
						error++;
					}
				}
			}
		}
    	return error;
    }

	public boolean transposedOrNo() {
		int randomNumber = new Random().nextInt((2 - 0) + 0) + 0;
        if(randomNumber == 1)
        	return true;
        return false;
	}
	
    public void tranposedMatrix(int [][] matrix){
        int[][] newMatrix  = new int[9][9];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
            	newMatrix[j][i] = matrix[i][j];
            }
        }
        copyMatrix(matrix, newMatrix);
    }
	
	/**
	 * O método generateSudoku(int currentValue, int numberOfHide), gera três matrizes uma com os campos,
	 * outra com os valores que foram preenchidos pelos usuário e outro com a matriz de resposta.
	 * @param currentValue é um inteiro com um valor que auxiliar o valor que deve ser preenchido no sudoku.
	 * @param numberOfHide é um inteiro com a quantidade de campos que devem ser preenchidos por linha.
	 * @return sudoku retorna um objeto do tipo Sudoku, com os três arranjos bidimensionais inicializados.
	 * */
	public Sudoku generateSudoku(int currentValue, int numberOfHide) {
		Sudoku sudoku = new Sudoku();
		sudoku.matrixAnswers = new int[rows][columns];
		sudoku.matrixFields = new int[rows][columns];
		sudoku.matrixPlayer = new int[rows][columns];
		fillFirstColumnMatrix(currentValue, sudoku.matrixAnswers);
		fillOtherPositions(sudoku.matrixAnswers);
		if(transposedOrNo() == true){
			tranposedMatrix(sudoku.matrixAnswers);
		}
		hidePositionsMatrix(sudoku.matrixFields, sudoku.matrixAnswers, numberOfHide);
		copyMatrix(sudoku.matrixPlayer, sudoku.matrixFields);
		return sudoku;
	}

}
