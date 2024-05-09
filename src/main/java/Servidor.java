import com.fasterxml.jackson.databind.JsonNode;
import middlewares.JsonMiddleware;
import services.UserService;
import Model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

            while (in.readLine() != null) {

                    String input = in.readLine();
                    System.out.println(input);

                    if (input == null) {
                        System.out.println("Received null input");
                        break; // Skip to the next loop iteration if input is null
                    }

                    JsonNode node = JsonMiddleware.stringToJsonNode(input);

                    switch (node.get("operacao").asText()) {
                        case "cadastrarCandidato": {
                            User user = new User();
                            user.setNome(node.get("nome").asText());
                            user.setEmail(node.get("email").asText());
                            user.setSenha(node.get("senha").asText());

                            try {
                                boolean success = userService.createUser(user);
                                if (success) {
                                    out.println("Cadastro realizado com sucesso");
                                }
                                break;
                            } catch (Exception e) {
                                out.println("Erro ao cadastrar usuário.");
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
                            user.setId(node.get("id").asLong());
                            user.setNome(node.get("nome").asText());
                            user.setEmail(node.get("email").asText());
                            user.setSenha(node.get("senha").asText());

                            try {
                                boolean success = userService.updateUser(node.get("id").asLong(), user);
                                if (success) {
                                    out.println("Candidato atualizado com sucesso");
                                }
                                break;
                            } catch (Exception e) {
                                out.println("Erro ao atualizar usuário usuário.");
                                break;
                            }
                        }
                        case "deletarCandidato": {
                            try {
                                boolean success = userService.deleteUser(node.get("id").asLong());
                                System.out.println(success);
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
