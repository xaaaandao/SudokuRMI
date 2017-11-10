import java.rmi.registry.*;

public class Servidor {
	
	/**
	 * O método main(String args[]) instância a classe Sudoku(), registra um objeto remoto e agurda as invocações remotas.
	 * @return void.
	 * */
     public static void main(String args[]) {
       try {
             if (System.getSecurityManager() == null) {
                 System.setSecurityManager(new SecurityManager());
             }

             /* Inicializa um objeto remoto */
             ManageSudoku manageSudoku = new ManageSudoku();

             /* Registra o objeto remoto no binder */
             Registry registry = LocateRegistry.getRegistry("localhost");
	         registry.bind("JogoSudoku", manageSudoku);

	         /* Aguardando invocações remotas */
	         System.out.println("Servidor pronto ...");
	         
	     } catch (Exception e) {
	         System.out.println(e);
         }
     }
}