package gui;

import java.awt.Component;
import java.awt.EventQueue;
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
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class CompetenciaHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCompetencias;
	private Cliente cliente;
	private HashMap<String, String> session;

	public CompetenciaHome(Cliente cliente, HashMap<String, String> session) {
		setTitle("Competências");
		this.cliente = cliente;
		this.session = session;
		initComponents();
		getCompetencias();
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
		panelCompetencias = new JPanel();
		panelCompetencias.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(panelCompetencias);
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
	
	void getCompetencias() {
		// Verifica se session não é null antes de acessar seus valores
		if (session != null && session.containsKey("email") && session.containsKey("token")) {
		    JSONObject request = new JSONObject();
		    
		    request.put("operacao", "visualizarCompetenciaExperiencia");
		    request.put("email", session.get("email"));
		    request.put("token", session.get("token"));
		    
		    String response = cliente.callServer(request);
		    JSONObject json = new JSONObject(response);
		    
		    if (json.getInt("status") == 201) {
		        JSONArray competenciaExperiencia = json.getJSONArray("competenciaExperiencia");
		        
		        int yPosition = 10; // Posição inicial y
		        
		        for (int i = 0; i < competenciaExperiencia.length(); i++) {
		            JSONObject item = competenciaExperiencia.getJSONObject(i);
		            String competencia = item.getString("competencia");
		            int experiencia = item.getInt("experiencia");

		            JCheckBox chkCompetencia = new JCheckBox();
		            chkCompetencia.setBounds(10, yPosition, 25, 25);
		            panelCompetencias.add(chkCompetencia);

		            JLabel lblCompetencia = new JLabel(competencia);
		            lblCompetencia.setBounds(40, yPosition, 200, 25);
		            panelCompetencias.add(lblCompetencia);

		            JTextField txtExperiencia = new JTextField(String.valueOf(experiencia));
		            txtExperiencia.setBounds(250, yPosition, 150, 25);
		            panelCompetencias.add(txtExperiencia);
		            
		            yPosition += 35; // Incrementa a posição y para o próximo conjunto de componentes
		        }
		        
		        // Ajusta o tamanho do painel de competências para o scroll funcionar corretamente
		        panelCompetencias.setPreferredSize(new java.awt.Dimension(450, yPosition));
		    } else {
		        JOptionPane.showMessageDialog(CompetenciaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} else {
		    JOptionPane.showMessageDialog(CompetenciaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void cadastrar() {
		CadastrarCompetenciaExperiencia cadastro = new CadastrarCompetenciaExperiencia(cliente, session);
        cadastro.addWindowListener((WindowListener) new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                panelCompetencias.removeAll();
                getCompetencias();
                panelCompetencias.revalidate();
                panelCompetencias.repaint();
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
	        for (int i = 0; i < panelCompetencias.getComponentCount(); i++) {
	            Component component = panelCompetencias.getComponent(i);
	            if (component instanceof JCheckBox) {
	                JCheckBox chkCompetencia = (JCheckBox) component;
	                if (chkCompetencia.isSelected()) {
	                    // Encontra o JLabel correspondente
	                    Component lblComponent = panelCompetencias.getComponent(i + 1);
	                    if (lblComponent instanceof JLabel) {
	                        JLabel lblCompetencia = (JLabel) lblComponent;
	                        String competencia = lblCompetencia.getText();

	                        // Encontra o JTextField correspondente
	                        Component txtComponent = panelCompetencias.getComponent(i + 2);
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
	            panelCompetencias.removeAll();
	            getCompetencias();
	            JOptionPane.showMessageDialog(CompetenciaHome.this, json.getString("mensagem"), "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
	        } else {
	            JOptionPane.showMessageDialog(CompetenciaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(CompetenciaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
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
	        for (int i = 0; i < panelCompetencias.getComponentCount(); i++) {
	            Component component = panelCompetencias.getComponent(i);
	            if (component instanceof JCheckBox) {
	                JCheckBox chkCompetencia = (JCheckBox) component;
	                if (chkCompetencia.isSelected()) {
	                    // Encontra o JLabel correspondente
	                    Component lblComponent = panelCompetencias.getComponent(i + 1);
	                    if (lblComponent instanceof JLabel) {
	                        JLabel lblCompetencia = (JLabel) lblComponent;
	                        String competencia = lblCompetencia.getText();

	                        // Encontra o JTextField correspondente
	                        Component txtComponent = panelCompetencias.getComponent(i + 2);
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
	            panelCompetencias.removeAll();
	            getCompetencias();
	            JOptionPane.showMessageDialog(CompetenciaHome.this, json.getString("mensagem"), "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
	        } else {
	            JOptionPane.showMessageDialog(CompetenciaHome.this, json.get("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(CompetenciaHome.this, "Sessão inválida", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

}
