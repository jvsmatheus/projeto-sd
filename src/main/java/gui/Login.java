package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Cliente.Cliente;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Cliente cliente;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JRadioButton rdbtnEmpresa;
    private JRadioButton rdbtnCandidato;
    
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Create the frame.
     */
    public Login(Cliente cliente) {
        setTitle("Login");
        this.cliente = cliente;
        initComponents();
    }

    public void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 418, 212);
        contentPane = new JPanel();
        contentPane.setToolTipText("");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        rdbtnEmpresa = new JRadioButton("Empresa");
        buttonGroup.add(rdbtnEmpresa);
        rdbtnEmpresa.setBounds(81, 7, 67, 23);
        contentPane.add(rdbtnEmpresa);

        rdbtnCandidato = new JRadioButton("Candidato");
        buttonGroup.add(rdbtnCandidato);
        rdbtnCandidato.setBounds(218, 7, 75, 23);
        contentPane.add(rdbtnCandidato);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 58, 89, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblNewLabel = new JLabel("Email:");
        lblNewLabel.setBounds(103, 61, 38, 14);
        contentPane.add(lblNewLabel);

        txtSenha = new JTextField();
        txtSenha.setColumns(10);
        txtSenha.setBounds(150, 89, 89, 20);
        contentPane.add(txtSenha);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(103, 92, 38, 14);
        contentPane.add(lblSenha);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoLogin = rdbtnEmpresa.isSelected() ? "Empresa" : "Candidato";
                
                JSONObject request = new JSONObject();
                request.put("email", txtEmail.getText());
                request.put("senha", txtSenha.getText());
                request.put("operacao", "login" + tipoLogin);
                
                String response = cliente.callServer(request);
                JSONObject json = new JSONObject(response);
                if (json.getInt("status") == 200) {
                	if(tipoLogin.equals("Candidato")) {
                		new CandidatoHome(cliente, usuario, email, json.getString("token")).setVisible(true);
                	}else {
                		new EmpresaHome(cliente, usuario, email, json.getString("token")).setVisible(true);
                	}
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, json.getString("mensagem"), "Erro Login", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        btnLogin.setBounds(81, 139, 89, 23);
        contentPane.add(btnLogin);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Adicione sua lógica de cadastro aqui
            }
        });
        btnCadastrar.setBounds(204, 139, 89, 23);
        contentPane.add(btnCadastrar);
    }
}
