registry:
	rmiregistry -J-Djava.rmi.server.userCodebaseOnly=false

clean:
	rm *.class

compile:
	javac Sudoku.java SudokuInterface.java Servidor.java
	javac SudokuGUI.java Cliente.java

server:
	javac Sudoku.java SudokuInterface.java Servidor.java
	java -Djava.security.policy=policy.txt -Djava.rmi.server.codebase=file: Servidor

client:
<<<<<<< HEAD
	javac SudokuGUI.java SudokuInterface.java Cliente.java
=======
	javac SudokuGUI.java Fields.java Cliente.java
>>>>>>> xandao
	java -Djava.security.policy=policy.txt Cliente

git:
	git checkout master
	git pull origin master
	git merge xandao
	git push origin master
	git checkout xandao
