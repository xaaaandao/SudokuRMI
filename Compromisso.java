import java.io.*;

/**
 * Descrição: classe que será na classe AgendaGUI e Agenda uma lista de compromisso. 
 * Autor: Alexandre Yuji Kajihara
 * Data de criação: 21/10/17
 * Data de atualização: 21/10/17
 */

public class Compromisso implements Serializable{
	/* Atributos */
	static int id = 0;
	String data;
	String hora;
	String assunto;
	String descricao;
	String notificacao;
	
	/**
     * Construtor da classe Compromisso.
     * @param data data do compromisso.
     * @param hora hora do compromisso.
     * @param assunto assunto do compromisso.
     * @param descricao descrição do compromisso.
     * @param notificacao notificação do compromisso.
     */
	public Compromisso(String data, String hora, String assunto, String descricao, String notificacao){
		this.id++;
		this.data = data;
		this.hora = hora;
		this.assunto = assunto;
		this.descricao = descricao;
		this.notificacao = notificacao;
	}
}
