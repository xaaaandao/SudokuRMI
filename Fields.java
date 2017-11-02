import java.util.*;

import javax.swing.JTextField;

public class Fields{

	private int i;
	private int j;
	private JTextField field = new JTextField();
	
	/**
	 * O construtor Fields(int i, int j, JTextField field), que atribui 
	 * os valores nos atributos daquele objeto.
	 * */
	public Fields(int i, int j, JTextField field){
		this.i = i;
		this.j = j;
		this.field = field;
	}
	
	/**
	 * O método setI(int i), atribui o valor de i no atributo i do objeto. 
	 * @return void.
	 * */
	public void setI(int i){
		this.i = i;
	}

	/**
	 * O método setJ(int j), atribui o valor de j no atributo j do objeto. 
	 * @return void.
	 * */
	public void setJ(int j){
		this.j = j;
	}
	
	/**
	 * O método setJTextField(JTextField field), atribui o valor de field no atributo field do objeto. 
	 * @return void.
	 * */
	public void setJTextField(JTextField field){
		this.field = field;
	}

	/**
	 * O método getI(), retorna o valor do atributo i. 
	 * @return i que é um inteiro com o valor do atributo.
	 * */
	public int getI(){
		return i;
	}

	/**
	 * O método getJ(), retorna o valor do atributo j. 
	 * @return j que é um inteiro com o valor do atributo.
	 * */
	public int getJ(){
		return j;
	}
	
	/**
	 * O método getJTextField(), retorna o valor do atributo field. 
	 * @return field que é um JTextField com o valor do atributo.
	 * */
	public JTextField getJTextField(){
		return field;
	}
}
