import java.rmi.RemoteException;
import java.util.*;

public class Command {

	int rows = 9;
	int columns = 9;
	int numberOfSudoku = 9;

	public void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  

	public String processCommandPlayer(ManageSudoku manageSudoku, String command){
		String answers = "";
		switch(command) {
			case "-t":
				try {
					for(int i = 0; i < numberOfSudoku; i++) {
						answers = answers + matrixToString(i+1, manageSudoku.matrixForPlayer(i));
					}
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "1":
				try {
					answers = answers + matrixToString(1, manageSudoku.matrixForPlayer(0));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "2":
				try {
					answers = answers + matrixToString(2, manageSudoku.matrixForPlayer(1));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "3":
				try {
					answers = answers + matrixToString(3, manageSudoku.matrixForPlayer(2));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "4":
				try {
					answers = answers + matrixToString(4, manageSudoku.matrixForPlayer(3));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "5":
				try {
					answers = answers + matrixToString(5, manageSudoku.matrixForPlayer(4));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "6":
				try {
					answers = answers + matrixToString(6, manageSudoku.matrixForPlayer(5));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "7":
				try {
					answers = answers + matrixToString(7, manageSudoku.matrixForPlayer(6));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "8":
				try {
					answers = answers + matrixToString(8, manageSudoku.matrixForPlayer(7));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "9":
				try {
					answers = answers + matrixToString(9, manageSudoku.matrixForPlayer(8));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			default:
				return "faltou algum parâmetro pesquise digitando ajuda";
		}
	}

	
	public String matrixToString(int id, int [][]matrix) {
		String print = "╔══════════════════╗\n";
		print = print +"║     Nível "+ id  +"      ║\n";
		print = print +"╠══════════════════╣\n";
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(j == 0) {
					print = print + "║" + Integer.toString(matrix[i][j]) + " ";	
				} else {
					print = print + Integer.toString(matrix[i][j]) + " ";	
				}
			}
			print = print + "║\n";
		}
		print = print + "╚══════════════════╝\n\n";
		return print;
	}
	
	
	public String processCommandAnswers(ManageSudoku manageSudoku, String command){
		String answers = "";
		switch(command) {
			case "-t":
				try {
					for(int i = 0; i < numberOfSudoku; i++) {
						answers = answers + matrixToString(i+1, manageSudoku.matrixForAnswers(i));
					}
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "1":
				try {
					answers = answers + matrixToString(1, manageSudoku.matrixForAnswers(0));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "2":
				try {
					answers = answers + matrixToString(2, manageSudoku.matrixForAnswers(1));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "3":
				try {
					answers = answers + matrixToString(3, manageSudoku.matrixForAnswers(2));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "4":
				try {
					answers = answers + matrixToString(4, manageSudoku.matrixForAnswers(3));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "5":
				try {
					answers = answers + matrixToString(5, manageSudoku.matrixForAnswers(4));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "6":
				try {
					answers = answers + matrixToString(6, manageSudoku.matrixForAnswers(5));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "7":
				try {
					answers = answers + matrixToString(7, manageSudoku.matrixForAnswers(6));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "8":
				try {
					answers = answers + matrixToString(8, manageSudoku.matrixForAnswers(7));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "9":
				try {
					answers = answers + matrixToString(9, manageSudoku.matrixForAnswers(8));
					return answers;
				} catch (RemoteException r) {
					System.out.println(r);
				}
			default:
				return "faltou algum parâmetro pesquise digitando ajuda";
		}
	}
	
	public String processCommandReset(ManageSudoku manageSudoku, String command){
		switch(command) {
			case "-s":
				try {
					for(int i = 0; i < numberOfSudoku; i++) {
						manageSudoku.resetMatrixForPlayer(i);
					}
					return "todos os sudokus foram resetados";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s1":
				try {
					manageSudoku.resetMatrixForPlayer(0);
					return "o sudoku de nível 1 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s2":
				try {
					manageSudoku.resetMatrixForPlayer(1);
					return "o sudoku de nível 2 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s3":
				try {
					manageSudoku.resetMatrixForPlayer(2);
					return "o sudoku de nível 3 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s4":
				try {
					manageSudoku.resetMatrixForPlayer(3);
					return "o sudoku de nível 4 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s5":
				try {
					manageSudoku.resetMatrixForPlayer(4);
					return "o sudoku de nível 5 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s6":
				try {
					manageSudoku.resetMatrixForPlayer(5);
					return "o sudoku de nível 6 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s7":
				try {
					manageSudoku.resetMatrixForPlayer(6);
					return "o sudoku de nível 7 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s8":
				try {
					manageSudoku.resetMatrixForPlayer(7);
					return "o sudoku de nível 8 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-s9":
				try {
					manageSudoku.resetMatrixForPlayer(8);
					return "o sudoku de nível 9 foi resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			case "-r":
				try {
					manageSudoku.resetRanking();
					return "ranking resetado";
				} catch (RemoteException r) {
					System.out.println(r);
				}
			default:
				return "faltou algum parâmetro pesquise digitando ajuda";
		}
	}
	
	public String printRanking(List<Players> listOfPlayers){
		if(listOfPlayers.size() == 0){
			return "Ranking vazio!\n";
		}
		String ranking = "Nome:Pontuação\n";
		for(int i = 0; i < listOfPlayers.size(); i++) {
			ranking = ranking + listOfPlayers.get(i).getName() + ":" + listOfPlayers.get(i).getScore() + "\n";
		}
		return ranking;
	}
	
	public String processCommand(ManageSudoku manageSudoku, String command){
		command = command.toLowerCase();
		String []request = command.split(" ");
		if(request.length == 1) {
			switch(request[0]){
				case "ajuda":
					return "ajuda: aparece essa informação\n" +
						   "ranking: mostra o ranking de jogadores e sua pontuação\n" +
						   "resposta {-t}: mostra as resposta de todos os sudokus\n" +
						   "resposta {1...9}: mostra as resposta do sudoku que foi passado como parâmetro\n" +
						   "reset {-s}: apaga todos os valores presentes no sudoku de jogadores\n"+
					       "reset {-s1...-s9}: apaga os valores presentes em um sudoku específico de jogadores\n"+
					       "reset {-r}: apaga todos os jogadores presentes no ranking\n"+
					       "sair: sai do servidor\n"+
					       "versao: versão do sudoku\n"+
					       "visualizar {-t}: mostra todos os sudokus preenchido pelo jogador\n" +
						   "visualizar {1...9}: mostra os sudokus preenchido pelo jogador que foi passado como parâmetro\n";
				case "ranking":
					try {
						return printRanking(manageSudoku.getListOfPlayers());	
					} catch (RemoteException r) {
						
					}
				case "sair":
					return "Até a próxima!!!";
				case "versao":
					return "Sudoku RMI (Remote Method Invocation) v1.0";					
				default:
					return "comando não existente pesquise digitando ajuda";
			}
		} else if(request.length > 1) {
			switch(request[0]) {
				case "resposta":
					return processCommandAnswers(manageSudoku, request[1]);
				case "reset":
					return processCommandReset(manageSudoku, request[1]);
				case "visualizar":
					return processCommandPlayer(manageSudoku, request[1]);
				default:
					return "comando não existente pesquise digitando ajuda";
			}
		}
		return "comando não existente pesquise digitando ajuda";
	}
}
