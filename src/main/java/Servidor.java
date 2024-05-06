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
                    System.out.println(input);

                    if (input == null) {
                        out.println("Received null input");
                        continue; // Skip to the next loop iteration if input is null
                    }

                    switch (input) {
                        case "cadastrarUsuario":
                            String userJson = in.readLine();
                            if (userJson == null) {
                                out.println("No user data received");
                                break;
                            }
                            User user = JsonMiddleware.jsonToObject(userJson, User.class);
                            boolean success = userService.createUser(user);
                            out.println(success ? "Registration successful" : "Registration failed");
                            break;
                        case "list_users":
                            String usersList = JsonMiddleware.objectToJson(userService.findAllUsers());
                            out.println(usersList);
                            break;
                        case "exit":
                            out.println("Exiting...");
                            return; // Server stops after exit command
                        default:
                            out.println("Unknown command");
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
