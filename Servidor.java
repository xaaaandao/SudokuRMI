import java.rmi.registry.*;

public class Servidor {
     public static void main(String args[]) {
       try {
             if (System.getSecurityManager() == null) {
                 System.setSecurityManager(new SecurityManager());
             }

             /* Inicializa um objeto remoto */
             Sudoku sudoku = new Sudoku();

             /* Registra o objeto remoto no Binder */
             Registry registry = LocateRegistry.getRegistry("localhost");
	         registry.bind("JogoSudoku", sudoku);

	         /* Aguardando invocações remotas */
	         System.out.println("Servidor pronto ...");
	         
	     } catch (Exception e) {
	         System.out.println(e);
         }
     }
}