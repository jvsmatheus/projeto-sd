package gui;

import java.util.HashMap;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import Cliente.Cliente;
import Model.User;
import Services.UserService;

public class CandidatoHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtSenha;

    private Cliente cliente;
    private HashMap<String, String> session;

    UserService service = new UserService();

    public CandidatoHome(Cliente cliente, HashMap<String, String> session) {
        setTitle("Candidato");
        this.cliente = cliente;
        this.session = session; // Inicializa a variável session recebida como parâmetro
        initComponents();
        setFields();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtNome = new JTextField();
        txtNome.setBounds(178, 11, 138, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setBounds(178, 42, 138, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        txtSenha = new JTextField();
        txtSenha.setBounds(178, 73, 138, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setBounds(122, 14, 46, 14);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(122, 45, 46, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Senha:");
        lblNewLabel_2.setBounds(122, 76, 46, 14);
        contentPane.add(lblNewLabel_2);
    }

    private void setFields() {
        // Verifica se session não é null antes de acessar seus valores
		if (session != null && session.containsKey("email") && session.containsKey("token")) {
			JSONObject request = new JSONObject();
			
			request.put("operacao", "visualizarCandidato");
			request.put("email", session.get("email"));
			request.put("token", session.get("token"));
			
			String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		    	this.txtNome.setText(json.getString("nome"));
			    this.txtEmail.setText(session.get("email"));
			    this.txtSenha.setText(json.getString("senha"));
		    } else {
		    	JOptionPane.showMessageDialog(CandidatoHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
			
		    
		    
		} else {
		    JOptionPane.showMessageDialog(CandidatoHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
}
