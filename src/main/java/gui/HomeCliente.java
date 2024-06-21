package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Cliente.Cliente;


@SuppressWarnings("serial")
public class HomeCliente extends javax.swing.JFrame {

    private Cliente cliente;
    
    private JButton btnConectar;
    private JTextField txtGetIp; 
    private JLabel lblIp;

    public HomeCliente() {
    	setTitle("Conexão");
        initComponents();
    }

    private void initComponents() {

        lblIp = new JLabel();
        txtGetIp = new JTextField();
        btnConectar = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblIp.setText("IP do servidor:");
        txtGetIp.setText("192.168.1.2");

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnConectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConectar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIp)
                        .addGap(18, 18, 18)
                        .addComponent(txtGetIp)))
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIp)
                    .addComponent(txtGetIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(btnConectar)
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void bttnConectarActionPerformed(java.awt.event.ActionEvent evt) {
        String ipServidor = this.txtGetIp.getText();

        this.cliente = new Cliente(ipServidor);
        boolean isConnected = this.cliente.connect();
        if (isConnected) {
            new Login(cliente).setVisible(true);;
            dispose();
        } else {
            JOptionPane.showMessageDialog(HomeCliente.this, "Erro ao conectar ao servidor. Verifique o IP e tente novamente.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }

    }
}