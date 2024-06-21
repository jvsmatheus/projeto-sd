package Cliente;

import javax.swing.SwingUtilities;

import gui.HomeCliente;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new HomeCliente().setVisible(true));
		
	}

}
