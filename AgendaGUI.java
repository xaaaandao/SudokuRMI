import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Descrição: classe de interface gráfica onde os dados presentes neles, vão ser passados 
 * para o servidor que irá adicionar, remover, alterar, exibir ou listar os itens presentes na lista
 * de compromisso.
 * Autor: Alexandre Yuji Kajihara
 * Data de criação: 21/10/17
 * Data de atualização: 21/10/17
 */

public class AgendaGUI extends javax.swing.JFrame {

	/* Atributos */
    private CompromissoGUI dialogCompromisso;
    private DefaultTableModel modelAgenda;
    private javax.swing.JButton buttonAdicionar;
    private javax.swing.JButton buttonAlterar;
    private javax.swing.JButton buttonListar;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonRemover;
    private javax.swing.JButton buttonExibir;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelAgenda;
    private javax.swing.JPanel panelCompromissos;
    private javax.swing.JPanel panelSistema;
    private javax.swing.JTable tableAgenda;
    private javax.swing.JTextField textFieldUsuario;
    static AgendaInterface agendaGlobal;
    static int quantidadeCompromisso;
    static List<Compromisso> listaCompromisso;
    
    /**
     * Construtor da classe AgendaGUI.
     */
    public AgendaGUI() {
        initComponents();
        dialogCompromisso = new CompromissoGUI(this, true);
        modelAgenda = (DefaultTableModel) tableAgenda.getModel();
    }

