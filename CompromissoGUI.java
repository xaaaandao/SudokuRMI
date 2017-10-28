/**
 * Descrição: classe de interface gráfica é uma nova janela que irá ter os campos para
 * adicionar um compromisso.
 * Autor: Alexandre Yuji Kajihara
 * Data de criação: 21/10/17
 * Data de atualização: 21/10/17
 */
public class CompromissoGUI extends javax.swing.JDialog {
	
	/* Atributos */
    public enum ActionType {ADD,CANCEL};
    private ActionType actionExecuted;
    private javax.swing.JButton buttonAdicionar;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField textFieldAssunto;
    private javax.swing.JTextField textFieldData;
    private javax.swing.JTextField textFieldDescricao;
    private javax.swing.JTextField textFieldHora;
    private javax.swing.JTextField textFieldNotificacao;    

    /**
     * Construtor da classe CompromissoGUI.
     */
    public CompromissoGUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * Inicializa os componentes presentes na interface gráfica.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textFieldData = new javax.swing.JTextField();
        textFieldHora = new javax.swing.JTextField();
        textFieldAssunto = new javax.swing.JTextField();
        textFieldDescricao = new javax.swing.JTextField();
        textFieldNotificacao = new javax.swing.JTextField();
        buttonAdicionar = new javax.swing.JButton();
        buttonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adiciona compromissos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Data:");

        jLabel2.setText("Hora:");

        jLabel3.setText("Assunto:");

        jLabel4.setText("Descrição:");

        jLabel5.setText("Notificação:");

        buttonAdicionar.setText("Adicionar");
        buttonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdicionarActionPerformed(evt);
            }
        });

        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldData)
                        .addGap(199, 199, 199))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldHora, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldNotificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonAdicionar)
                .addGap(18, 18, 18)
                .addComponent(buttonCancelar)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textFieldHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFieldAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(textFieldNotificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAdicionar)
                    .addComponent(buttonCancelar))
                .addGap(22, 22, 22))
        );

        pack();
    }

    /**
     * Evento relacionado ao botão cancelar presente na interface gráfica para adicionar um novo compromisso.
     * @param evt evento relacionado ao botão cancelar na interface gráfica.
     */
    private void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.actionExecuted = ActionType.CANCEL;
        this.limparCampos();
        this.setVisible(false);
    }

    /**
     * Evento relacionado ao campo do focus no campo data.
     * @param evt evento relacionado ao focus na entrada data na interface gráfica.
     */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        textFieldData.requestFocus();
    }

    /**
     * Evento relacionado ao botão adicionar presente na interface gráfica para adicionar um novo compromisso.
     * @param evt evento relacionado ao botão adicionar na interface gráfica.
     */
    private void buttonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {
        this.actionExecuted = ActionType.ADD;
        this.setVisible(false);
    }

    /**
     * Método relacionado para limpar os campos da interface gráfica quando adicionamos um novo compromisso.
     */
    private void limparCampos() {
        textFieldAssunto.setText(null);
        textFieldData.setText(null);
        textFieldDescricao.setText(null);
        textFieldHora.setText(null);
        textFieldNotificacao.setText(null);
    }
    
    /**
     * Método relacionado que obtém os valores que estão nos campos da interface gráfica.
     * @return dados que foram obtidos nos campos preenchidos.
     */
    public String[] obtemDados() {
        String[] dados =  new String[]{textFieldData.getText(), textFieldHora.getText(),
        textFieldAssunto.getText(), textFieldDescricao.getText(), textFieldNotificacao.getText()};
        this.limparCampos();
        return dados;
    }
    
    /**
     * Método relacionado que retorna uma ação executada.
     * @return actionExecuted ação que foi executada.
     */
    public ActionType getActionExecuted() {
        return actionExecuted;
    }
    
    /**
     * Inicializa a interface gráfica.
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompromissoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompromissoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompromissoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompromissoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CompromissoGUI dialog = new CompromissoGUI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

}
