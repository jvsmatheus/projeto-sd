package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Cliente.Cliente;

public class CadastrarEmpresa extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtRazaoSocial;
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JTextArea txtDescricao;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JButton btnCadastrar;

    private Cliente cliente;
    private HashMap<String, String> session;
    private JTextField txtCnpj;
    private JLabel lblNewLabel_3;
    private JTextField txtRamo;

    public CadastrarEmpresa(Cliente cliente) {
        setTitle("Cadastrar Empresa");
        this.cliente = cliente;
        this.session = new HashMap<>(); // Inicializa a variável session

        initComponents();
        
     // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 377, 373);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtRazaoSocial = new JTextField();
        txtRazaoSocial.setBounds(96, 11, 252, 20);
        contentPane.add(txtRazaoSocial);
        txtRazaoSocial.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setBounds(96, 42, 252, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        txtSenha = new JTextField();
        txtSenha.setBounds(96, 103, 252, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        lblNewLabel = new JLabel("Razão Social:");
        lblNewLabel.setBounds(10, 14, 76, 14);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(10, 45, 46, 14);
        contentPane.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Senha:");
        lblNewLabel_2.setBounds(10, 106, 46, 14);
        contentPane.add(lblNewLabel_2);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
        btnCadastrar.setBounds(96, 301, 169, 23);
        contentPane.add(btnCadastrar);
        
        txtCnpj = new JTextField();
        txtCnpj.setBounds(96, 73, 252, 20);
        contentPane.add(txtCnpj);
        txtCnpj.setColumns(10);
        
        lblNewLabel_3 = new JLabel("CNPJ:");
        lblNewLabel_3.setBounds(10, 78, 46, 14);
        contentPane.add(lblNewLabel_3);
        
        txtRamo = new JTextField();
        txtRamo.setBounds(96, 134, 252, 20);
        contentPane.add(txtRamo);
        txtRamo.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Ramo:");
        lblNewLabel_4.setBounds(10, 137, 46, 14);
        contentPane.add(lblNewLabel_4);
        
        txtDescricao = new JTextArea();
        txtDescricao.setBounds(96, 165, 252, 111);
        contentPane.add(txtDescricao);
        
        JLabel lblNewLabel_5 = new JLabel("Descrição:");
        lblNewLabel_5.setBounds(10, 170, 76, 14);
        contentPane.add(lblNewLabel_5);
    }
    
    private void cadastrar() {
    	
    	JSONObject request = new JSONObject();

        String razaoSocial = txtRazaoSocial.getText();
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        String cnpj = txtCnpj.getText();
        String descricao = txtDescricao.getText();
        String ramo = txtRamo.getText();

        request.put("razaoSocial", razaoSocial);
        request.put("email", email);
        request.put("senha", senha);
        request.put("cnpj", cnpj);
        request.put("descricao", descricao);
        request.put("ramo", ramo);
        request.put("operacao", "cadastrarEmpresa");

        String response = cliente.callServer(request);
        JSONObject json = new JSONObject(response);
        System.out.println(json);

        if (json.getInt("status") == 201) {
            session.put("email", email);
            session.put("token", json.getString("token"));
            JOptionPane.showMessageDialog(CadastrarEmpresa.this, "Empresa cadastrada!", "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
            new EmpresaHome(cliente, session).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(CadastrarEmpresa.this, json.getString("mensagem"), "Erro Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}
