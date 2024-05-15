import Model.ResponseEntity;
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

                                ResponseEntity responseEntity = new ResponseEntity(201, "cadastrarUsuario", userGetId.getId().toString());

                                out.println(JsonMiddleware.objectToJson(responseEntity));
                            }
                            break;
                        } catch (Exception e) {
                            ResponseEntity responseEntity = new ResponseEntity(404, "cadastrarUsuario", "erro ao cadastrar candidato");

                            out.println(JsonMiddleware.objectToJson(responseEntity));
                            break;
                        }
                    }
                    case "visualizarCandidato": {
                        try {
                            User user = userService.getUserByEmail(node.get("email").asText());
                            if (Objects.isNull(user)) {
                                throw new IOException();
                            }
                            String userJson = JsonMiddleware.objectToJson(user);
                            ResponseEntity responseEntity = new ResponseEntity(201, "cadastrarUsuario", userJson);

                            out.println(JsonMiddleware.objectToJson(responseEntity));
                            break;
                        } catch (Exception e) {
                            out.println(new ResponseEntity(404, "cadastrarUsuario", "email não encontrado"));
                            break;
                        }
                    }
                    case "listarCandidato": {
                        String usersList = JsonMiddleware.objectListToJson(userService.findAllUsers());
                        out.println("Lista de usuários cadastrados: " + usersList);
                        break;
                    }

                    case "atualizarCandidato": {

                        User user = new User();
                        user.setId(node.get("id").asText());
                        user.setNome(node.get("nome").asText());
                        user.setEmail(node.get("email").asText());
                        user.setSenha(node.get("senha").asText());

                        System.out.println(user);

//                        try {
//                            boolean success = userService.updateUser(node.get("id").asLong(), user);
//                            if (success) {
//                                out.println("Candidato atualizado com sucesso");
//                            }
//                            break;
//                        } catch (Exception e) {
//                            out.println("Erro ao atualizar usuário usuário.");
//                            break;
//                        }
                    }
                    case "deletarCandidato": {
                        try {
                            boolean success = userService.deleteUser(node.get("email").asText());
                            if (success) {
                                out.println("Candidato atualizado com sucesso");
                            } else {
                                out.println("Usuário não cadastrado");
                            }
                            break;
                        } catch (Exception e) {
                            out.println("Erro ao deletar usuário");
                            break;
                        }
                    }
                    case "loginCandidato": {
                        out.println(userService.userLogin(node.get("email").asText(), node.get("senha").asText()));
                        break;
                    }
                    case "logoutCandidato": {
                        out.println(userService.userLogout(userService.userLogout(node.get("token").asText())));
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
