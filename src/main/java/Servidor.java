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

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String input = in.readLine();

                    if (input == null) {
                        out.println("Received null input");
                        continue; // Skip to the next loop iteration if input is null
                    }

                    JsonNode node = JsonMiddleware.stringToJsonNode(input);

                    switch (node.get("operacao").asText()) {
                        case "cadastrarUsuario":
                            User user = new User();
                            user.setNome(node.get("nome").asText());
                            user.setEmail(node.get("email").asText());
                            user.setSenha(node.get("senha").asText());

                            try {
                                userService.createUser(user);
                            } catch (Exception e) {
                                out.println("Erro ao cadastrar usuário.");
                                break;
                            }

                        case "list_users":
                            String usersList = JsonMiddleware.objectToJson(userService.findAllUsers());
                            out.println(usersList);
                            break;
                        case "exit":
                            out.println("Exiting...");
                            return; // Server stops after exit command
                        default:
                            out.println("Comando inválido");
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port " +
                            port + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
