import java.rmi.*;
import java.util.*;

public interface SudokuInterface extends Remote {
    public int [][]matrixForUser() throws RemoteException;
    public void checkInput(int i, int j) throws RemoteException;
}
