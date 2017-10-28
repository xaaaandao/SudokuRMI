registry:
	rmiregistry -J-Djava.rmi.server.userCodebaseOnly=false

clean:
	rm *.class

server:
	javac Sudoku.java SudokuInterface.java Servidor.java
	java -Djava.security.policy=policy.txt -Djava.rmi.server.codebase=file: Servidor

client:
	javac SudokuGUI.java Cliente.java
	java -Djava.security.policy=policy.txt Cliente

git:
	git checkout master
	git pull origin master
	git merge xandao
	git push origin master
	git checkout xandao