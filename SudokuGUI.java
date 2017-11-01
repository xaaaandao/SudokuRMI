import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.text.DocumentFilter.*;

import javafx.stage.WindowEvent;

import javax.swing.*;

public class SudokuGUI {
	
	int row = 9;
	int column = 9;
	
	/**
     * return 1 -> Todos os campos estão preenchidos (Pergunto para o cliente se ele deseja conferir a resposta)
     * return 2 -> Valor adicionado com sucesso
     * return 3 -> Verifico se o usuário que sobrescrever o valor daquela posição
     **/
	public void checkResponseSudoku(SudokuInterface sudoku, int response) {
		switch (response) {
			case 1:
				/* Preciso ter uma interface verificando sim ou não */
				/* Se sim, peço para o servidor me contar a quantidade de erros e acertos, e imprimo na interface */
				if (JOptionPane.showConfirmDialog(null, "Would you like to count the number of hits and errors?", "Finish sudoku", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						String stringHit = Integer.toString(sudoku.countHit());
						String stringError = Integer.toString(sudoku.countError());
						if (JOptionPane.showConfirmDialog(null, "Hits: " + stringHit +"\nErrors: " +  stringError + "\nWould you like to play again?", "Hits ands errors", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
				/* JOptionPane.showMessageDialog (null, "Added value successfully!!!", "Sucessful!", JOptionPane.INFORMATION_MESSAGE);*/
				break;
			case 3:
				/* Preciso ter uma interface verificando sim ou não */
				/* Se sim, substituto o valor que está na minha posição pelo valor que eu quero */
				/*if (JOptionPane.showConfirmDialog(null, "This position is fulfilled! Would you like to override the value of this field?", "Filled position", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				    // yes option
				}*/
				break;
			default:
				break;
		}
	}
	
	public void buildWindowSudoku(SudokuInterface sudoku, int [][]matrixFields, int[][] matrixUser) {
		JFrame window = new JFrame("Play Sudoku");
		JLabel[][] fixContent = new JLabel[row][column];
		JTextField[][] fillContent = new JTextField[row][column];
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(row, column));
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
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
	}
	
	//Checa se o valor é entre 1 e 9, e é só um dígito
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
	
	public void focusField(SudokuInterface sudoku, JTextField field, Fields position) {
	    field.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent e) {
				JTextField input = (JTextField) e.getSource();
				if(input.getText().length() > 0) {
					try {
						int responseServer = sudoku.checkInput(Integer.parseInt(input.getText()), position.getI(), position.getJ());
						checkResponseSudoku(sudoku, responseServer);
					} catch (RemoteException re){
						
					}
				}
			}
		});
	}
}

