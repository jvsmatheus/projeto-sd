package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.json.JSONArray;
import org.json.JSONObject;
import Cliente.Cliente;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class CadastrarCompetenciaExperiencia extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtExperiencia;
    private Cliente cliente;
    private HashMap<String, String> session;
    
    JSONObject request = new JSONObject();
    JSONArray competenciExperiencia = new JSONArray();
    
    private JComboBox<String> cbxCompetencia;

    public CadastrarCompetenciaExperiencia(Cliente cliente, HashMap<String, String> session) {
        setTitle("Cadastrar Competências");
        this.cliente = cliente;
        this.session = session;
        initComponents();
        
        // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 293, 141);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        cbxCompetencia = new JComboBox<>();
        cbxCompetencia.setModel(new DefaultComboBoxModel<>(new String[] {"Python", "C#", "C++", "JS", "PHP", "Swift", "Java", "Go", "SQL", "Ruby", "HTML", "CSS", "NOSQL", "Flutter", "TypeScript", "Perl", "Cobol", "dotNet", "Kotlin", "Dart"}));
        cbxCompetencia.setBounds(138, 11, 129, 22);
        contentPane.add(cbxCompetencia);
        
        JLabel lblNewLabel = new JLabel("Lista de comptências:");
        lblNewLabel.setBounds(10, 15, 118, 14);
        contentPane.add(lblNewLabel);
        
        txtExperiencia = new JTextField();
        txtExperiencia.setBounds(138, 39, 129, 20);
        contentPane.add(txtExperiencia);
        txtExperiencia.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Experiência:");
        lblNewLabel_1.setBounds(10, 42, 118, 14);
        contentPane.add(lblNewLabel_1);
        
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarCompetencia();
            }
        });
        btnCadastrar.setBounds(148, 68, 119, 23);
        contentPane.add(btnCadastrar);
        
        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarCompentencia();
            }
        });
        btnAdicionar.setBounds(10, 68, 116, 23);
        contentPane.add(btnAdicionar);
    }
    
    private void adicionarCompentencia() {
        String competencia = (String) cbxCompetencia.getSelectedItem();
        int experiencia;
        try {
            experiencia = Integer.parseInt(txtExperiencia.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido para a experiência.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JSONObject competenciaExperiencia = new JSONObject();
        competenciaExperiencia.put("competencia", competencia);
        competenciaExperiencia.put("experiencia", experiencia);

        competenciExperiencia.put(competenciaExperiencia);

        // Limpar o campo de texto para a próxima entrada
        txtExperiencia.setText("");
        JOptionPane.showMessageDialog(this, "Competência adicionada com sucesso!");
    }
    
    private void cadastrarCompetencia() {
    	request.put("operacao", "cadastrarCompetenciaExperiencia");
    	request.put("email", session.get("email"));
    	request.put("token", session.get("token"));
    	request.put("competenciaExperiencia", competenciExperiencia);

        String response = cliente.callServer(request);
        JSONObject json = new JSONObject(response);
        System.out.println(json);

        if (json.getInt("status") == 201) {
            JOptionPane.showMessageDialog(CadastrarCompetenciaExperiencia.this, json.getString("mensagem"), "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
            new CompetenciaHome(cliente, session);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(CadastrarCompetenciaExperiencia.this, json.getString("mensagem"), "Erro Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cliente cliente = new Cliente("123.123.123-12");
                    HashMap<String, String> session = new HashMap<String, String>();
                    session.put("email", "testeEmail");
                    session.put("token", "testeToken");
                    CadastrarCompetenciaExperiencia frame = new CadastrarCompetenciaExperiencia(cliente, session);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
