import java.rmi.RemoteException;
import java.util.*;

public class SudokuFinish extends TimerTask {
	
	boolean isFinish;
	boolean newGame;
	SudokuInterface s;
	
	public SudokuFinish(SudokuInterface sudoku){
		s = sudoku;
		isFinish = false;
		newGame = false;
	}

	public void run() {
		try {
			if(s.countFillFields() == 0) {
				setIsFinish(true);
			} else {
				setIsFinish(false);
			}
		} catch (RemoteException r) {
			
		}
	}
    
	public void setIsFinish(boolean finish){
    	isFinish = finish;
    }
    
    public boolean getIsFinish(){
    	return isFinish;
    }
    
	public void setNewGame(boolean game){
    	newGame = game;
    }
    
    public boolean getNewGame(){
    	return newGame;
    }
}
