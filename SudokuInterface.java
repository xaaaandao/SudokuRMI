import java.rmi.*;
import java.util.*;

public interface SudokuInterface extends Remote {
    public int [][]matrixForUser() throws RemoteException;
<<<<<<< HEAD
    public boolean checkInput(int value, int i, int j) throws RemoteException;
}
=======
    public void checkInput(int i, int j) throws RemoteException;
}
>>>>>>> xandao
