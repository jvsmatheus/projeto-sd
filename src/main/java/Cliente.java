import Middlewares.JsonMiddleware;
import Middlewares.Utils;
import Model.ResponseEntity;
import Services.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        String serverHostname = "10.20.8.173";
        System.out.println("Attempting to connect to host " + serverHostname + " on port 22222.");

        UserService userService = new UserService();

        try (Socket socket = new Socket(serverHostname, 22222);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {

                System.out.println("\nMenu:");
                System.out.println("1. Cadastrar candidato");
                System.out.println("2. Achar candidato por email");
                System.out.println("3. Listar todos os candidatos");
                System.out.println("4. Atualizar candidato");
                System.out.println("5. Deletar candidato");
                System.out.println("6. login");
                System.out.println("7. logout");
                System.out.println("8. Encerrar aplicação");
                System.out.print("Digite a opção: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: {
                        System.out.println("Nome: ");
                        String name = scanner.nextLine();
                        System.out.println("Email: ");
                        String email = scanner.nextLine();
                        System.out.println("Senha: ");
                        String password = scanner.nextLine();

                        if (Utils.isUsernameValid(name) && Utils.isEmailValid(email) && Utils.isPasswordValid(password)) {
                            Map<String, String> jsonFields = new HashMap<>();
                            jsonFields.put("nome", name);
                            jsonFields.put("email", email);
                            jsonFields.put("senha", password);
                            jsonFields.put("operacao", "cadastrarCandidato");

                            out.println(JsonMiddleware.mapToJson(jsonFields));
                            break;
                        } else {
                            System.out.println("Erro no cadastro:");
                            if (!Utils.isUsernameValid(name)) {
                                System.out.println(new ResponseEntity(404, "cadastrarUsuario", "nome inválido"));
                            }
                            if (!Utils.isEmailValid(email)) {
                                System.out.println(new ResponseEntity(404, "cadastrarUsuario", "email invalido"));
                                if (!Objects.isNull(userService.getUserByEmail(email))) {
                                    System.out.println(new ResponseEntity(422, "cadastrarUsuario", "email já cadastrado"));
                                }
                            }
                            if (!Utils.isPasswordValid(password)) {
                                System.out.println(new ResponseEntity(404, "cadastrarUsuario", "senha inválida"));
                            }
                        }
                    }
                    case 2: {
                        System.out.println("Email: ");
                        String email = scanner.nextLine();

                        Map<String, String> jsonFields = new HashMap<>();
                        jsonFields.put("email", email);
                        jsonFields.put("operacao", "visualizarCandidato");

                        out.println(JsonMiddleware.mapToJson(jsonFields));
                        break;
                    }
                    case 3: {
                        Map<String, String> jsonFields = new HashMap<>();
                        jsonFields.put("operacao", "listarCandidato");

                        out.println(JsonMiddleware.mapToJson(jsonFields));
                        break;
                    }

                    case 4: {

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
                        jsonFields.put("operacao", "atualizarCandidato");

                        out.println(JsonMiddleware.mapToJson(jsonFields));
                        break;
                    }

                    case 5: {
                        System.out.println("Email do candidato a ser deletado: ");
                        String email = scanner.nextLine();

                        Map<String, String> jsonFields = new HashMap<>();
                        jsonFields.put("email", email);
                        jsonFields.put("operacao", "deletarCandidato");

                        out.println(JsonMiddleware.mapToJson(jsonFields));
                        break;
                    }

                    case 6: {
                        System.out.println("Email: ");
                        String email = scanner.nextLine();
                        System.out.println("Senha: ");
                        String password = scanner.nextLine();

                        Map<String, String> jsonFields = new HashMap<>();
                        jsonFields.put("email", email);
                        jsonFields.put("senha", password);
                        jsonFields.put("operacao", "loginCandidato");

                        out.println(JsonMiddleware.mapToJson(jsonFields));
                        break;
                    }

                    case 7: {
                        System.out.println("Token: ");
                        String token = scanner.nextLine();

                        Map<String, String> jsonFields = new HashMap<>();
                        jsonFields.put("token", token);
                        jsonFields.put("operacao", "logoutCandidato");

                        out.println(JsonMiddleware.mapToJson(jsonFields));
                        break;
                    }

                    case 8: {
                        System.out.println("Fechando conexão e desligando servidor");
                        return;
                    }
                    default: {
                        System.out.println("Opção inválida");
                        break;
                    }

                }
                String response = in.readLine();
                System.out.println(response);
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
