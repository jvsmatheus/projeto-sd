import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

import org.json.JSONObject;

//import Model.CompetenciaExperiencia;
//import Model.Empresa;
//import Model.ResponseEntities.MessageResponseEntity;
//import Model.ResponseEntities.ResponseEntity;
//import Model.Candidato;
//import Services.CompetenciaService;
//import Services.EmpressService;
import Services.CandidatoService;
import Services.EmpresaService;
import Services.LoginService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int port = 22222;

        CandidatoService userService = new CandidatoService();
        EmpresaService empresaService = new EmpresaService();
        LoginService loginService = new LoginService();
        
        HashMap<String, String> session = new HashMap<String, String>();

        System.out.println("Server is listening on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Usuário conectado");

            while (true) {

                String input = in.readLine();
                System.out.println(input);

                if (input == null) {
                    out.println("Input nulo"); // Skip to the next loop iteration if input is null
                    break;
                }

                JSONObject node = new JSONObject(input);

                switch (node.getString("operacao")) {
                    case "cadastrarCandidato": {
                    	var response = userService.cadastrarCandidato(node);
                    	session.put("token", response.getString("token"));
                    	session.put("email", node.getString("email"));
                        out.println(response);
                        break;
                    }
                    case "visualizarCandidato": {
                        out.println(userService.vizualizarCandidato(node));
                        break;
                    }
                    case "atualizarCandidato": {
                        out.println(userService.atualizarCandidato(node));
                        break;
                    }
                    case "apagarCandidato": {
                        out.println(userService.apagarCandidato(node));
                        break;
                    }
//                    case "cadastrarCompetenciaExperiencia": {
//                        String email = node.get("email").asText();
//                        String token = node.get("token").asText();
//
//                        System.out.println(node);

//                        ArrayNode competenciasArray = (ArrayNode) node.get("competenciaExperiencia");
//                        for (JsonNode competenciaNode : competenciasArray) {
//                            String competencia = competenciaNode.get("competencia").asText();
//                            int experiencia = competenciaNode.get("experiencia").asInt();
//
//                            if (CompetenciaService.isCompetenciaValida(competencia)) {
//                                CompetenciaExperiencia competenciaExperiencia = new CompetenciaExperiencia();
//                                competenciaExperiencia.setCompetencia(competencia);
//                                competenciaExperiencia.setExperiencia(experiencia);
//                                competenciaExperiencia.setCandidato(user);
//
//                                session.save(competenciaExperiencia);session.save(competenciaExperiencia);
//                            } else {
//                                responseNode.put("status", 400);
//                                responseNode.put("mensagem", "Competência inválida: " + competencia);
//                                responseWriter.println(responseNode.toString());
//                                return;
//                            }
//                        }
//                    }
                    case "cadastrarEmpresa": {
                        out.println(empresaService.cadastarEmpresa(node));
                        break;
                    }
                    case "visualizarEmpresa": {
                        out.println(empresaService.getEmpresaByEmail(node));
                        break;
                    }
                    case "atualizarEmpresa": {
                        out.println(empresaService.atualizarEmpresa(node));
                        break;

                    }
                    case "apagarEmpresa": {
                        out.println(empresaService.apagarEmpresa(node));
                        break;
                    }
                    case "loginCandidato":
                    case "loginEmpresa": 
                    {
                    	var response = loginService.login(node);
                    	if (response.has("token")) {
                    		session.put("token", response.getString("token"));
                        	session.put("email", node.getString("email"));
                    	}
                        out.println(response);
                        break;
                    }
                    case "logout": {
                        out.println(loginService.logout(node, session));
                        session.clear();
                        break;
                    }
                    case "exit":
                        out.println("Exiting...");
                        return; // Server stops after exit command
                    default:
                        out.println("Comando inválido");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " +
                    port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
