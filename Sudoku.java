import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Sudoku{

	int [][]matrixAnswers;
	int [][]matrixFields;
	int [][]matrixPlayer;
	int rows = 9;
	int columns = 9;
	
	public void copyMatrix (int[][] dest,  int[][] src) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	public void printMatrix(int [][]matrix) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
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
	
	public int nextNumber(int number){
		if(number == 7)
			return 1;
		if(number == 8)
			return 2;
		if(number == 9)
			return 3;
		return number = number + 3;
	}
	
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

	
	public Sudoku generateSudoku(int currentValue, int numberOfHide) {
		Sudoku sudoku = new Sudoku();
		sudoku.matrixAnswers = new int[rows][columns];
		sudoku.matrixFields = new int[rows][columns];
		sudoku.matrixPlayer = new int[rows][columns];
		fillFirstColumnMatrix(currentValue, sudoku.matrixAnswers);
		fillOtherPositions(sudoku.matrixAnswers);
		hidePositionsMatrix(sudoku.matrixFields, sudoku.matrixAnswers, numberOfHide);
		copyMatrix(sudoku.matrixPlayer, sudoku.matrixFields);
		return sudoku;
	}

}
