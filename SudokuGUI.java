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
				if (JOptionPane.showConfirmDialog(null, "Você deseja ver a quantidade de acertos e erros?", "Todas as posições preenchidas", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						String stringHit = Integer.toString(sudoku.countHit());
						String stringError = Integer.toString(sudoku.countError());
						if (JOptionPane.showConfirmDialog(null, "Acertos: " + stringHit +"\nErros: " +  stringError + "\nVocê deseja jogar novamente?", "Acertos e erros", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							//Invocar o método que pede novo sudoku
						} else {
							//Senão pede fecha o sudoku
							System.exit(1);
						}
					} catch (RemoteException re){
						
					}
				}
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
		Timer timer = new Timer();
		int [][]matrixUpdate = new int[rows][columns]; 
		SudokuUpdate sudokuUpdate = new SudokuUpdate(sudoku); 
		timer.schedule(sudokuUpdate, 0, 1000);
		
		
		while(true) {
			/* Se eu não tiver montado a GUI monto ela */
			if(buildGUI == false) {
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
				
				window.add(panel, BorderLayout.CENTER);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setSize(600, 600);
				window.setLocationRelativeTo(null);
				window.setVisible(true);
				buildGUI = true;
			/* Caso já tenha a GUI montada */
			} else {
				/* Verifico a matrix que tá sendo recebida é diferenta tá que eu tô */
				matrixUpdate = sudokuUpdate.getMatrix();
				if(!compareMatrix(matrixUpdate, matrixUser)) {
					for(int i = 0; i < rows; i++) {
						for(int j = 0; j < columns; j++) {
							if(matrixFields[i][j] == 0) {
								fillContent[i][j].setText(Integer.toString(matrixUpdate[i][j]));
							}
						}
					}
					copyMatrix(matrixUser, matrixUpdate);
				}
			}
			/* Verifico se todas as posições estão preenchidas */
			/*try {
				if(sudoku.countFillFields() == 0) {
					checkResponseSudoku(sudoku, 1, 0, null);
				}
			} catch (RemoteException re) {
				
			}*/
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
				if(input.getText().length() > 0) {
					try {
						int responseServer = sudoku.checkInput(Integer.parseInt(input.getText()), position.getI(), position.getJ());
						checkResponseSudoku(sudoku, responseServer, Integer.parseInt(input.getText()), position);
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
	
}


