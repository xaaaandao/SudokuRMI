import java.rmi.*;
import java.util.*;

public interface SudokuInterface extends Remote {
	public int [][]matrixForPlayer() throws RemoteException;
	public int [][]matrixFields() throws RemoteException;
	public int currentId() throws RemoteException;
	public void replaceValue(int value, int i, int j) throws RemoteException;
	public int countFillFields() throws RemoteException;
	public int numberOfHits() throws RemoteException;
	public int numberOfErrors() throws RemoteException;
	public int checkInput(int value, int i, int j) throws RemoteException;
}
