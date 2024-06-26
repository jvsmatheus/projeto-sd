package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Cliente.Cliente;

public class VagaHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelVagas;
	private Cliente cliente;
	private HashMap<String, String> session;

	public VagaHome(Cliente cliente, HashMap<String, String> session) {
		setTitle("Competências");
		this.cliente = cliente;
		this.session = session;
		initComponents();
		getVagas();
		setLocationRelativeTo(null);
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// Painel para competências com scroll
		panelVagas = new JPanel();
		panelVagas.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(panelVagas);
		scrollPane.setBounds(10, 10, 410, 250);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);

		// Botão de exemplo
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		btnCadastrar.setBounds(10, 280, 100, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});
		btnAtualizar.setBounds(120, 280, 100, 23);
		contentPane.add(btnAtualizar);
		
		JButton btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apagar();
			}
		});
		btnApagar.setBounds(230, 280, 89, 23);
		contentPane.add(btnApagar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CandidatoHome(cliente, session).setVisible(true);
				dispose();
			}
		});
		btnVoltar.setBounds(331, 280, 89, 23);
		contentPane.add(btnVoltar);
		
		
	}
	
	void getVagas() {
	    // Verifica se session não é null antes de acessar seus valores
	    if (session != null && session.containsKey("email") && session.containsKey("token")) {
	        JSONObject request = new JSONObject();
	        
	        request.put("operacao", "visualizarVaga");
	        request.put("email", session.get("email"));
	        request.put("token", session.get("token"));
	        
	        String response = cliente.callServer(request);
	        JSONObject json = new JSONObject(response);
	        
	        if (json.getInt("status") == 201) {
	            JSONArray vagasArray = json.getJSONArray("vagas");
	            
	            int yPosition = 10; // Posição inicial y
	            
	            for (int i = 0; i < vagasArray.length(); i++) {
	                JSONObject item = vagasArray.getJSONObject(i);
	                String nome = item.getString("nome");
	                double faixaSalarial = item.getDouble("faixaSalarial");
	                String descricao = item.getString("descricao");
	                String estado = item.getString("estado");
	                JSONArray competenciasArray = item.getJSONArray("competencias");
	                
	                JCheckBox chkVaga = new JCheckBox();
	                chkVaga.setBounds(10, yPosition, 25, 25);
	                panelVagas.add(chkVaga);

	                JLabel lblNome = new JLabel("Nome: " + nome);
	                lblNome.setBounds(40, yPosition, 200, 25);
	                panelVagas.add(lblNome);
	                
	                JLabel lblFaixaSalarial = new JLabel("Faixa Salarial: " + faixaSalarial);
	                lblFaixaSalarial.setBounds(40, yPosition + 20, 200, 25);
	                panelVagas.add(lblFaixaSalarial);
	                
	                JLabel lblDescricao = new JLabel("Descrição: " + descricao);
	                lblDescricao.setBounds(40, yPosition + 40, 200, 25);
	                panelVagas.add(lblDescricao);
	                
	                JLabel lblEstado = new JLabel("Estado: " + estado);
	                lblEstado.setBounds(40, yPosition + 60, 200, 25);
	                panelVagas.add(lblEstado);

	                JLabel lblCompetencias = new JLabel("Competências: " + competenciasArray.join(", "));
	                lblCompetencias.setBounds(40, yPosition + 80, 300, 25);
	                panelVagas.add(lblCompetencias);
	                
	                yPosition += 110; // Incrementa a posição y para o próximo conjunto de componentes
	            }
	            
	            // Ajusta o tamanho do painel de vagas para o scroll funcionar corretamente
	            panelVagas.setPreferredSize(new java.awt.Dimension(450, yPosition));
	        } else {
	            JOptionPane.showMessageDialog(VagaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(VagaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}


	private void cadastrar() {
		CadastrarVaga cadastro = new CadastrarVaga(cliente, session);
        cadastro.addWindowListener((WindowListener) new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                panelVagas.removeAll();
                getVagas();
                panelVagas.revalidate();
                panelVagas.repaint();
            }
        });
        cadastro.setVisible(true);
	}
	
	private void atualizar() {
	    // Verifica se session não é null antes de acessar seus valores
	    if (session != null && session.containsKey("email") && session.containsKey("token")) {
	        JSONObject request = new JSONObject();
	        
	        request.put("operacao", "atualizarCompetenciaExperiencia");
	        request.put("email", session.get("email"));
	        request.put("token", session.get("token"));
	        
	        JSONArray competenciaExperienciaArray = new JSONArray();

	        // Iterar sobre os componentes do painel de competências
	        for (int i = 0; i < panelVagas.getComponentCount(); i++) {
	            Component component = panelVagas.getComponent(i);
	            if (component instanceof JCheckBox) {
	                JCheckBox chkCompetencia = (JCheckBox) component;
	                if (chkCompetencia.isSelected()) {
	                    // Encontra o JLabel correspondente
	                    Component lblComponent = panelVagas.getComponent(i + 1);
	                    if (lblComponent instanceof JLabel) {
	                        JLabel lblCompetencia = (JLabel) lblComponent;
	                        String competencia = lblCompetencia.getText();

	                        // Encontra o JTextField correspondente
	                        Component txtComponent = panelVagas.getComponent(i + 2);
	                        if (txtComponent instanceof JTextField) {
	                            JTextField txtExperiencia = (JTextField) txtComponent;
	                            int experiencia = Integer.parseInt(txtExperiencia.getText());

	                            JSONObject competenciaExperiencia = new JSONObject();
	                            competenciaExperiencia.put("competencia", competencia);
	                            competenciaExperiencia.put("experiencia", experiencia);

	                            competenciaExperienciaArray.put(competenciaExperiencia);
	                        }
	                    }
	                }
	            }
	        }

	        request.put("competenciaExperiencia", competenciaExperienciaArray);

	        String response = cliente.callServer(request);
	        JSONObject json = new JSONObject(response);

	        if (json.getInt("status") == 201) {
	            panelVagas.removeAll();
	            getVagas();
	            JOptionPane.showMessageDialog(VagaHome.this, json.getString("mensagem"), "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
	        } else {
	            JOptionPane.showMessageDialog(VagaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(VagaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	private void apagar() {
		// Verifica se session não é null antes de acessar seus valores
	    if (session != null && session.containsKey("email") && session.containsKey("token")) {
	        JSONObject request = new JSONObject();
	        
	        request.put("operacao", "apagarCompetenciaExperiencia");
	        request.put("email", session.get("email"));
	        request.put("token", session.get("token"));
	        
	        JSONArray competenciaExperienciaArray = new JSONArray();

	        // Iterar sobre os componentes do painel de competências
	        for (int i = 0; i < panelVagas.getComponentCount(); i++) {
	            Component component = panelVagas.getComponent(i);
	            if (component instanceof JCheckBox) {
	                JCheckBox chkCompetencia = (JCheckBox) component;
	                if (chkCompetencia.isSelected()) {
	                    // Encontra o JLabel correspondente
	                    Component lblComponent = panelVagas.getComponent(i + 1);
	                    if (lblComponent instanceof JLabel) {
	                        JLabel lblCompetencia = (JLabel) lblComponent;
	                        String competencia = lblCompetencia.getText();

	                        // Encontra o JTextField correspondente
	                        Component txtComponent = panelVagas.getComponent(i + 2);
	                        if (txtComponent instanceof JTextField) {
	                            JTextField txtExperiencia = (JTextField) txtComponent;
	                            int experiencia = Integer.parseInt(txtExperiencia.getText());

	                            JSONObject competenciaExperiencia = new JSONObject();
	                            competenciaExperiencia.put("competencia", competencia);
	                            competenciaExperiencia.put("experiencia", experiencia);

	                            competenciaExperienciaArray.put(competenciaExperiencia);
	                        }
	                    }
	                }
	            }
	        }

	        request.put("competenciaExperiencia", competenciaExperienciaArray);

	        String response = cliente.callServer(request);
	        JSONObject json = new JSONObject(response);

	        if (json.getInt("status") == 201) {
	            panelVagas.removeAll();
	            getVagas();
	            JOptionPane.showMessageDialog(VagaHome.this, json.getString("mensagem"), "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
	        } else {
	            JOptionPane.showMessageDialog(VagaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(VagaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
