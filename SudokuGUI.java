import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import javax.swing.*;

public class SudokuGUI {
	public void waitSudoku(){
		JFrame waitSudoku = new JFrame();
		JOptionPane.showMessageDialog(waitSudoku, "Wait for sudoku...", "Wait for sudoku...",
	            JOptionPane.INFORMATION_MESSAGE);
	}
}