    /**
     * Inicializa os componentes presentes na interface gráfica.
     */
    private void initComponents() {
        panelCompromissos = new javax.swing.JPanel();
        buttonAdicionar = new javax.swing.JButton();
        buttonAlterar = new javax.swing.JButton();
        buttonListar = new javax.swing.JButton();
        buttonRemover = new javax.swing.JButton();
        buttonExibir = new javax.swing.JButton();
        panelSistema = new javax.swing.JPanel();
        buttonLogin = new javax.swing.JButton();
        buttonLogout = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        textFieldUsuario = new javax.swing.JTextField();
        panelAgenda = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAgenda = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agenda Remota");

        panelCompromissos.setBorder(javax.swing.BorderFactory.createTitledBorder(" Compromissos "));
        panelCompromissos.setName("panelCompromissos");

        buttonAdicionar.setText("Adicionar");
        buttonAdicionar.setName("buttonAdicionar");
        buttonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdicionarActionPerformed(evt);
            }
        });

        buttonAlterar.setText("Alterar");
        buttonAlterar.setName("buttonAlterar");
        buttonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlterarActionPerformed(evt);
            }
        });

        buttonListar.setText("Listar");
        buttonListar.setName("buttonListar");
        buttonListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonListarActionPerformed(evt);
            }
        });

        buttonRemover.setText("Remover");
        buttonRemover.setName("buttonRemover");
        buttonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoverActionPerformed(evt);
            }
        });

        buttonExibir.setText("Exibir");
        buttonExibir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCompromissosLayout = new javax.swing.GroupLayout(panelCompromissos);
        panelCompromissos.setLayout(panelCompromissosLayout);
        panelCompromissosLayout.setHorizontalGroup(
            panelCompromissosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompromissosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCompromissosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonExibir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelCompromissosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonAdicionar, buttonAlterar, buttonListar, buttonRemover});

        panelCompromissosLayout.setVerticalGroup(
            panelCompromissosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompromissosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRemover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonListar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExibir)
                .addContainerGap())
        );

        buttonExibir.getAccessibleContext().setAccessibleDescription("");

        panelSistema.setBorder(javax.swing.BorderFactory.createTitledBorder(" Sistema "));
        panelSistema.setName("panelSistema"); 

        buttonLogin.setText("Login");
        buttonLogin.setName("buttonLogin");
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginActionPerformed(evt);
            }
        });

        buttonLogout.setText("Logout");
        buttonLogout.setName("buttonLogout");
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSistemaLayout = new javax.swing.GroupLayout(panelSistema);
        panelSistema.setLayout(panelSistemaLayout);
        panelSistemaLayout.setHorizontalGroup(
            panelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSistemaLayout.setVerticalGroup(
            panelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelUsuario.setText("Usuário: ");
        labelUsuario.setName("labelUsuario");

        textFieldUsuario.setEditable(false);
        textFieldUsuario.setText("Turma de SD");
        textFieldUsuario.setName("textFieldUsuario");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUsuario)
                .addGap(4, 4, 4)
                .addComponent(textFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario)
                    .addComponent(textFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelAgenda.setBorder(javax.swing.BorderFactory.createTitledBorder(" Agenda "));
        panelAgenda.setName("panelAgenda");

        tableAgenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"17/05/2010", "20:50:00", "Prova SD", "Prova de sistemas distribuídos sobre comunicação entre processos", "SIM"}
            },
            new String [] {
                "Data", "Hora", "Assunto", "Descrição", "Notificação"
            }
        ));
        tableAgenda.setName("tableAgenda");
        jScrollPane1.setViewportView(tableAgenda);
        if (tableAgenda.getColumnModel().getColumnCount() > 0) {
            tableAgenda.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableAgenda.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableAgenda.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableAgenda.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        javax.swing.GroupLayout panelAgendaLayout = new javax.swing.GroupLayout(panelAgenda);
        panelAgenda.setLayout(panelAgendaLayout);
        panelAgendaLayout.setHorizontalGroup(
            panelAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAgendaLayout.setVerticalGroup(
            panelAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgendaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelCompromissos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCompromissos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelAgenda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Evento relacionado ao botão adicionar presente na interface gráfica para adicionar um compromisso.
     * @param evt evento relacionado ao botão adicionar na interface gráfica.
     */
    private void buttonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {
    	JPanel panel = new JPanel();
    	dialogCompromisso.setVisible(true);

        /* Se a ação executa da dialog CompromissoGUI foi "Adicionar" */
        if (dialogCompromisso.getActionExecuted() == CompromissoGUI.ActionType.ADD) {
            /* Não obtive um objeto remoto */
            if(agendaGlobal == null) {
        		JOptionPane.showMessageDialog(panel, "Objeto remoto não disponível", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String[] dados = dialogCompromisso.obtemDados();
                modelAgenda.addRow(dados);
            	/* Obtive um objeto remoto */
            	try {
            		Compromisso c = new Compromisso((String)modelAgenda.getValueAt(quantidadeCompromisso, 0), (String)modelAgenda.getValueAt(quantidadeCompromisso, 1), (String)modelAgenda.getValueAt(quantidadeCompromisso, 2), (String)modelAgenda.getValueAt(quantidadeCompromisso, 3), (String)modelAgenda.getValueAt(quantidadeCompromisso, 4));
            		quantidadeCompromisso++;
            		agendaGlobal.adiciona(c);
            	} catch (Exception e) {
            		System.out.println(e);
            	}
            }
        }
    }

    /**
     * Evento relacionado ao botão alterar presente na interface gráfica para alterar um compromisso.
     * @param evt evento relacionado ao botão alterar na interface gráfica.
     */
    private void buttonAlterarActionPerformed(java.awt.event.ActionEvent evt) {
        /* Pega os valores de uma linha selecionada */
        int linha = tableAgenda.getSelectedRow();
        if (linha >= 0) {
            String data = (String) modelAgenda.getValueAt(linha, 0);
            String hora = (String) modelAgenda.getValueAt(linha, 1);
            String assunto = (String) modelAgenda.getValueAt(linha, 2);
            String descricao = (String) modelAgenda.getValueAt(linha, 3);
            String notificacao = (String) modelAgenda.getValueAt(linha, 4);
        	
            String dataNova = JOptionPane.showInputDialog("Alterar para que data em: ", "digite a data");
            JPanel panel = new JPanel();
        	
            /* Se não tiver obtido uma referência remota */
            if(agendaGlobal == null) {
            	JOptionPane.showMessageDialog(panel, "Objeto remoto não disponível", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	/* Através da data ele vai alterar */
            	try {
            		agendaGlobal.altera(dataNova, hora, assunto, descricao, notificacao);
            	} catch (Exception e) {
                    System.out.println(e);
                }
            }
        } 
    }

    /**
     * Evento relacionado ao botão remover presente na interface gráfica para remover um compromisso.
     * @param evt evento relacionado ao botão remover na interface gráfica.
     */
    private void buttonRemoverActionPerformed(java.awt.event.ActionEvent evt) {
        /* Remove a linha selecionada */
        int linha = tableAgenda.getSelectedRow();
        if (linha >= 0) {
            /* Pega os campos chaves */
            String data = (String) modelAgenda.getValueAt(linha, 0);
            String hora = (String) modelAgenda.getValueAt(linha, 1);

            /* Remove da GUI */
            modelAgenda.removeRow(linha);
            try {
            	/* Remover da lista */
                agendaGlobal.remove(data, hora);	
            } catch (Exception e) {
            	System.out.println(e);
            }
        }
    }

    
    /**
     * Evento relacionado ao botão remover presente na interface gráfica para listar um compromisso.
     * @param evt evento relacionado ao botão listar na interface gráfica.
     */
    private void buttonListarActionPerformed(java.awt.event.ActionEvent evt) {
        String data = JOptionPane.showInputDialog("Listar compromissos em: ", "digite a data");
        JPanel panel = new JPanel();
    	
        /* Se não tiver obtido uma referência remota */
        if(agendaGlobal == null) {
        	JOptionPane.showMessageDialog(panel, "Objeto remoto não disponível", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        	try {
        		List<Compromisso> listaCompromissoEspecifico = agendaGlobal.lista(data);
	        	/* Se não tiver nenhuma compromisso nesse dia*/
	        	if(listaCompromissoEspecifico == null) {
	        		JOptionPane.showMessageDialog(panel, "Nessa data não existe compromisso", "Error", JOptionPane.ERROR_MESSAGE);
	        	/* Se tiver compromisso(s) */
	        	} else {
	        		for(int i = 0; i < modelAgenda.getRowCount(); i++) {
	            		modelAgenda.removeRow(i);
	            	}
	            	for(int i = 0; i < listaCompromissoEspecifico.size(); i++) {
	            		modelAgenda.setValueAt(listaCompromissoEspecifico.get(i).data, i, 0);
	            		modelAgenda.setValueAt(listaCompromissoEspecifico.get(i).hora, i, 1);
	            		modelAgenda.setValueAt(listaCompromissoEspecifico.get(i).assunto, i, 2);
	            		modelAgenda.setValueAt(listaCompromissoEspecifico.get(i).descricao, i, 3);
	            		modelAgenda.setValueAt(listaCompromissoEspecifico.get(i).notificacao, i, 4);
	            	}
	        	}
        	} catch (Exception e) {
                System.out.println(e);
            }
        }
        
    }

    /**
     * Evento relacionado ao botão login presente na interface gráfica para obter a referência de um objeto remoto.
     * @param evt evento relacionado ao botão login na interface gráfica.
     */
    private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt) {
    	try {
            if (System.getSecurityManager() == null) {
               System.setSecurityManager(new SecurityManager());
            }

            /* Se já tiver alguma coisa cadastrada já removo */
            for(int i = 0; i < modelAgenda.getRowCount(); i++) {
    			modelAgenda.removeRow(i);
    		}
            
            /* Obtém a referência para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            AgendaInterface agenda = (AgendaInterface)registry.lookup("ServicoAgenda");
            agendaGlobal = agenda;
            
            quantidadeCompromisso = 0;
        } catch (Exception e) {
           System.out.println(e);
        }
    }

    /**
     * Evento relacionado ao botão logout presente na interface gráfica para não realizar o acesso remoto.
     * @param evt evento relacionado ao botão logout na interface gráfica.
     */
    private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt){
    	try {
    		/* Se já tiver alguma coisa cadastrada já removo */
            for(int i = 0; i < modelAgenda.getRowCount(); i++) {
    			modelAgenda.removeRow(i);
    		}
    		
    		Registry registry = LocateRegistry.getRegistry("localhost");
    		registry.unbind("ServicoAgenda");
    		agendaGlobal = null;
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }

    /**
     * Evento relacionado ao botão exibir presente na interface gráfica para listar os compromisso presentes na agenda.
     * @param evt evento relacionado ao botão exibir na interface gráfica.
     */
    private void buttonExibirActionPerformed(java.awt.event.ActionEvent evt) {
    	 JPanel panel = new JPanel();
     	
         /* Se não tiver obtido uma referência remota */
         if(agendaGlobal == null) {
         	JOptionPane.showMessageDialog(panel, "Objeto remoto não disponível", "Error", JOptionPane.ERROR_MESSAGE);
         } else {
         	try {
         		List<Compromisso> todosCompromissos = agendaGlobal.exibe();
 	        	/* Se não tiver nenhuma compromisso nesse dia*/
 	        	if(todosCompromissos == null) {
 	        		JOptionPane.showMessageDialog(panel, "Não existe compromisso", "Error", JOptionPane.ERROR_MESSAGE);
 	        	/* Se tiver compromisso(s) */
 	        	} else {
 	        		for(int i = 0; i < modelAgenda.getRowCount(); i++) {
 	            		modelAgenda.removeRow(i);
 	            	}
 	            	for(int i = 0; i < todosCompromissos.size(); i++) {
 	            		modelAgenda.setValueAt(todosCompromissos.get(i).data, i, 0);
 	            		modelAgenda.setValueAt(todosCompromissos.get(i).hora, i, 1);
 	            		modelAgenda.setValueAt(todosCompromissos.get(i).assunto, i, 2);
 	            		modelAgenda.setValueAt(todosCompromissos.get(i).descricao, i, 3);
 	            		modelAgenda.setValueAt(todosCompromissos.get(i).notificacao, i, 4);
 	            	}
 	        	}
         	} catch (Exception e) {
                 System.out.println(e);
            }
         }
    }

    /**
     * Inicializa a lista de compromisso e interface gráfica.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	listaCompromisso = new ArrayList<Compromisso>();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgendaGUI().setVisible(true);
            }
        });
    }
}
