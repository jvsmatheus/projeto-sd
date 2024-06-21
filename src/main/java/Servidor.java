import Middlewares.JsonMiddleware;
import Model.CompetenciaExperiencia;
import Model.Empress;
import Model.ResponseEntities.MessageResponseEntity;
import Model.ResponseEntities.ResponseEntity;
import Model.User;
import Services.CompetenciaService;
import Services.EmpressService;
import Services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int port = 22222;

        UserService userService = new UserService();
        EmpressService empressService = new EmpressService();

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

                JsonNode node = JsonMiddleware.stringToJsonNode(input);

                switch (node.get("operacao").asText()) {
                    case "cadastrarCandidato": {
                        out.println(userService.createUser(node));
                        break;
                    }
                    case "visualizarCandidato": {
                        out.println(userService.vizualizarCandidato(node.get("email").asText()));
                        break;
                    }
                    case "atualizarCandidato": {
                        out.println(userService.updateUser(node));
                        break;
                    }
                    case "apagarCandidato": {
                        out.println(userService.deleteUser(node));
                        break;
                    }
                    case "cadastrarCompetenciaExperiencia": {
                        String email = node.get("email").asText();
                        String token = node.get("token").asText();

                        System.out.println(node);

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
                    }
                    case "loginCandidato": {
                        out.println(userService.userLogin(node.get("email").asText(), node.get("senha").asText()));
                        break;
                    }

                    case "cadastrarEmpresa": {
                        out.println(empressService.createEmpress(node));
                        break;
                    }
                    case "visualizarEmpresa": {
                        out.println(empressService.getEmpressByEmail(node));
                        break;
                    }
                    case "atualizarEmpresa": {
                        Empress empress = new Empress();
                        empress.setRazaoSocial(node.get("razaoSocial").asText());
                        empress.setEmail(node.get("email").asText());
                        empress.setCnpj(node.get("cnpj").asText());
                        empress.setSenha(node.get("senha").asText());
                        empress.setDescricao(node.get("descricao").asText());
                        empress.setRamo(node.get("ramo").asText());

                        boolean success = empressService.updateEmpress(empress.getEmail(), empress);
                        if (success) {
                            out.println(JsonMiddleware.objectToJson(new ResponseEntity(201, "atualizarEmpresa")));
                        } else {
                            out.println(JsonMiddleware.objectToJson(new MessageResponseEntity(404, "atualizarEmpresa", "E-mail não encontrado")));
                        }
                        break;

                    }
                    case "apagarEmpresa": {
                        out.println(empressService.deleteEmpress(node));
                        break;
                    }
                    case "loginEmpresa": {
                        out.println(empressService.empressLogin(node.get("email").asText(), node.get("senha").asText()));
                        break;
                    }

                    case "logout": {
                        out.println(userService.logout(node.get("token").asText()));
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
