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
import Services.CandidatoService;

public class EmpresaHome extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtRazaoSocial;
    private JTextField txtEmail;
    private JTextField txtSenha;
    private JTextArea txtDescricao;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private Cliente cliente;
    private HashMap<String, String> session;
    private JTextField txtCnpj;
    private JLabel lblNewLabel_3;
    private JTextField txtRamo;

    CandidatoService service = new CandidatoService();
    private JTextField txtToken;
    private JLabel lblNewLabel_6;

    public EmpresaHome(Cliente cliente, HashMap<String, String> session) {
        setTitle("Empresa");
        this.cliente = cliente;
        this.session = session; // Inicializa a variável session recebida como parâmetro
        initComponents();
        setFields();
        
     // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 377, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtRazaoSocial = new JTextField();
        txtRazaoSocial.setBounds(96, 42, 252, 20);
        contentPane.add(txtRazaoSocial);
        txtRazaoSocial.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setEditable(false);
        txtEmail.setBounds(96, 73, 252, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        txtSenha = new JTextField();
        txtSenha.setBounds(96, 135, 252, 20);
        contentPane.add(txtSenha);
        txtSenha.setColumns(10);

        lblNewLabel = new JLabel("Razão Social:");
        lblNewLabel.setBounds(10, 45, 76, 14);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(10, 76, 46, 14);
        contentPane.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Senha:");
        lblNewLabel_2.setBounds(10, 138, 46, 14);
        contentPane.add(lblNewLabel_2);

        txtCnpj = new JTextField();
        txtCnpj.setBounds(96, 104, 252, 20);
        contentPane.add(txtCnpj);
        txtCnpj.setColumns(10);
        
        lblNewLabel_3 = new JLabel("CNPJ:");
        lblNewLabel_3.setBounds(10, 107, 46, 14);
        contentPane.add(lblNewLabel_3);
        
        txtRamo = new JTextField();
        txtRamo.setBounds(96, 166, 252, 20);
        contentPane.add(txtRamo);
        txtRamo.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Ramo:");
        lblNewLabel_4.setBounds(10, 169, 46, 14);
        contentPane.add(lblNewLabel_4);
        
        txtDescricao = new JTextArea();
        txtDescricao.setBounds(96, 197, 252, 111);
        contentPane.add(txtDescricao);
        
        JLabel lblNewLabel_5 = new JLabel("Descrição:");
        lblNewLabel_5.setBounds(10, 202, 76, 14);
        contentPane.add(lblNewLabel_5);
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		atualizarEmpresa();
        	}
        });
        btnAtualizar.setBounds(26, 353, 89, 23);
        contentPane.add(btnAtualizar);
        
        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		apagarCandidato();
        	}
        });
        btnDeletar.setBounds(125, 353, 109, 23);
        contentPane.add(btnDeletar);
        
        JButton btnCadastrarCompetencia = new JButton("Cadastrar Competências");
        btnCadastrarCompetencia.setBounds(82, 319, 192, 23);
        contentPane.add(btnCadastrarCompetencia);
        
        JButton btnLogout = new JButton("Sair");
        btnLogout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		logout();
        	}
        });
        btnLogout.setBounds(244, 353, 89, 23);
        contentPane.add(btnLogout);
        
        txtToken = new JTextField();
        txtToken.setEditable(false);
        txtToken.setBounds(96, 11, 252, 20);
        contentPane.add(txtToken);
        txtToken.setColumns(10);
        
        lblNewLabel_6 = new JLabel("Token:");
        lblNewLabel_6.setBounds(10, 14, 46, 14);
        contentPane.add(lblNewLabel_6);
    }

    private void setFields() {
		if (session != null && session.containsKey("email") && session.containsKey("token")) {
			
			JSONObject request = new JSONObject();
			
			request.put("operacao", "visualizarEmpresa");
			request.put("email", session.get("email"));
			request.put("token", session.get("token"));
			
			String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		    	this.txtToken.setText(session.get("token"));
		    	this.txtToken.setEditable(false);
		    	
		    	this.txtRazaoSocial.setText(json.getString("razaoSocial"));
		    	
			    this.txtEmail.setText(session.get("email"));
			    this.txtEmail.setEditable(false);
			    
			    this.txtSenha.setText(json.getString("senha"));
			    this.txtCnpj.setText(json.getString("cnpj"));
			    this.txtDescricao.setText(json.getString("descricao"));
			    this.txtRamo.setText(json.getString("ramo"));
			    
		    } else {
		    	JOptionPane.showMessageDialog(EmpresaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(EmpresaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void atualizarEmpresa() {
    	if (session != null && session.containsKey("email") && session.containsKey("token")) {
			JSONObject request = new JSONObject();
			
			request.put("operacao", "atualizarEmpresa");
			request.put("token", session.get("token"));
			request.put("razaoSocial", this.txtRazaoSocial.getText());
			request.put("email", session.get("email"));
			request.put("senha", this.txtSenha.getText());
			request.put("cnpj", this.txtCnpj.getText());
			request.put("descricao", this.txtDescricao.getText());
			request.put("ramo", this.txtRamo.getText());
			
			String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		    	setFields();
		    	JOptionPane.showMessageDialog(EmpresaHome.this, "Empresa atualizada!", "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
		    } else {
		    	JOptionPane.showMessageDialog(EmpresaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(EmpresaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void apagarCandidato() {
    	if (session != null && session.containsKey("email") && session.containsKey("token")) {
			JSONObject request = new JSONObject();
			
			request.put("operacao", "apagarEmpresa");
			request.put("token", session.get("token"));
			request.put("email", session.get("email"));
			
			String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		    	session.clear();
		    	JOptionPane.showMessageDialog(EmpresaHome.this, "Candidato deletado", "Erro", JOptionPane.YES_NO_CANCEL_OPTION);
		    	new Login(cliente).setVisible(true);
		    	dispose();
		    } else {
		    	JOptionPane.showMessageDialog(EmpresaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(EmpresaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(EmpresaHome.this, json.getString("mensagem"), "Erro Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
