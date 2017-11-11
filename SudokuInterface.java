import java.rmi.*;
import java.util.*;

public interface SudokuInterface extends Remote {
	public int [][]matrixForPlayer(int id) throws RemoteException;
	public int [][]matrixFields(int id) throws RemoteException;	
	public void replaceValue(int id, int value, int i, int j) throws RemoteException;
	public int countFillFields(int id) throws RemoteException;
	public int numberOfHits(int id) throws RemoteException;
	public int numberOfErrors(int id) throws RemoteException;
	public int checkInput(int id, int value, int i, int j) throws RemoteException;
	public int getOldValue(int id, int i, int j) throws RemoteException;
}