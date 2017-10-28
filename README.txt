Alexandre Yuji Kajihara - 1510762
=============================================
		COMPILAR
=============================================
1-Para compilar o servidor: javac Compromisso.java Agenda.java AgendaInterface.java Servidor.java
2-Para compilar o servidor: javac CompromissoGUI.java AgendaGUI.java
=============================================
		EXECUTAR
=============================================
1-Para executar o servidor antes é necessário usar o seguinte comando: rmiregistry -J-Djava.rmi.server.userCodebaseOnly=false
2-Para executar o servidor é: java -Djava.security.policy=policy.txt -Djava.rmi.server.codebase=file: Servidor
3-Para executar o cliente é: java -Djava.security.policy=policy.txt AgendaGUI
=============================================
		OBSERVAÇÃO
=============================================
Para facilitar tudo isso criamos um Makefile
1-make registry
2-make server (que compila e executa)
3-make client
Observação: cada um desses comandos deve ser executado em um terminal diferente
=============================================
		USO
=============================================
1-Antes de realizar qualquer operação você deve dar login.
2-Após isso você pode utilizar qualquer operação.
3-Você pode adicionar passando data, hora, assunto, descrição, notificação.
4-Você pode alterar selecionando um dos compromissos, passando a data que irá se alterar.
5-Você pode remover selecionando um compromisso.
6-Você pode listar um compromisso à partir de uma data.
7-Você pode exibir todos os compromissos.
