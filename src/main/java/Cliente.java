import Model.User;
import services.UserService;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

import static middlewares.JsonMiddleware.jsonToObject;

public class Cliente {

    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        String serverHostname = "192.168.1.14";
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
                String choice = scanner.nextLine();

                if (Objects.equals(choice, "register")) {
                    System.out.print("Enter json: ");
                    String userDataJson = in.readLine();
                    User user = jsonToObject(userDataJson, User.class); // Deserialize JSON into User object
//                    userService.createUser(user);
//                    out.println("register");
                    out.println(userDataJson);
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
