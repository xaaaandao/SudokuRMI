import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.text.DocumentFilter.*;

import javax.swing.*;

public class SudokuGUI {
	
	int row = 9;
	int column = 9;
	
	public void buildWindowSudoku(SudokuInterface sudoku, int [][]matrix) {
		JFrame window = new JFrame("Play Sudoku");
		JLabel[][] fixContent = new JLabel[row][column];
		JTextField[][] fillContent = new JTextField[row][column];
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(row, column));
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				if(matrix[i][j] == 0) {
					fillContent[i][j] = new JTextField(1);
				    fillContent[i][j].setHorizontalAlignment(JTextField.CENTER);
					PlainDocument document = (PlainDocument) fillContent[i][j].getDocument();
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
				             if (intValue < 0 || intValue > 99)
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

				    Fields field = new Fields(i, j, fillContent[i][j]);
				    fillContent[i][j].addFocusListener(new java.awt.event.FocusAdapter() {
						public void focusLost(java.awt.event.FocusEvent e) {
							JTextField input = (JTextField) e.getSource();
							//try {
								//sudoku.checkInput(9, 9);
								if(input.getText().length() > 0) {
									System.out.println("xii nao tem nada");
								}
								System.out.println("->"+input.getText());
								System.out.println("i"+field.getI());
								System.out.println("j"+field.getJ());
							//} catch (RemoteException re) {
								
							//}
						}
					});
					panel.add(fillContent[i][j]);
				} else {
					fixContent[i][j] = new JLabel(Integer.toString(matrix[i][j]), SwingConstants.CENTER);
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
}

