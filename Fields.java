import java.util.*;

import javax.swing.JTextField;

public class Fields{

	private int i;
	private int j;
	private JTextField field = new JTextField();
	
	public Fields(int i, int j, JTextField field){
		this.i = i;
		this.j = j;
		this.field = field;
	}
	
	public void setI(int i){
		this.i = i;
	}

	public void setJ(int j){
		this.j = j;
	}
	
	public void setJTextField(JTextField field){
		this.field = field;
	}
	
	public int getI(){
		return i;
	}

	public int getJ(){
		return j;
	}
	
	public JTextField getJTextField(){
		return field;
	}
}
