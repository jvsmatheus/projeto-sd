import Auth.JwtService;
import Middlewares.JsonMiddleware;
import Middlewares.Utils;
import Model.ResponseEntities.ResponseEntity;
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
        String serverHostname = "192.168.1.2";
        System.out.println("Attempting to connect to host " + serverHostname + " on port 22222.");

        UserService userService = new UserService();

        try (Socket socket = new Socket(serverHostname, 22222);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {

                System.out.println("Digite 1 para candidato e 2 para empresa: ");
                String type = scanner.nextLine();

                switch (type) {
                    case "1" : {
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
                                String password = JwtService.hashPassword(scanner.nextLine());

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("nome", name);
                                jsonFields.put("email", email);
                                jsonFields.put("senha", password);
                                jsonFields.put("operacao", "cadastrarCandidato");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                break;

//                                else {
//                                    System.out.println("Erro no cadastro:");
//                                    if (!Utils.isUsernameValid(name)) {
//                                        System.out.println(new ResponseEntity(404, "cadastrarUsuario", "nome inválido"));
//                                    }
//                                    if (!Utils.isEmailValid(email)) {
//                                        System.out.println(new ResponseEntity(404, "cadastrarUsuario", "email invalido"));
//                                        if (!Objects.isNull(userService.getUserByEmail(email))) {
//                                            System.out.println(new ResponseEntity(422, "cadastrarUsuario", "email já cadastrado"));
//                                        }
//                                    }
//                                    if (!Utils.isPasswordValid(password)) {
//                                        System.out.println(new ResponseEntity(404, "cadastrarUsuario", "senha inválida"));
//                                    }
//                                    break;
//                                }
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
                                System.out.println("Email já cadastrado: ");
                                String email = scanner.nextLine();
                                System.out.println("Nome a ser trocado: ");
                                String name = scanner.nextLine();
                                System.out.println("Senha a ser trocado: ");
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
                                jsonFields.put("operacao", "apagarCandidato");

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
                                String email = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("token", email);
                                jsonFields.put("operacao", "logout");

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
                    }
//                    case "2" : {
//                        System.out.println("\nMenu:");
//                        System.out.println("1. Cadastrar empresa");
//                        System.out.println("2. Vizualizar empresa");
//                        System.out.println("3. Atualizar candidato");
//                        System.out.println("4. Deletar candidato");
//                        System.out.println("5. login");
//                        System.out.println("6. logout");
//                        System.out.println("7. Encerrar aplicação");
//                        System.out.print("Digite a opção: ");
//                        int choice = scanner.nextInt();
//                        scanner.nextLine();
//
//                        switch (choice) {
//                            // Adaptar para empresa
//                            case 1: {
//                                System.out.println("Nome: ");
//                                String name = scanner.nextLine();
//                                System.out.println("Email: ");
//                                String email = scanner.nextLine();
//                                System.out.println("Senha: ");
//                                String password = scanner.nextLine();
//
//                                if (Utils.isUsernameValid(name) && Utils.isEmailValid(email) && Utils.isPasswordValid(password)) {
//                                    Map<String, String> jsonFields = new HashMap<>();
//                                    jsonFields.put("nome", name);
//                                    jsonFields.put("email", email);
//                                    jsonFields.put("senha", password);
//                                    jsonFields.put("operacao", "cadastrarEmpresa");
//
//                                    out.println(JsonMiddleware.mapToJson(jsonFields));
//                                    break;
//                                } else {
//                                    System.out.println("Erro no cadastro:");
//                                    if (!Utils.isUsernameValid(name)) {
//                                        System.out.println(new ResponseEntity(404, "cadastrarEmpresa", "nome inválido"));
//                                    }
//                                    if (!Utils.isEmailValid(email)) {
//                                        System.out.println(new ResponseEntity(404, "cadastrarEmpresa", "email invalido"));
//                                        if (!Objects.isNull(userService.getUserByEmail(email))) {
//                                            System.out.println(new ResponseEntity(422, "cadastrarEmpresa", "email já cadastrado"));
//                                        }
//                                    }
//                                    if (!Utils.isPasswordValid(password)) {
//                                        System.out.println(new ResponseEntity(404, "cadastrarEmpresa", "senha inválida"));
//                                    }
//                                }
//                            }
//                        }
//                    }
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
