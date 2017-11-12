import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ManageSudoku extends UnicastRemoteObject implements SudokuInterface, Serializable{
	
	List<Sudoku> listOfSudoku;
	List<Players> listOfPlayers;
	
	public ManageSudoku() throws RemoteException {
        super();
        listOfSudoku = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        generateListOfSudoku();
        generateListOfPlayers();
        System.out.println("Objeto remoto instanciado");
    }
	
	public void generateListOfPlayers() {
		File fileDb = new File("db.xml");
		/* Se o arquivo existe e não é um diretório */
		if(fileDb.exists() && !fileDb.isDirectory()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(fileDb);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream));
				String line = null;
				while ((line = buffer.readLine()) != null){
					line = line.replace("<player>", "");
					line = line.replace("</player>", "");
					String []information = line.split(":");
					Players player = new Players();
					player.setName(information[0]);
					player.setScore(Integer.parseInt(information[1]));
					listOfPlayers.add(player);
					//System.out.println(line);
				}
				buffer.close();	
			} catch (FileNotFoundException f) {
				
			} catch (IOException i) {
				
			}
		}
	}
	
	public void generateListOfSudoku() {
		int numberOfSudoku = 9;
		int numberOfHide = 0;
		for(int i = 0; i < numberOfSudoku; i++) {
			Sudoku sudoku = new Sudoku();
			if(i >= 0 && i <= 2) {
				numberOfHide = 3;
			} else if(i >= 3 && i <= 5) {
				numberOfHide = 5;
			} else {
				numberOfHide = 7;
			}
			if(i == 0) {
				sudoku = sudoku.generateSudoku(i, numberOfHide);
			} else {
				sudoku = sudoku.generateSudoku(listOfSudoku.get(listOfSudoku.size() - 1).matrixAnswers[0][0], numberOfHide);
			}
			listOfSudoku.add(sudoku);
		}
	}
	
	public int [][]matrixForPlayer(int id) throws RemoteException {
    	return listOfSudoku.get(id).matrixPlayer;
    }
    

	public int [][]matrixFields(int id) throws RemoteException {
		return listOfSudoku.get(id).matrixFields;
	}
	
	public void replaceValue(int id, int value, int i, int j) throws RemoteException{
    	listOfSudoku.get(id).matrixPlayer[i][j] = value;
    }
    
	public int countFillFields(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countFieldsEmpty(listOfSudoku.get(id).matrixPlayer);
    }
	
	public int numberOfHits(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countHits(listOfSudoku.get(id).matrixFields, listOfSudoku.get(id).matrixPlayer, listOfSudoku.get(id).matrixAnswers);
    }
	
	public int numberOfErrors(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countErrors(listOfSudoku.get(id).matrixFields, listOfSudoku.get(id).matrixPlayer, listOfSudoku.get(id).matrixAnswers);
    }
	
	public int checkInput(int id, int value, int i, int j) throws RemoteException{
    	/* Verifico se a posição que o usuário está tentando inserir está vazia */
    	if(listOfSudoku.get(id).matrixPlayer[i][j] == 0) {
    		/* Insiro na matriz */
    		listOfSudoku.get(id).matrixPlayer[i][j] = value;
    		return 1;
    	/* Caso a posição já está preenchida */
    	} else {
    		/* Verifico se eu quero sobrescrever com o meu valor */
    		/* Se sim coloco o valor que o usuário quer e devolvo para todos os meus jogadores */
    		if(listOfSudoku.get(id).matrixPlayer[i][j] == value){
        		return 2;	
    		}
    		return 3;
    	}
	}
	
	public int getOldValue(int id, int i, int j) throws RemoteException{
		return listOfSudoku.get(id).matrixPlayer[i][j];
	}	
	
	public void refreshDb(){
		try (BufferedWriter file = new BufferedWriter(new FileWriter("db.xml"))) {
			for(Players p : listOfPlayers) {
				file.write("<player>" + p.getName() + ":" + p.getScore() + "</player>\n"); 
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewRecord(Players player) throws RemoteException{
		boolean find = false;
		for(Players p : listOfPlayers) {
			/* Verifico se o nome do jogador existe na lista se sim */
			/* Pego a sua pontuação e adiciono mais um valor */
			if(player.getName().equals(p.getName())) {
				p.setScore(player.getScore() + p.getScore());
				find = true;
			}
		}
		/* Se o usuário não existe adiciono na lista */
		if(find == false) {
			listOfPlayers.add(player);	
		}
		/* Escrevo no arquivo de db.xml */
		refreshDb();
	}

	
    public void sortReversedListOfPlayers(List<Players> list){
        Collections.sort(listOfPlayers, new Comparator<Players>() {
            @Override
            public int compare(Players p1, Players p2) {
                return Integer.compare(p1.getScore(), p2.getScore());
            }
        }.reversed());  
    }

    public void copyListOfPlayers(List<Players> dst, List<Players> src) {
    	for(Players s : src) {
    		Players d = new Players();
    		d.setName(s.getName());
    		d.setScore(s.getScore());
    		dst.add(d);
    	}
    }
    
	public List<Players> getListOfPlayers() throws RemoteException{
		List<Players> copyListOfPlayers = new ArrayList<>();
		copyListOfPlayers(copyListOfPlayers, listOfPlayers);
		sortReversedListOfPlayers(copyListOfPlayers);
		return copyListOfPlayers;
	}
}
