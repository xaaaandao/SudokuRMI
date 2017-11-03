import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.text.DocumentFilter.*;
import javafx.stage.*;
import javax.swing.*;
import java.util.Timer;

public class SudokuGUI {
	
	int rows = 9;
	int columns = 9;
	SudokuInterface s;
	
	public SudokuGUI(SudokuInterface sudoku) {
		s = sudoku;
	}
	
	public int allFillFields() {
		if (JOptionPane.showConfirmDialog(null, "Acabou! Você deseja ver a quantidade de acertos e erros?", "Todas as posições preenchidas", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			try {
				String stringHit = Integer.toString(s.countHit());
				String stringError = Integer.toString(s.countError());
				if (JOptionPane.showConfirmDialog(null, "Acertos: " + stringHit +"\nErros: " +  stringError + "\nVocê deseja jogar novamente?", "Acertos e erros", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					//Invocar o método que pede novo sudoku
					return 1;
				} else {
					//Senão pede fecha o sudoku
					System.exit(1);
				}
			} catch (RemoteException re){
				
			}
		} 
		return 2;
	}
	
	
	
	/**
	 * O método checkResponseSudoku(SudokuInterface sudoku, int response, int value, Fields field), interpreta
	 * a resposta que foi dada ao servidor, se for um significa que todos os campos já foram preenchidos, se for
	 * dois que o valor foi adicionado correamente, e por fim três que verifica se o usuário deseja ou não
	 * sobrescrever ou não o valor.
	 * @return void.
	 * */
	public void checkResponseSudoku(SudokuInterface sudoku, int response, int value, Fields field) {
		switch (response) {
			case 1:
				/* Preciso ter uma interface verificando sim ou não */
				/* Se sim, peço para o servidor me contar a quantidade de erros e acertos, e imprimo na interface */
				
				break;
			case 2:
				/* Valor adicionado com sucesso */
				break;
			case 3:
				/* Caso que o jogador esteja sobescrevendo seja igual ao valor que já esteja lá */
				break;
			case 4:
				/* Caso que o jogador esteja sobescrevendo seja diferente ao valor que já esteja lá */
				if (JOptionPane.showConfirmDialog(null, "Você deseja sobrescrever o valor?", "Posição já preenchida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						sudoku.replaceValue(value, field.getI(), field.getJ());
					} catch (RemoteException r) {
						
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
		System.out.println("Na GUI");
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
	
	/**
	 * O método buildWindowSudoku(SudokuInterface sudoku, int [][]matrixFields, int[][] matrixUser), primeiramente
	 * verifica se a interface gráfica já foi criada ou não, se já tiver sido criada percorre na matrixFields
	 * verifica se na posição que está sendo percorrida é zero se for zero vai ser um JTextField, caso contrário
	 * JLabel. Caso a interface gráfica já esteja montada, verifica se matriz que está sendo exibida e a matriz
	 * que está sendo preenchida pelos usuários no servidor é diferente, se for diferente atualiza com os novos valores.
	 * @return void.
	 * */
	public void buildWindowSudoku(SudokuInterface sudoku, int [][]matrixFields, int[][] matrixUser) {
		boolean buildGUI = false;
		JFrame window = new JFrame("Jogue Sudoku");
		JLabel[][] fixContent = new JLabel[rows][columns];
		JTextField[][] fillContent = new JTextField[rows][columns];
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns));
		
		/* A cada um segundo recebe a matrix do jogador que está no servidor  */
		Timer timerSudokuUpdate = new Timer();
		int [][]matrixUpdate = new int[rows][columns]; 
		SudokuUpdate sudokuUpdate = new SudokuUpdate(sudoku);
		timerSudokuUpdate.schedule(sudokuUpdate, 0, 1000);
		
		/* A cada um segundo verifica se todas as colunas estão preenchidas */
		Timer timerSudokuFinish = new Timer();
		SudokuFinish sudokuFinish = new SudokuFinish(sudoku);
		long timerFinish = 1000;
		timerSudokuFinish.schedule(sudokuFinish, 0, timerFinish);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu optionsMenu = new JMenu("Opções");
		JMenuItem finishMenuItem = new JMenuItem("Fim de jogo");
		JMenuItem exitMenuItem = new JMenuItem("Sair");
		JMenu helpMenu = new JMenu("Ajuda");
		JMenuItem contactMenuItem = new JMenuItem("Entre em contato");
		
		while(true) {
			/* Se eu não tiver montado a GUI monto ela */
			if(buildGUI == false) {
				optionsMenu.add(finishMenuItem);
				optionsMenu.add(exitMenuItem);

				helpMenu.add(contactMenuItem);
				
				menuBar.add(optionsMenu);
				menuBar.add(helpMenu);

	    		finishMenuItemListener(sudoku, finishMenuItem);
				exitMenuItemListener(exitMenuItem);
				contactMenuItemListener(contactMenuItem);
				for(int i = 0; i < rows; i++) {
					for(int j = 0; j < columns; j++) {
						if(matrixFields[i][j] == 0) {
							fillContent[i][j] = new JTextField(1);
						    fillContent[i][j].setHorizontalAlignment(JTextField.CENTER);
						    if(matrixUser[i][j] > 0) {
						    	fillContent[i][j].setText(Integer.toString(matrixUser[i][j]));
						    }
							PlainDocument document = (PlainDocument) fillContent[i][j].getDocument();
							checkValue(document);
						    /* Fields pega a posição que o cliente está preenchendo */
						    Fields field = new Fields(i, j, fillContent[i][j]);
						    focusField(sudoku, fillContent[i][j], field);
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
				window.setLocationRelativeTo(null);
				window.setVisible(true);
				buildGUI = true;
			/* Caso já tenha a GUI montada */
			} else {		
				//System.out.println("passo daqui");
				/* Verifico a matrix que tá sendo recebida é diferenta tá que eu tô */
				matrixUpdate = sudokuUpdate.getMatrix();
				if(!compareMatrix(matrixUpdate, matrixUser)) {
					for(int i = 0; i < rows; i++) {
						for(int j = 0; j < columns; j++) {
							if(matrixFields[i][j] == 0) {
								if(matrixUpdate[i][j] > 0) {
									fillContent[i][j].setText(Integer.toString(matrixUpdate[i][j]));	
								} else {
									fillContent[i][j].setText("");
								}
							}
						}
					}
					copyMatrix(matrixUser, matrixUpdate);
				}
			}
			/* Verifico se todas as posições estão preenchidas */
			//System.out.println("acabou? "+sudokuFinish.getIsFinish());
			if(sudokuFinish.getIsFinish()) {
				int responseFinish = allFillFields();
				if(responseFinish == 1) {
					//New game
				} else if(responseFinish == 2){
					//System.out.println("aq");
					if(timerFinish == 1000) {
						timerFinish = 5000;
					} else {
						timerFinish = timerFinish + 5000;
					}
					timerSudokuFinish.cancel();
					timerSudokuFinish = new Timer();
					sudokuFinish = new SudokuFinish(sudoku); 
					timerSudokuFinish.schedule(sudokuFinish, timerFinish);
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
	public void focusField(SudokuInterface sudoku, JTextField field, Fields position) {
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
						int responseServer = sudoku.checkInput(value, position.getI(), position.getJ());
						checkResponseSudoku(sudoku, responseServer, value, position);
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
	
	public void exitMenuItemListener(JMenuItem exitMenuItem){
		exitMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		            System.exit(1);
		    }
		});

	}
	
	
	public void contactMenuItemListener(JMenuItem contactMenuItem){
		contactMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		    	JOptionPane.showMessageDialog (null, "Problemas, dúvida e sugestões?\nEntre em contato conosco pelo e-mail: alexandre.ykz@gmail.com", "Entre em contato conosco", JOptionPane.INFORMATION_MESSAGE);

		    }
		});
	}
	
	public void finishMenuItemListener(SudokuInterface sudoku, JMenuItem finishMenuItem){
		finishMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	    		allFillFields();
		    }
		});
	}
	
}


