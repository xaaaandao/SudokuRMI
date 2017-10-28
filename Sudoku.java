import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Sudoku extends UnicastRemoteObject implements SudokuInterface{

	public Sudoku() throws RemoteException {
        super();
        System.out.println("Objeto remoto instanciado");
    }

}
