import java.util.*;

import javax.swing.JTextField;

/**
 * Descrição: é uma classe que armazena as posições na matriz de um determinado JTextField, e utilizado para saber qual posição foi
 * preenchida pelo jogador.
 * Autor: Alexandre Yuji Kajihara e Rafael Alessandro Ramos
 * Data de criação: 02/11/2017
 * Data de atualização: 12/11/2017
 * */

public class Fields{

	private int i;
	private int j;
	private JTextField field = new JTextField();
	
	/**
	 * O construtor Fields(int i, int j, JTextField field), que atribui 
	 * os valores nos atributos daquele objeto.
	 * @param i é um inteiro com a posição do campo no eixo x.
	 * @param j é um inteiro com a posição do campo no eixo y.
	 * @param field JTextField com a o campo onde será preenchido pelo usuário.
	 * */
	public Fields(int i, int j, JTextField field){
		this.i = i;
		this.j = j;
		this.field = field;
	}
	
	/**
	 * O método setI(int i), atribui o valor de i no atributo i do objeto. 
	 * @param i é um inteiro com a posição do campo no eixo x.
	 * @return void.
	 * */
	public void setI(int i){
		this.i = i;
	}

	/**
	 * O método setJ(int j), atribui o valor de j no atributo j do objeto.
	 * @param j é um inteiro com a posição do campo no eixo y.
	 * @return void.
	 * */
	public void setJ(int j){
		this.j = j;
	}
	
	/**
	 * O método setJTextField(JTextField field), atribui o valor de field no atributo field do objeto.
	 * @param field JTextField com a o campo onde será preenchido pelo usuário.
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
