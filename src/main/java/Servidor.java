import Model.Empress;
import Model.ResponseEntities.MessageResponseEntity;
import Model.ResponseEntities.ResponseEntity;
import Model.ResponseEntities.TokenResponseEntity;
import Services.EmpressService;
import com.fasterxml.jackson.databind.JsonNode;
import Middlewares.JsonMiddleware;
import Services.UserService;
import Model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.UUID;

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
                        User user = new User();
                        user.setId(String.valueOf(UUID.randomUUID()));
                        user.setNome(node.get("nome").asText());
                        user.setEmail(node.get("email").asText());
                        user.setSenha(node.get("senha").asText());

                        try {
                            boolean success = userService.createUser(user);
                            if (success) {
                                User userGetId = userService.getUserByEmail(user.getEmail());

                                out.println(JsonMiddleware.objectToJson(new TokenResponseEntity(201, "cadastrarCandidato", userGetId.getId())));
                            }
                            break;
                        } catch (Exception e) {
                            MessageResponseEntity messageResponseEntity = new MessageResponseEntity(404, "cadastrarCandidato", "erro ao cadastrar candidato");

                            out.println(JsonMiddleware.objectToJson(messageResponseEntity));
                            break;
                        }
                    }
                    case "visualizarCandidato": {
                        User user = userService.getUserByEmail(node.get("email").asText());
                        if (Objects.isNull(user)) {
                            out.println(JsonMiddleware.objectToJson(new MessageResponseEntity(404, "vizualizarCandidato", "E-mail não encontrado")));
                        }

                        out.println(JsonMiddleware.objectToJson(new TokenResponseEntity(201, "vizualizarCandidato", user.getId())));
                        break;
                    }
                    case "atualizarCandidato": {
                        User user = new User();
                        user.setNome(node.get("nome").asText());
                        user.setEmail(node.get("email").asText());
                        user.setSenha(node.get("senha").asText());

                        boolean success = userService.updateUser(user.getEmail(), user);
                        if (success) {
                            out.println(JsonMiddleware.objectToJson(new ResponseEntity(201, "atualizarCandidato")));
                        } else {
                            out.println(JsonMiddleware.objectToJson(new MessageResponseEntity(404, "atualizarCandidato", "E-mail não encontrado")));
                        }
                        break;

                    }
                    case "apagarCandidato": {
                        boolean success = userService.deleteUser(node.get("email").asText());
                        if (success) {
                            out.println(JsonMiddleware.objectToJson(new ResponseEntity(201, "apagarCandidato")));
                        } else {
                            out.println(JsonMiddleware.objectToJson(new MessageResponseEntity(404, "apagarCandidato", "E-mail não encontrado")));
                        }
                        break;
                    }
                    case "loginCandidato": {
                        out.println(userService.userLogin(node.get("email").asText(), node.get("senha").asText()));
                        break;
                    }

                    case "cadastrarEmpresa": {
                        Empress empress = new Empress();
                        empress.setId(String.valueOf(UUID.randomUUID()));
                        empress.setRazaoSocial(node.get("razaoSocial").asText());
                        empress.setCnpj(node.get("cnpj").asText());
                        empress.setEmail(node.get("email").asText());
                        empress.setSenha(node.get("senha").asText());
                        empress.setDescricao(node.get("descricao").asText());
                        empress.setRamo(node.get("ramo").asText());

                        try {
                            boolean success = empressService.createEmpress(empress);
                            if (success) {
                                Empress empressGetId = empressService.getEmpressByEmail(empress.getEmail());

                                out.println(JsonMiddleware.objectToJson(new TokenResponseEntity(201, "cadastrarEmpresa", empressGetId.getId())));
                            }
                            break;
                        } catch (Exception e) {
                            MessageResponseEntity messageResponseEntity = new MessageResponseEntity(404, "cadastrarEmpresa", "erro ao cadastrar empresa");

                            out.println(JsonMiddleware.objectToJson(messageResponseEntity));
                            break;
                        }
                    }
                    case "visualizarEmpresa": {
                        Empress empress = empressService.getEmpressByEmail(node.get("email").asText());
                        if (Objects.isNull(empress)) {
                            out.println(JsonMiddleware.objectToJson(new MessageResponseEntity(404, "vizualizarEmpresa", "E-mail não encontrado")));
                        }

                        out.println(JsonMiddleware.objectToJson(new TokenResponseEntity(201, "vizualizarCandidato", empress.getId())));
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
                        boolean success = empressService.deleteEmpress(node.get("email").asText());
                        if (success) {
                            out.println(JsonMiddleware.objectToJson(new ResponseEntity(201, "apagarEmpresa")));
                        } else {
                            out.println(JsonMiddleware.objectToJson(new MessageResponseEntity(404, "apagarEmpresa", "E-mail não encontrado")));
                        }
                        break;
                    }
                    case "loginEmpresa": {
                        out.println(empressService.empressLogin(node.get("email").asText(), node.get("senha").asText()));
                        break;
                    }

                    case "logout": {
                        out.println(empressService.empressLogout(node.get("token").asText()));
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
