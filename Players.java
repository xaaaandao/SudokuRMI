import java.io.*;
import java.util.*;

public class Players implements Serializable{

	private String name;
	private int score;
	
	public void setName(String name){
		this.name = name;
	}

	public void setScore(int score){
		this.score = score;
	}
	
	public String getName(){
		return name;
	}

	public int getScore(){
		return score;
	}
}
