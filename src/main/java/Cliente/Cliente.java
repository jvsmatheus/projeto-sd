package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class Cliente {

	private String ipServidor;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public Cliente(String ipServidor) {
        this.ipServidor = ipServidor;
    }

	public boolean connect() {
		try {
			this.socket = new Socket(this.ipServidor, 22222);
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			System.out.println("Conectado com Sucesso!" + "Ip do Servidor: "+ this.ipServidor);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String callServer(JSONObject request) {
		try {
			System.out.println("Enviado: "+request);
			out.println(request.toString());
			String response = this.in.readLine();
			System.out.println("Recebido do Sevidor:" + response);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro ao se comunicar com o servidor";
		}
	}
}
