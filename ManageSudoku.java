import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ManageSudoku extends UnicastRemoteObject implements SudokuInterface, Serializable{
	
	List<Sudoku> listOfSudoku;
	List<Players> listOfPlayers;
	int rows = 9;
	int columns = 9;
	
	/**
	 * O construtor ManageSudoku(), que instância uma lista de sudoku e lista de jogadores
	 * além do que preenche ambas as listas.
	 * */
	public ManageSudoku() throws RemoteException {
        super();
        listOfSudoku = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        generateListOfSudoku();
        generateListOfPlayers();
        System.out.println("Objeto remoto instanciado");
    }

	/**
	 * O método generateListOfPlayers() caso o arquivo de banco de dados exista
	 * o mesmo é carregado em uma lista, caso contrário nada é adicionado na lista
	 * de jogadores.
	 * @return void.
	 * */
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
				}
				buffer.close();	
			} catch (FileNotFoundException f) {
				
			} catch (IOException i) {
				
			}
		}
	}
	
	/**
	 * O método generateListOfSudoku() armazena em uma lista noves tipos de sudoku
	 * com diferente graus de dificuldade.
	 * @return void.
	 * */
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
	
	/**
	 * O método matrixForAnswers(int id) que pode ser invocado remotamente, retorna o sudoku
	 * que está com todas as respostas de todos os sudokus a partir de um identificador.
	 * @param id é um inteiro com o identificador do sudoku solicitado pelo servidor.
	 * @return matrixAnswers arranjo bidimensional de inteiros com as respostas.
	 * */
	public int [][]matrixForAnswers(int id) throws RemoteException {
    	return listOfSudoku.get(id).matrixAnswers;
    }

	/**
	 * O método resetMatrixForPlayer(int id) que pode ser invocado remotamente, tira 
	 * os valores preenhchidos no sudoku pelo jogador. 
	 * @param id é um inteiro com o identificador do sudoku solicitado pelo servidor.
	 * @return void.
	 * */
	public void resetMatrixForPlayer(int id) throws RemoteException {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(listOfSudoku.get(id).matrixFields[i][j] == 0) {
					listOfSudoku.get(id).matrixPlayer[i][j] = 0;
				}
			}
		}
    }
	
	/**
	 * O método resetRanking() que pode ser invocado remotamente, apaga todos os 
	 * jogadores presentes na lista de jogadores e atualiza o arquivo de banco de dados.
	 * @return void.
	 * */
	public void resetRanking() throws RemoteException  {
		listOfPlayers = new ArrayList<>();
		refreshDb();
	}
	
	/**
	 * O método matrixForPlayer(int id) que pode ser invocado remotamente, retorna o sudoku
	 * que está sendo preenchido pelos jogadores a partir de um identificador.
	 * @param id é um inteiro com o identificador do sudoku solicitado pelo jogador.
	 * @return matrixPlayer arranjo bidimensional de inteiros preenchida pelos jogadores.
	 * */
	public int [][]matrixForPlayer(int id) throws RemoteException {
    	return listOfSudoku.get(id).matrixPlayer;
    }
    
	/**
	 * O método matrixFields(int id) que pode ser invocado remotamente, retorna o sudoku
	 * que tem os campos que devem ser preenchidos pelos jogadores.
	 * @param id é um inteiro com o identificador do sudoku solicitado pelo jogador.
	 * @return matrixPlayer arranjo bidimensional de inteiros que tem as posições que devem ser preenchidas pelos jogadores.
	 * */
	public int [][]matrixFields(int id) throws RemoteException {
		return listOfSudoku.get(id).matrixFields;
	}
	
	/**
	 * O método replaceValue(int id, int value, int i, int j) sobrescreve o valor que o jogador inseriu
	 * no sudoku que é preenchido pelos usuários.
	 * @param id é um inteiro com o identificador do sudoku que será substituído o valor.
	 * @param value é um inteiro com o valor que será substituído.
	 * @param i é um inteiro com a posição no eixo x onde será substituído esse valor.
	 * @param j é um inteiro com a posição no eixo y onde será substituído esse valor.
	 * @return void.
	 * */
	public void replaceValue(int id, int value, int i, int j) throws RemoteException{
    	listOfSudoku.get(id).matrixPlayer[i][j] = value;
    }
    
	/**
	 * O método countFillFields(int id) simplesmente retorna a quantidade de campos restante que é preenchida 
	 * pelos usuários.
	 * @param id é um identificador com o valor que do sudoku que será verficado a quantidade campos restantes.
	 * @return um inteiro, com a quantidade de campos que restam na matriz que preenchida pelos usuários.
	 * */
	public int countFillFields(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countFieldsEmpty(listOfSudoku.get(id).matrixPlayer);
    }
	
	/**
	 * O método numberOfHits(int id) simplesmente retorna a quantidade de campos certos pelo jogador.
	 * @param id é um identificador com o valor que do sudoku que será verificado a quantidade campos corretos.
	 * @return um inteiro, com a quantidade de campos que certos na matriz que preenchida pelos usuários.
	 * */
	public int numberOfHits(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countHits(listOfSudoku.get(id).matrixFields, listOfSudoku.get(id).matrixPlayer, listOfSudoku.get(id).matrixAnswers);
    }
	
	/**
	 * O método numberOfErrors(int id) simplesmente retorna a quantidade de campos errados pelo jogador.
	 * @param id é um identificador com o valor que do sudoku que será verificado a quantidade campos errados.
	 * @return um inteiro, com a quantidade de campos que errados na matriz que preenchida pelos usuários.
	 * */
	public int numberOfErrors(int id) throws RemoteException{
		Sudoku sudoku = new Sudoku();
    	return sudoku.countErrors(listOfSudoku.get(id).matrixFields, listOfSudoku.get(id).matrixPlayer, listOfSudoku.get(id).matrixAnswers);
    }
	
	/**
	 * O método checkInput(int value, int i, int j) que pode ser invocado remotamente, verifica
	 * se a posição que o usuário deseja está vazia, se sim preenche e verifica se todos os campos
	 * estão preenchidos. Caso contrário retorna verifica se o jogador deseja ou não sobrescrever 
	 * o valor que está lá no sudoku.
	 * @param id é um inteiro com identificador do sudoku que o usuário quer que adicione, troque ou remova de valor.
	 * @param value é um inteiro com o valor que será adicionado, substituído ou removido.
	 * @param i é um inteiro com o valor da posição no eixo x do valor que será adicionado, removido ou substituído.
	 * @param j é um inteiro com o valor da posição no eixo y do valor que será adicionado, removido ou substituído.
	 * @return 1 é um inteiro, que signfica que o valor foi adicionado com sucesso.
	 * @return 2 é um inteiro, que signfica que o valor que o jogador está tentando inserir é igual ao que está na matriz.
	 * @return 3 é um inteiro, que signfica que o valor que o jogador está tentando inserir é difernete ao que está na matriz.
	 * */
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
	
	/**
	 * O método getOldValue(int id, int i, int j) simplesmente retorna o valor antigo que estava na posição desejada.
	 * @param id é um inteiro com identificador com o valor que do sudoku que será verificado o valor que estava naquele sudoku e naquela posição.
	 * @param i é um inteiro com o valor na posição do eixo x, onde está o valor que deve ser recuperado.
	 * @param j é um inteiro com o valor na posição do eixo y, onde está o valor que deve ser recuperado.
	 * @return um inteiro, com o valor recuperado.
	 * */
	public int getOldValue(int id, int i, int j) throws RemoteException{
		return listOfSudoku.get(id).matrixPlayer[i][j];
	}	
	
	/**
	 * O método refreshDb() a cada novo jogador ou a cada nova pontuação no ranking atualizamos
	 * nossa base de dedados.
	 * @return void.
	 * */
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

	/**
	 * O método addNewRecord(Players player) recebe um objeto do tipo Players,
	 * na qual será verificado na lista de jogadores se já existe um jogador com 
	 * esse nome se sim, verifica se a pontuação atual ou se a que estava presente é maior
	 * e atualiza para que é maior. Caso, não seja encontrado na lista adiciona esse
	 * jogador na lista.
	 * @param player é um objeto do tipo Players, que contém o seguinte atributo nome e pontuação. 
	 * @return void.
	 * */
	public void addNewRecord(Players player) throws RemoteException{
		boolean find = false;
		for(Players p : listOfPlayers) {
			/* Verifico se o nome do jogador existe na lista se sim */
			/* Pego a sua pontuação e adiciono mais um valor */
			if(player.getName().equals(p.getName())) {
				if(player.getScore() > p.getScore()) {
					p.setScore(player.getScore());
				} else {
					p.setScore(p.getScore());
				}
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

	/**
	 * O método sortReversedListOfPlayers(List<Players> list) ordena a lista em ordem decrescente
	 * pela pontuação.
	 * @param list é uma List<Players>, que contém uma lista de jogadores. 
	 * @return void.
	 * */
    public void sortReversedListOfPlayers(List<Players> list){
        Collections.sort(listOfPlayers, new Comparator<Players>() {
            @Override
            public int compare(Players p1, Players p2) {
                return Integer.compare(p1.getScore(), p2.getScore());
            }
        }.reversed());  
    }

	/**
	 * O método copyListOfPlayers(List<Players> dst, List<Players> src), recebe duas lista de jogadores
	 * em que será copiado da lista de origem os seus valores para uma lista de destino.
	 * @param dest lista de jogadores que terá valores iguais aos valores da lista de origem.
	 * @param src lista de jogadores terá seus valores copiados para uma outra lista.
	 * @return void.
	 * */
    public void copyListOfPlayers(List<Players> dst, List<Players> src) {
    	for(Players s : src) {
    		Players d = new Players();
    		d.setName(s.getName());
    		d.setScore(s.getScore());
    		dst.add(d);
    	}
    }
    
	/**
	 * O método getListOfPlayers(), retorna a cópia da  lista de jogadores ordenados em ordem decrescente pela sua pontuação
	 * para que ser possa ser exibida pela interface gráfica.
	 * @return copyListOfPlayers que é uma cópia da lista de jogadores ordenados em ordem decrescente pela sua pontuação.
	 * */
	public List<Players> getListOfPlayers() throws RemoteException{
		List<Players> copyListOfPlayers = new ArrayList<>();
		copyListOfPlayers(copyListOfPlayers, listOfPlayers);
		sortReversedListOfPlayers(copyListOfPlayers);
		return copyListOfPlayers;
	}
}
