import java.io.*;
import java.util.*;

public class Players implements Serializable{

	private String name;
	private int score;
		
	/**
	 * O método setName(String name), atribui o valor de name no atributo name do objeto.
	 * @param name é uma String com o nome do jogador. 
	 * @return void.
	 * */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * O método setScore(int score), atribui o valor de score no atributo score do objeto. 
	 * @param score é um inteiro com a pontuação do jogador. 
	 * @return void.
	 * */
	public void setScore(int score){
		this.score = score;
	}

	/**
	 * O método getName(), retorna o valor do atributo name. 
	 * @return name que é uma String com o valor do atributo.
	 * */
	public String getName(){
		return name;
	}

	/**
	 * O método getScore(), retorna o valor do atributo score. 
	 * @return score que é um inteiro com o valor do atributo.
	 * */
	public int getScore(){
		return score;
	}
}
