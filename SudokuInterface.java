import java.rmi.*;
import java.util.*;

/**
 * Descrição: especificando os métodos que podem ser invocados remotamente pelo cliente.
 * Autor: Alexandre Yuji Kajihara e Rafael Alessandro Ramos.
 * Data de criação: 02/11/2017
 * Data de atualização: 12/11/2017
 * */

public interface SudokuInterface extends Remote {
	public int [][]matrixForAnswers(int id) throws RemoteException;
	public void resetMatrixForPlayer(int id) throws RemoteException;
	public void resetRanking() throws RemoteException;
	public int [][]matrixForPlayer(int id) throws RemoteException;
	public int [][]matrixFields(int id) throws RemoteException;	
	public void replaceValue(int id, int value, int i, int j) throws RemoteException;
	public int countFillFields(int id) throws RemoteException;
	public int numberOfHits(int id) throws RemoteException;
	public int numberOfErrors(int id) throws RemoteException;
	public int checkInput(int id, int value, int i, int j) throws RemoteException;
	public int getOldValue(int id, int i, int j) throws RemoteException;
	public void addNewRecord(Players player) throws RemoteException;
	public List<Players> getListOfPlayers() throws RemoteException;
	public void removePlayer(String name) throws RemoteException;
}
