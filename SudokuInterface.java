import java.rmi.*;
import java.util.*;

public interface SudokuInterface extends Remote {
	public int countHit() throws RemoteException;
	public int countError() throws RemoteException;
    public int [][]matrixForPlayer() throws RemoteException;
    public int [][]matrixFields() throws RemoteException;
    public int checkInput(int value, int i, int j) throws RemoteException;
}
