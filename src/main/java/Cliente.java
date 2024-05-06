import Model.User;
import middlewares.JsonMiddleware;
import services.UserService;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static middlewares.JsonMiddleware.jsonToObject;

public class Cliente {

    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        String serverHostname = "10.40.14.179";
        System.out.println("Attempting to connect to host " + serverHostname + " on port 22222.");

        try (Socket socket = new Socket(serverHostname, 22222);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Register user");
                System.out.println("2. List users");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (Objects.equals(choice, 1)) {
                    System.out.println("Nome: ");
                    String name = scanner.nextLine();
                    System.out.println("Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Senha: ");
                    String password = scanner.nextLine();

                    Map<String, String> jsonFields = new HashMap<>();
                    jsonFields.put("nome", name);
                    jsonFields.put("email", email);
                    jsonFields.put("senha", password);
                    jsonFields.put("operacao", "cadastrarUsuario");

                    out.println(JsonMiddleware.stringToJson(jsonFields));

                } else if (Objects.equals(choice, "list")) {
                    out.println("list_users");
                } else if (Objects.equals(choice, "exit")) {
                    out.println("exit");
                    break;
                } else {
                    System.out.println("Invalid choice, please try again.");
                }

                String response = in.readLine();
                System.out.println("Server says: " + response);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHostname);
            System.exit(1);
        }
    }
}
