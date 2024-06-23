package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Cliente.Cliente;

public class CadastrarCandidato extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JButton btnCadastrar;

    private Cliente cliente;
    private HashMap<String, String> session;

    public CadastrarCandidato(Cliente cliente) {
        setTitle("Cadastrar Candidato");
        this.cliente = cliente;
        this.session = new HashMap<>(); // Inicializa a variável session

        initComponents();
        
     // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 352, 230);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtNome = new JTextField();
        txtNome.setBounds(78, 11, 252, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setBounds(78, 42, 252, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        txtSenha = new JTextField();
        txtSenha.setBounds(78, 73, 252, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setBounds(10, 14, 46, 14);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(10, 45, 46, 14);
        contentPane.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Senha:");
        lblNewLabel_2.setBounds(10, 76, 46, 14);
        contentPane.add(lblNewLabel_2);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSONObject request = new JSONObject();

                String nome = txtNome.getText();
                String email = txtEmail.getText();
                String senha = txtSenha.getText();

                request.put("nome", nome);
                request.put("email", email);
                request.put("senha", senha);
                request.put("operacao", "cadastrarCandidato");

                String response = cliente.callServer(request);
                JSONObject json = new JSONObject(response);
                System.out.println(json);

                if (json.getInt("status") == 201) {
                    session.put("email", email);
                    session.put("token", json.getString("token"));
                    new CandidatoHome(cliente, session).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CadastrarCandidato.this, json.getString("mensagem"), "Erro Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCadastrar.setBounds(84, 143, 169, 23);
        contentPane.add(btnCadastrar);
    }

}
