package gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

public class CadastrarVaga extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtEmail;
    private Cliente cliente;
    private HashMap<String, String> session;

    private JTextField txtNome;
    private JTextField txtFaixaSalarial;
    private JTextField txtDescricao;
    private JPanel panelCompetencias;
    JComboBox<String> cbxDivDisp;
    private JSONObject request = new JSONObject();

    public CadastrarVaga(Cliente cliente, HashMap<String, String> session) {
        setTitle("Cadastrar Vaga");
        this.cliente = cliente;
        this.session = session;
        initComponents();

        // Centralizar a janela no monitor
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 350, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setBounds(10, 15, 118, 14);
        contentPane.add(lblNewLabel);

        txtNome = new JTextField();
        txtNome.setBounds(138, 12, 180, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(10, 42, 118, 14);
        contentPane.add(lblNewLabel_1);

        txtEmail = new JTextField();
        txtEmail.setBounds(138, 39, 180, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Faixa Salarial:");
        lblNewLabel_2.setBounds(10, 67, 118, 14);
        contentPane.add(lblNewLabel_2);

        txtFaixaSalarial = new JTextField();
        txtFaixaSalarial.setBounds(138, 64, 180, 20);
        contentPane.add(txtFaixaSalarial);
        txtFaixaSalarial.setColumns(10);

        JLabel lblNewLabel_2_1 = new JLabel("Descrição:");
        lblNewLabel_2_1.setBounds(10, 92, 118, 14);
        contentPane.add(lblNewLabel_2_1);

        txtDescricao = new JTextField();
        txtDescricao.setColumns(10);
        txtDescricao.setBounds(138, 89, 180, 20);
        contentPane.add(txtDescricao);

        JLabel lblNewLabel_2_2 = new JLabel("Estado:");
        lblNewLabel_2_2.setBounds(10, 117, 118, 14);
        contentPane.add(lblNewLabel_2_2);

        cbxDivDisp = new JComboBox<>();
        cbxDivDisp.setModel(new DefaultComboBoxModel<>(new String[] {"Divulgável", "Disponível"}));
        cbxDivDisp.setBounds(138, 113, 180, 22);
        contentPane.add(cbxDivDisp);

        JLabel lblNewLabel_2_3 = new JLabel("Competências:");
        lblNewLabel_2_3.setBounds(10, 142, 118, 14);
        contentPane.add(lblNewLabel_2_3);

        // Painel para competências com scroll
        panelCompetencias = new JPanel();
        panelCompetencias.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(panelCompetencias);
        scrollPane.setBounds(10, 167, 310, 300);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollPane);

        // Adicionar checkboxes estaticamente
        String[] competencias = {"Python", "C#", "C++", "JS", "PHP", "Swift", "Java", "Go", "SQL", "Ruby", "HTML", "CSS", "NOSQL", "Flutter", "TypeScript", "Perl", "Cobol", "dotNet", "Kotlin", "Dart"};
        
        int yPosition = 10;
        for (String competencia : competencias) {
            JCheckBox chkCompetencia = new JCheckBox(competencia);
            chkCompetencia.setBounds(10, yPosition, 200, 25);
            panelCompetencias.add(chkCompetencia);
            yPosition += 30;
        }

        // Ajusta o tamanho do painel de competências para o scroll funcionar corretamente
        panelCompetencias.setPreferredSize(new java.awt.Dimension(280, yPosition));

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarCompetencia();
            }
        });
        btnCadastrar.setBounds(115, 480, 100, 23);
        contentPane.add(btnCadastrar);
    }

    private void cadastrarCompetencia() {
        request.put("operacao", "cadastrarVaga");
        request.put("email", session.get("email"));
        request.put("token", session.get("token"));
        request.put("nome", txtNome.getText());
        request.put("faixaSalarial", Double.parseDouble(txtFaixaSalarial.getText()));
        request.put("descricao", txtDescricao.getText());
        request.put("estado", (String) cbxDivDisp.getSelectedItem());

        JSONArray competenciasSelecionadas = new JSONArray();

        for (Component component : panelCompetencias.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox chkCompetencia = (JCheckBox) component;
                if (chkCompetencia.isSelected()) {
                    competenciasSelecionadas.put(chkCompetencia.getText());
                }
            }
        }

        request.put("competencias", competenciasSelecionadas);
        

        String response = cliente.callServer(request);
        JSONObject json = new JSONObject(response);

        if (json.getInt("status") == 201) {
            JOptionPane.showMessageDialog(CadastrarVaga.this, json.getString("mensagem"), "Sucesso", JOptionPane.YES_NO_CANCEL_OPTION);
            new CompetenciaHome(cliente, session).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(CadastrarVaga.this, json.getString("mensagem"), "Erro", JOptionPane.ERROR_MESSAGE);
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
                    HashMap<String, String> session = new HashMap<>();
                    session.put("email", "testeEmail");
                    session.put("token", "testeToken");
                    CadastrarVaga frame = new CadastrarVaga(cliente, session);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
