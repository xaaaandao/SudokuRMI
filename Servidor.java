import java.rmi.registry.*;
import java.util.Scanner;

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
	         Command command = new Command();
        	 command.clearScreen();
	         System.out.println("Servidor pronto ...");
	         
	         /* Executa comandos no servidor */
        	 Scanner scanner = new Scanner(System.in);
        	 String commandToExecute = "";
    		 while(!commandToExecute.equalsIgnoreCase("sair")){
        		 System.out.print(">");
        		 commandToExecute = scanner.nextLine();
        		 System.out.println(command.processCommand(manageSudoku, commandToExecute));
	         }
	         System.exit(1);
	     } catch (Exception e) {
	         System.out.println(e);
         }
     }
}