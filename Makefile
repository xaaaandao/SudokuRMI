registry:
	rmiregistry -J-Djava.rmi.server.userCodebaseOnly=false

clean:
	rm *.class

server:
	javac Sudoku.java SudokuInterface.java Servidor.java
	java -Djava.security.policy=policy.txt -Djava.rmi.server.codebase=file: Servidor

client:
	javac CompromissoGUI.java AgendaGUI.java
	java -Djava.security.policy=policy.txt AgendaGUI

