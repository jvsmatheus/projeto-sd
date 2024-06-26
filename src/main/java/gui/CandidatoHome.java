package gui;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Cliente.Cliente;
import Services.CandidatoService;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CandidatoHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtSenha;

    private Cliente cliente;
    private HashMap<String, String> session;

    CandidatoService service = new CandidatoService();
    private JTextField txtToken;

    public CandidatoHome(Cliente cliente, HashMap<String, String> session) {
        setTitle("Candidato");
        this.cliente = cliente;
        this.session = session; // Inicializa a variável session recebida como parâmetro
        initComponents();
        setFields();
        
     // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtNome = new JTextField();
        txtNome.setBounds(104, 49, 261, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setBounds(104, 80, 261, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        txtSenha = new JTextField();
        txtSenha.setBounds(104, 111, 261, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setBounds(54, 52, 46, 14);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(54, 83, 46, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Senha:");
        lblNewLabel_2.setBounds(54, 114, 46, 14);
        contentPane.add(lblNewLabel_2);
        
        txtToken = new JTextField();
        txtToken.setBounds(104, 18, 261, 20);
        contentPane.add(txtToken);
        txtToken.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Token:");
        lblNewLabel_3.setBounds(54, 21, 46, 14);
        contentPane.add(lblNewLabel_3);
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		atualizarCandidato();
        	}
        });
        btnAtualizar.setBounds(54, 192, 89, 23);
        contentPane.add(btnAtualizar);
        
        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		apagarCandidato();
        	}
        });
        btnDeletar.setBounds(157, 192, 109, 23);
        contentPane.add(btnDeletar);
        
        JButton btnCadastrarCompetencia = new JButton("Competências");
        btnCadastrarCompetencia.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		competencias();
        	}
        });
        btnCadastrarCompetencia.setBounds(117, 158, 192, 23);
        contentPane.add(btnCadastrarCompetencia);
        
        JButton btnLogout = new JButton("Sair");
        btnLogout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		logout();
        	}
        });
        btnLogout.setBounds(276, 192, 89, 23);
        contentPane.add(btnLogout);
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
		    	this.txtToken.setText(session.get("token"));
		    	this.txtToken.setEditable(false);
		    	
		    	this.txtNome.setText(json.getString("nome"));
		    	
			    this.txtEmail.setText(session.get("email"));
			    this.txtEmail.setEditable(false);
			    
			    this.txtSenha.setText(json.getString("senha"));
		    } else {
		    	JOptionPane.showMessageDialog(CandidatoHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(CandidatoHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void competencias() {
    	new CompetenciaHome(cliente, session).setVisible(true);
    	dispose();
    }
    
    private void atualizarCandidato() {
    	if (session != null && session.containsKey("email") && session.containsKey("token")) {
			JSONObject request = new JSONObject();
			
			request.put("operacao", "atualizarCandidato");
			request.put("token", session.get("token"));
			request.put("email", session.get("email"));
			request.put("nome", this.txtNome.getText());
			request.put("senha", this.txtSenha.getText());
			
			String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		    	setFields();
		    } else {
		    	JOptionPane.showMessageDialog(CandidatoHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(CandidatoHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void apagarCandidato() {
    	if (session != null && session.containsKey("email") && session.containsKey("token")) {
			JSONObject request = new JSONObject();
			
			request.put("operacao", "apagarCandidato");
			request.put("token", session.get("token"));
			request.put("email", session.get("email"));
			
			String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		    	session.clear();
		    	new Login(cliente).setVisible(true);;
		    	dispose();
		    } else {
		    	JOptionPane.showMessageDialog(CandidatoHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(CandidatoHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void logout() {
    	
        JSONObject request = new JSONObject();

        request.put("operacao", "logout");
        request.put("token", txtToken.getText());
        
        String response = cliente.callServer(request);
        JSONObject json = new JSONObject(response);
        
        if (json.getInt("status") == 204) {
        	session.clear();
            new Login(cliente).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(CandidatoHome.this, json.getString("mensagem"), "Erro Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
