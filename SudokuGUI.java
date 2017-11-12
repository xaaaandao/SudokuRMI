import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.text.DocumentFilter.*;
import javafx.stage.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public class SudokuGUI{
	
	boolean otherWindow;
	int rows = 9;
	int columns = 9;
	static int currentHits = 0;
	static int currentErrors = 0;
	SudokuInterface s;
		
	/**
	 * O construtor SudokuGUI(SudokuInterface sudoku), atribui o parâmetro sudoku
	 * a um atributo da classe.
	 * */
	public SudokuGUI(SudokuInterface sudoku) {
		s = sudoku;
	}

	/**
	 * O método checkResponseSudoku(SudokuInterface sudoku, int response, int value, Fields field), interpreta
	 * a resposta que foi dada ao servidor, se for um significa que todos os campos já foram preenchidos, se for
	 * dois que o valor foi adicionado correamente, e por fim três que verifica se o usuário deseja ou não
	 * sobrescrever ou não o valor.
	 * @return void.
	 * */
	public void checkResponseSudoku(SudokuInterface sudoku, int level, int response, int value, Fields field) {
		switch (response) {
			case 1:
				/* Valor adicionado com sucesso */
				break;
			case 2:
				/* Caso que o jogador esteja sobescrevendo seja igual ao valor que já esteja lá */
				break;
			case 3:
				/* Caso que o jogador esteja sobescrevendo seja diferente ao valor que já esteja lá */
				String message = "Você deseja sobrescrever o valor?";
				if(value == 0) {
					message = "Você deseja remover o valor?";
				}
				if (JOptionPane.showConfirmDialog(null, message, "Posição já preenchida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						sudoku.replaceValue(level, value, field.getI(), field.getJ());
					} catch (RemoteException r) {
						
					}
				} else {
					try {
						JTextField fieldOldValue = field.getJTextField();
						String oldValue = Integer.toString(sudoku.getOldValue(level, field.getI(), field.getJ()));
						fieldOldValue.setText(oldValue);
					} catch (RemoteException r) {
						System.out.println(r);
					}
				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * O método printMatrix(int [][]matrix), imprime a variável matrix.
	 * @return void.
	 * */
	public void printMatrix(int [][]matrix) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * O método copyMatrix(int[][] dest, int[][] src), recebe duas matrizes uma de origem e outra de destino,
	 * e é copiado os valores presentes na matriz de origem para um matriz de destino.
	 * @return void.
	 * */
	public void copyMatrix(int[][] dest,  int[][] src) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				dest[i][j] = src[i][j];
		}
	}
	
	public String levelForSudoku(int level){
		/*if(level >= 0 && level <= 2)
			return "Jogue Sudoku (Nível Fácil)";
		else if(level >= 3 && level <= 5)
			return "Jogue Sudoku (Nível Médio)";
		return "Jogue Sudoku (Nível Díficil)";*/
		return "Jogue Sudoku (Nível " +  Integer.toString(level + 1)+")";
	}
	
	/**
	 * O método buildWindowSudoku(SudokuInterface sudoku, int [][]matrixFields, int[][] matrixUser), primeiramente
	 * verifica se a interface gráfica já foi criada ou não, se já tiver sido criada percorre na matrixFields
	 * verifica se na posição que está sendo percorrida é zero se for zero vai ser um JTextField, caso contrário
	 * JLabel. Caso a interface gráfica já esteja montada, verifica se matriz que está sendo exibida e a matriz
	 * que está sendo preenchida pelos usuários no servidor é diferente, se for diferente atualiza com os novos valores.
	 * Verifica se todos os campos estão preenchidos, se sim pergunta se o jogador se quer verificar 
	 * os acertos e erros, se não continua jogando e após daqui 10 segundos verifica se todas as posições 
	 * estão preenchidas novamente. 
	 * @return void.
	 * */
	public void buildWindowSudoku(SudokuInterface sudoku, int level, int [][]matrixFields, int[][] matrixUser) throws IOException{
		List<Fields> listOfFields = new ArrayList<>();
		int[][] matrixUpdate = new int[rows][columns];
		
		JFrame window = new JFrame(levelForSudoku(level));
		JLabel[][] fixContent = new JLabel[rows][columns];
		JTextField[][] fillContent = new JTextField[rows][columns];
		JPanel panel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JMenu optionsMenuItem = new JMenu("Opções");
		JMenu helpMenuItem = new JMenu("Ajuda");
		JMenuItem rankMenuItem = new JMenuItem("Ranking");
		JMenuItem exitMenuItem = new JMenuItem("Sair");
		JMenuItem howToPlayMenuItem = new JMenuItem("Como jogar");
		JMenuItem contactMenuItem = new JMenuItem("Entre em contato");

		otherWindow = false;
		panel.setLayout(new GridLayout(rows, columns));
		optionsMenuItem.add(rankMenuItem);
		optionsMenuItem.add(exitMenuItem);
		helpMenuItem.add(howToPlayMenuItem);
		helpMenuItem.add(contactMenuItem);
		menuBar.add(optionsMenuItem);
		menuBar.add(helpMenuItem);
		rankMenuItemListener(sudoku, rankMenuItem);
		exitMenuItemListener(exitMenuItem);
		howToPlayMenuItemListener(howToPlayMenuItem);
		contactMenuItemListener(contactMenuItem);
		loadListOfFields(listOfFields, matrixFields);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixFields[i][j] == 0) {
					fillContent[i][j] = new JTextField(1);
				    fillContent[i][j].setHorizontalAlignment(JTextField.CENTER);
				    if(matrixUser[i][j] > 0) {
				    	fillContent[i][j].setText(Integer.toString(matrixUser[i][j]));
				    	matrixUser[i][j] = matrixUser[i][j];
				    } else {
				    	fillContent[i][j].setText("");
				    	matrixUser[i][j] = 0;
				    }
					PlainDocument document = (PlainDocument) fillContent[i][j].getDocument();
					checkValue(document);
				    /* Fields pega a posição que o cliente está preenchendo */
				    Fields field = new Fields(i, j, fillContent[i][j]);
				    focusField(sudoku, level, fillContent[i][j], field);
					panel.add(fillContent[i][j]);
				} else {
					fixContent[i][j] = new JLabel(Integer.toString(matrixFields[i][j]), SwingConstants.CENTER);
					panel.add(fixContent[i][j]);
				}
			}
		}
		
		window.setJMenuBar(menuBar);
		window.add(panel, BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 600);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		while(true) {
			//matrixUpdate = sudokuUpdate.getMatrix();
			try {
				matrixUpdate = sudoku.matrixForPlayer(level); 
			} catch (RemoteException r) {
				
			}
			if(!compareMatrix(matrixUpdate, matrixUser)){
				for(Fields f : listOfFields) {
					int i = f.getI();
					int j = f.getJ();
					if(matrixUpdate[i][j] > 0) {
						fillContent[i][j].setText(Integer.toString(matrixUpdate[i][j]));	
						matrixUser[i][j] = matrixUpdate[i][j];
					} else {
						fillContent[i][j].setText("");
						matrixUser[i][j] = 0;
					}
				} 
			}
			if(sudokuFinish(listOfFields, matrixUser, fillContent) && otherWindow == false) {
				if (JOptionPane.showConfirmDialog(null, "Acabou! Você deseja ver a quantidade de acertos e erros?", "Todas as posições preenchidas", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						int numberOfHits = s.numberOfHits(level);
						int numberOfErrors = s.numberOfErrors(level);
						String stringHit = Integer.toString(numberOfHits);
						String stringError = Integer.toString(numberOfErrors);
						if (JOptionPane.showConfirmDialog(null, "Acertos: " + stringHit +"\nErros: " +  stringError + "\nVocê deseja ir para o próximo nível?", "Acertos e erros", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							/* Atualizo a pontuação do cliente */
							currentHits = currentHits + numberOfHits;
							currentErrors = currentErrors + numberOfErrors;
							if(level + 1 == 9) {
								if (JOptionPane.showConfirmDialog(null, "Parabéns! Você zerou o Sudoku!\nVocê acertou no total: " + Integer.toString(currentHits) + "\nVocê errou no total: " + Integer.toString(currentErrors) + "\nVocê deseja salvar seu nome no ranking?", "Parabéns!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
									addNameRanking(sudoku, currentHits);
								}
								System.exit(1);	
							} else {
								//Pede o novo sudoku
								window.dispose();
								level++;
								try {
									buildWindowSudoku(sudoku, level, sudoku.matrixFields(level), sudoku.matrixForPlayer(level));	
								} catch (RemoteException r) {
									
								}
							}
						} else {
							System.exit(1);
						}
					} catch (RemoteException re){
						
					}
				} else {
					/* Caso o usuário não queira visualizar os acertos significa que quero alterar algo,
					 * mas decidiu trocar um valor, então ele terá 10 segundos para trocar.
					 * Caso não altere nada, logo acabar os dez segundos irá ser solicitado se ele deseja conferir o resultado.
					 * Caso altere um valor nesses 10 segundos, ele irá verificar a corretude.
					 * Caso altere mais de um valor nesses 10 segundos, ele não irá verificar, mas esperar todas as posições estarem novamente completas. 
					 * */
					threadSleep(10000);
				}
			}
		}
	}
	
	public void threadSleep(long time) {
		try {
			Thread.sleep(time);	
		} catch (InterruptedException i) {
			
		}
	}
	
	public void addNameRanking(SudokuInterface sudoku, int score){
		String[] options = {"Ok"};
		JLabel labelName = new JLabel("Nome: ");
		JTextField fieldName = new JTextField(15);
		JPanel panel = new JPanel();
		panel.add(labelName);
		panel.add(fieldName);
		while(true) {
			int result = JOptionPane.showOptionDialog(null, panel, "Entrando no ranking", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
			if(result == 0 && fieldName.getText().length() > 0) {
				Players player = new Players();
				player.setName(fieldName.getText());
				player.setScore(score);
				try {
					sudoku.addNewRecord(player);
				} catch (RemoteException re) {

				}
				break;
			}
		}
	}
	
	/**
	 * O método sudokuFinish(List<Fields> listOfFields, int [][]matrixUser, JTextField [][]content),
	 * verifica se a matriz do usuário e o campo tem valores ou tem um caractere, se sim retorna
	 * true, caso contrário retorna false.
	 * @return true ou false, true caso todas as posições de ambas as matrizes estejem preenchidas, 
	 * false caso não estejem todas preenchidas.
	 * */
	public boolean sudokuFinish(List<Fields> listOfFields, int [][]matrixUser, JTextField [][]content) {
		for(Fields f : listOfFields) {
			int i = f.getI();
			int j = f.getJ();
			if(matrixUser[i][j] == 0 && content[i][j].getText().length() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * O método loadListOfFields(List<Fields> listOfFields, int [][]matrixFields), carrega as posições que 
	 * dos campos que devem ser preenchidos, em uma lista.
	 * @return void.
	 * */
	public void loadListOfFields(List<Fields> listOfFields, int [][]matrixFields) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixFields[i][j] == 0) {
					Fields f = new Fields(i, j, null);
					listOfFields.add(f);
				}
			}
		}
	}
	
	
	/**
	 * O método checkValue(PlainDocument document), verifica simplesmente se o valor que foi colocado no JTextField
	 * é um somente um caractere, e se é um valor de 1 a 9, que são os únicos valores que são permitidos por um
	 * sudoku.
	 * @return void.
	 * */
	public void checkValue(PlainDocument document) {
	    document.setDocumentFilter(new DocumentFilter() {
	         private boolean isValid(String testText) {
	             if (testText.length() > 1)
	                return false;
	             
	             if (testText.isEmpty())
	                return true;
	             
	             int intValue = 0;
	             try {
	                intValue = Integer.parseInt(testText.trim());
	             } catch (NumberFormatException e) {
	                return false;
	             }
	             if (intValue < 1 || intValue > 99)
	                return false;
	             return true; 
	          }

	          @Override
	          public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
	             StringBuilder sb = new StringBuilder();
	             sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
	             sb.insert(offset, text);
	             if (isValid(sb.toString()))
	                super.insertString(fb, offset, text, attr);
	          }

	          @Override
	          public void replace(FilterBypass fb, int offset, int length,String text, AttributeSet attrs) throws BadLocationException {
	             StringBuilder sb = new StringBuilder();
	             sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
	             int end = offset + length;
	             sb.replace(offset, end, text);
	             if (isValid(sb.toString()))
	                super.replace(fb, offset, length, text, attrs);
	          }

	          @Override
	          public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
	             StringBuilder sb = new StringBuilder();
	             sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
	             int end = offset + length;
	             sb.delete(offset, end);
	             if (isValid(sb.toString()))
	                super.remove(fb, offset, length);
	          }
	       });
	}
	
	/**
	 * O método focusField(SudokuInterface sudoku, JTextField field, Fields position), verifica
	 * se o cursor ainda está naquele campo, quando não está no campo ele pega o valor e a posição e manda 
	 * para o servidor processar e pega a resposta do servidor e interpreta ela.
	 * @return void.
	 * */
	public void focusField(SudokuInterface sudoku, int level, JTextField field, Fields position) {
	    field.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent e) {
				JTextField input = (JTextField) e.getSource();
				if(input.getText().length() >= 0) {
					try {
						int value;
						if(input.getText().length() == 0) {
							value = 0;
						} else {
							value = Integer.parseInt(input.getText());
						}
						int responseServer = sudoku.checkInput(level, value, position.getI(), position.getJ());
						checkResponseSudoku(sudoku, level, responseServer, value, position);
					} catch (RemoteException re){
						
					}
				}
			}
		});
	}
	
	/**
	 * O método compareMatrix(int [][]matrixOne, int[][] matrixTwo), compara duas matrizes se ambas forem
	 * iguais retorna true caso contrário falso.
	 * @return true ou false, que é um booleano, true caso sejem idênticas e false caso não seja.
	 * */
	public boolean compareMatrix(int [][]matrixOne, int[][] matrixTwo) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrixOne[i][j] != matrixTwo[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public void howToPlayMenuItemListener(JMenuItem howToPlayMenuItem){
		howToPlayMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		    	otherWindow = true;
		    	String[] options = {"Ok"};
		    	String message = "<html>1.Como jogar o sudoku?<br/>"
		    					+ "R:Existem 81 valores com algumas posições preenchidas e algums que devem ser preenchidas.<br/><br/>"
		    					+ "2.Quais valores devo utilizar?<br/>"
		    					+ "R:Os valores que devem ser preenchidos são de somente valores de 1 a 9.<br/><br/>"
		    					+ "3.Preenchi com o valor posso alterar ou remover?<br/>"
		    					+ "R:Sim, o valor que foi preenchido por você ou por outro jogador pode ser alterado ou removido,<br/>é somente confirmar a sua operação.<br/><br/>"
		    					+ "4.Posso jogar com mais pessoas?<br/>"
		    					+ "R:Sim, esse jogo pode ser jogado com quantas pessoas você quiser jogar! Para isso basta,<br/>"
		    					+ "com que pessoas executem um novo jogo. Quando as pessoas estiverem em um mesmo níveis,<br/>"
		    					+ "uns auxiliaram aos outros com os valores que acreditam estar corretos.<br/>"
		    					+ "5.Existem níveis ou graus de dificuldades?<br/>"
		    					+ "R:Sim, existem o nove níveis. O que varia entre elas é a quantidade de posições que devem ser preenchidas.<br/><br/>"
		    					+ "6.Como posso visualizar o ranking de pontuação?<br/>"
		    					+ "R:É só abrir na barra de menu o opções, e selecionar a opção de Ranking.<br/><br/>"
		    					+ "7.Como posso ter nome no ranking?<br/>"
		    					+ "R:Você deve jogar os nove níveis para ter seu nome e a sua pontuação no ranking.<br/><br/>"
		    					+ "8.Como visualizar o número de acertos e erros?<br/>"
		    					+ "R:Quando o seu sudoku em qualquer um dos níveis estiver preenchido, será identificado e você pode clicar em sim <br/>"
		    					+ "na janela que irá aparecer para visualizar os acertos e erros. Caso você queira modificar algum valor, é só clicar<br/>"
		    					+ "em não, se em dez segundos o sudoku continuar preenchido irá ser perguntado novamente, se for removido não irá ser<br/>"
		    					+ "pergutado novamente, até que novamente esteja preenchido.</html>";
				JLabel labelName = new JLabel(message);
				JPanel panel = new JPanel();
				panel.add(labelName);
				int result = JOptionPane.showOptionDialog(null, panel, "Como jogar?", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
				if(result == 0) {
					otherWindow = false;	
				}
		    }
		});
	}
	
	/**
	 * O método contactMenuItemListener(JMenuItem contactMenuItem), verifica
	 * se o cursor foi clicado no campo que foi passado por parâmetro,
	 * se sim aparece uma janela com os contatos da pessoa que desenvolveu.
	 * @return void.
	 * */
	public void contactMenuItemListener(JMenuItem contactMenuItem){
		contactMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		    	otherWindow = true;
		    	String[] options = {"Ok"};
				JLabel labelName = new JLabel("<html>Problemas, dúvida e sugestões<br/>Entre em contato conosco pelo e-mail: alexandre.ykz@gmail.com</html>");
				JPanel panel = new JPanel();
				panel.add(labelName);
				int result = JOptionPane.showOptionDialog(null, panel, "Entre em contato conosco", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
				if(result == 0) {
					otherWindow = false;	
				}
		    }
		});
	}

	/**
	 * O método exitMenuItemListener(JMenuItem exitMenuItem), verifica
	 * se o cursor foi clicado no campo que foi passado por parâmetro,
	 * se sim fecha a janela.
	 * @return void.
	 * */
	public void exitMenuItemListener(JMenuItem exitMenuItem){
		exitMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		            System.exit(1);
		    }
		});

	}
	
	public void showRanking(List<Players> listOfPlayers) {
		int numberOfColumns = 2;
		otherWindow = true;
		String[] options = {"Ok"};
		int result;
		if(listOfPlayers.size() == 0) {
			JLabel labelName = new JLabel("<html>Nenhum jogador jogou ainda!<br/>Jogue e tenha seu nome aqui! Boa sorte!</html>");
			JPanel panel = new JPanel();
			panel.add(labelName);
			result = JOptionPane.showOptionDialog(null, panel, "Ranking vazio!", JOptionPane.NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options , options[0]);
		} else {
			Object[][] valueTable = new Object[listOfPlayers.size()][numberOfColumns];
			for(int i = 0; i < listOfPlayers.size(); i++) {
				valueTable[i][0] = listOfPlayers.get(i).getName();
				valueTable[i][1] = Integer.toString(listOfPlayers.get(i).getScore());
			}
			Object[] nameColumns = {"Nome", "Pontuação"};
			JTable ranking = new JTable(valueTable, nameColumns);
			ranking.setDefaultEditor(Object.class, null);
			result = JOptionPane.showOptionDialog(null, new JScrollPane(ranking), "O nosso ranking", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
		}
		if(result == 0) {
			otherWindow = false;
		}
	}

	public void rankMenuItemListener(SudokuInterface sudoku, JMenuItem rankMenuItem){
		rankMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		    	try {
		    		List<Players> listOfPlayers = sudoku.getListOfPlayers();
		    		showRanking(listOfPlayers);
		    	} catch (RemoteException r) {
		    		
		    	}
		    }
		});

	}
}


