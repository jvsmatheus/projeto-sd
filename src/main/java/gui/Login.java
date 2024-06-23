package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Cliente.Cliente;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JRadioButton rdbtnEmpresa;
    private JRadioButton rdbtnCandidato;
    private String tipoLogin;
    private Cliente cliente;
    private HashMap<String, String> session;

    /**
     * Create the frame.
     */
    public Login(Cliente cliente) {
        setTitle("Login");
        this.cliente = cliente;
        this.session = new HashMap<String, String>();
        initComponents();
        
     // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }

    public void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 353, 212);
        contentPane = new JPanel();
        contentPane.setToolTipText("");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        rdbtnEmpresa = new JRadioButton("Empresa");
        rdbtnEmpresa.setSelected(false);
        rdbtnEmpresa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rdbtnEmpresa.isSelected()) {
                    tipoLogin = "Empresa";
                }
            }
        });
        buttonGroup.add(rdbtnEmpresa);
        rdbtnEmpresa.setBounds(57, 7, 89, 23);
        contentPane.add(rdbtnEmpresa);

        rdbtnCandidato = new JRadioButton("Candidato");
        rdbtnCandidato.setSelected(false);
        rdbtnCandidato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rdbtnCandidato.isSelected()) {
                    tipoLogin = "Candidato";
                }
            }
        });
        buttonGroup.add(rdbtnCandidato);
        rdbtnCandidato.setBounds(192, 7, 89, 23);
        contentPane.add(rdbtnCandidato);

        txtEmail = new JTextField();
        txtEmail.setBounds(88, 58, 210, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblNewLabel = new JLabel("Email:");
        lblNewLabel.setBounds(40, 61, 38, 14);
        contentPane.add(lblNewLabel);

        txtSenha = new JTextField();
        txtSenha.setColumns(10);
        txtSenha.setBounds(88, 89, 210, 20);
        contentPane.add(txtSenha);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(40, 92, 51, 14);
        contentPane.add(lblSenha);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tipoLogin != null) {
                    JSONObject request = new JSONObject();

                    String email = txtEmail.getText();
                    String senha = txtSenha.getText();

                    request.put("email", email);
                    request.put("senha", senha);
                    request.put("operacao", "login" + tipoLogin);
                    
                    String response = cliente.callServer(request);
                    System.out.println(response);
                    JSONObject json = new JSONObject(response);
                    
                    
                    if (json.getInt("status") == 200) {
                        if (tipoLogin.equals("Candidato")) {
                            session.put("email", email);
                            session.put("token", json.getString("token"));
                            new CandidatoHome(cliente, session).setVisible(true);
                            dispose();
                        }
                        else if(tipoLogin.equals("Empresa")) {
	                        session.put("email", email);
	                        session.put("token", json.getString("token"));
	                        new EmpresaHome(cliente, session).setVisible(true);
	                        dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(Login.this, "Selecione o tipo de login", "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(Login.this, json.getString("mensagem"), "Erro Login",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Selecione o tipo de login", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLogin.setBounds(40, 139, 106, 23);
        contentPane.add(btnLogin);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tipoLogin.equals("Candidato")) {
                    new CadastrarCandidato(cliente).setVisible(true);
                    dispose();
                }
                else if(tipoLogin.equals("Empresa")) {
                	new CadastrarEmpresa(cliente).setVisible(true);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(Login.this, "Selecione o tipo de cadastro", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });
        btnCadastrar.setBounds(192, 139, 106, 23);
        contentPane.add(btnCadastrar);
    }
}
