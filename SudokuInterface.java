import java.rmi.*;
import java.util.*;

public interface SudokuInterface extends Remote {
	public int countHit() throws RemoteException;
	public int countError() throws RemoteException;
    public int [][]matrixForUser() throws RemoteException;
    public int checkInput(int value, int i, int j) throws RemoteException;
}
