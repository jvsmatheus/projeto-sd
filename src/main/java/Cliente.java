import Middlewares.JsonMiddleware;
import Services.CompetenciaService;
import Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                        System.out.println("3. Atualizar candidato");
                        System.out.println("4. Deletar candidato");
                        System.out.println("5. Cadastrar Competência/Experiência");
                        System.out.println("6. Vizualizar Competencias/Experiencia");
                        System.out.println("7. Atualizar Competencias/Experiencia");
                        System.out.println("8. Apagar Competencias/Experiencia");
                        System.out.println("9. Filtrar Vagas");
                        System.out.println("10. login");
                        System.out.println("11. logout");
                        System.out.println("12. Encerrar aplicação");
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

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("nome", name);
                                jsonFields.put("email", email);
                                jsonFields.put("senha", password);
                                jsonFields.put("operacao", "cadastrarCandidato");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;

//                                else {
//                                    System.out.println("Erro no cadastro:");
//                                    if (!Utils.isUsernameValid(name)) {
//                                        System.out.println(new MessageResponseEntity(404, "cadastrarUsuario", "nome inválido"));
//                                    }
//                                    if (!Utils.isEmailValid(email)) {
//                                        System.out.println(new MessageResponseEntity(404, "cadastrarUsuario", "email invalido"));
//                                        if (!Objects.isNull(userService.getUserByEmail(email))) {
//                                            System.out.println(new MessageResponseEntity(422, "cadastrarUsuario", "email já cadastrado"));
//                                        }
//                                    }
//                                    if (!Utils.isPasswordValid(password)) {
//                                        System.out.println(new MessageResponseEntity(404, "cadastrarUsuario", "senha inválida"));
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
                                System.out.println(in.readLine());
                                break;
                            }
                            case 3: {
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
                                System.out.println(in.readLine());
                                break;
                            }
                            case 4: {
                                System.out.println("Email do candidato a ser deletado: ");
                                String email = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("operacao", "apagarCandidato");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 5: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "cadastrarCompetenciaExperiencia");

                                ObjectMapper mapper = new ObjectMapper();
                                ArrayNode competenciasExperiencias = mapper.createArrayNode();

                                List<String> competenciasFixas = CompetenciaService.getCompetenciasFixas();
                                System.out.println("Escolha uma competência entre as opções: " + competenciasFixas);

                                while (true) {
                                    System.out.println("Digite a competência (ou 'sair' para finalizar):");
                                    String competencia = scanner.nextLine();
                                    if (competencia.equalsIgnoreCase("sair")) {
                                        break;
                                    }

                                    if (!competenciasFixas.contains(competencia)) {
                                        System.out.println("Competência inválida. Tente novamente.");
                                        continue;
                                    }

                                    System.out.println("Digite os anos de experiência:");
                                    int experiencia = scanner.nextInt();
                                    scanner.nextLine();

                                    ObjectNode competenciaExperiencia = mapper.createObjectNode();
                                    competenciaExperiencia.put("competencia", competencia);
                                    competenciaExperiencia.put("experiencia", experiencia);

                                    competenciasExperiencias.add(competenciaExperiencia);
                                }
                                jsonFields.put("competenciaExperiencia", String.valueOf(competenciasExperiencias));

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 6: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "visualizarCompetenciaExperiencia");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 7: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "atualizarCompetenciaExperiencia");

                                ObjectMapper mapper = new ObjectMapper();
                                ArrayNode competenciasExperiencias = mapper.createArrayNode();

                                List<String> competenciasFixas = CompetenciaService.getCompetenciasFixas();
                                System.out.println("Escolha uma competência entre as opções: " + competenciasFixas);

                                boolean continueAdding = true;
                                while (continueAdding) {
                                    System.out.println("Digite a competência:");
                                    String competenciaAtual = scanner.nextLine();

                                    if (!competenciasFixas.contains(competenciaAtual)) {
                                        System.out.println("Competência inválida. Tente novamente:");
                                        continue;
                                    }

                                    System.out.println("Digite os anos de experiência a serem atualizados:");
                                    int experiencia;
                                    try {
                                        experiencia = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch (NumberFormatException e) {
                                        System.out.println("Entrada inválida para experiência. Deve ser um número. Tente novamente.");
                                        continue;
                                    }

                                    ObjectNode competenciaExperiencia = mapper.createObjectNode();
                                    competenciaExperiencia.put("competencia", competenciaAtual);
                                    competenciaExperiencia.put("experiencia", experiencia);
                                    competenciasExperiencias.add(competenciaExperiencia);

                                    System.out.println("Deseja adicionar ou atualizar outra competência? (sim/não)");
                                    String resposta = scanner.nextLine();
                                    if (!resposta.equalsIgnoreCase("sim")) {
                                        continueAdding = false;
                                    }
                                }

                                jsonFields.put("competenciaExperiencia", String.valueOf(competenciasExperiencias));
                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 8: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "apagarCompetenciaExperiencia");

                                ObjectMapper mapper = new ObjectMapper();
                                ArrayNode competenciasExperiencias = mapper.createArrayNode();

                                boolean continueAdding = true;
                                while (continueAdding) {
                                    System.out.println("Digite a competência a ser deletada:");
                                    String competencia = scanner.nextLine();

                                    System.out.println("Digite os anos de experiência para a competência " + competencia + ":");
                                    int experiencia;
                                    try {
                                        experiencia = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch (NumberFormatException e) {
                                        System.out.println("Entrada inválida para experiência. Deve ser um número. Tente novamente.");
                                        continue;
                                    }

                                    ObjectNode competenciaExperiencia = mapper.createObjectNode();
                                    competenciaExperiencia.put("competencia", competencia.trim());
                                    competenciaExperiencia.put("experiencia", experiencia);

                                    competenciasExperiencias.add(competenciaExperiencia);

                                    System.out.println("Deseja adicionar outra competência para deletar? (sim/não)");
                                    String resposta = scanner.nextLine();
                                    if (!resposta.equalsIgnoreCase("sim")) {
                                        continueAdding = false;
                                    }
                                }

                                jsonFields.put("competenciaExperiencia", String.valueOf(competenciasExperiencias));
                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 9: {
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "filtrarVagas");

                                ObjectMapper mapper = new ObjectMapper();
                                ObjectNode filtrosNode = mapper.createObjectNode();
                                ArrayNode competenciasNode = filtrosNode.putArray("competencias");

                                System.out.println("Digite as competências para filtrar (separadas por vírgula):");
                                String competenciasInput = scanner.nextLine();
                                String[] competenciasArray = competenciasInput.split(",");
                                for (String competencia : competenciasArray) {
                                    competenciasNode.add(competencia.trim());
                                }

                                System.out.println("Digite o tipo de filtro (AND/OR):");
                                String tipo = scanner.nextLine();
                                filtrosNode.put("tipo", tipo);

                                jsonFields.put("filtros", String.valueOf(filtrosNode));
                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 10: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Senha: ");
                                String password = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("senha", password);
                                jsonFields.put("operacao", "loginCandidato");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 11: {
                                System.out.println("Token: ");
                                String email = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("token", email);
                                jsonFields.put("operacao", "logout");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 12: {
                                System.out.println("Fechando conexão e desligando servidor");
                                return;
                            }
                            default: {
                                System.out.println("Opção inválida");
                                break;
                            }
                        }
                        break;
                    }
                    case "2" : {
                        System.out.println("\nMenu:");
                        System.out.println("1. Cadastrar empresa");
                        System.out.println("2. Vizualizar empresa");
                        System.out.println("3. Atualizar candidato");
                        System.out.println("4. Deletar candidato");
                        System.out.println("5. cadastrar vaga");
                        System.out.println("6. visualizar Vaga");
                        System.out.println("7. Atualizar Vaga");
                        System.out.println("8. Apagar Vaga");
                        System.out.println("9. Listar Vaga");
                        System.out.println("10. Filtrar Vaga");
                        System.out.println("11. login");
                        System.out.println("12. logout");
                        System.out.println("13. Encerrar aplicação");
                        System.out.print("Digite a opção: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            // Adaptar para empresa
                            case 1: {
                                System.out.println("Razão Social: ");
                                String razaoSocial = scanner.nextLine();
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Cnpj: ");
                                String cnpj = scanner.nextLine();
                                System.out.println("Senha: ");
                                String password = scanner.nextLine();
                                System.out.println("Descrição: ");
                                String descricao = scanner.nextLine();
                                System.out.println("Ramo: ");
                                String ramo = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("razaoSocial", razaoSocial);
                                jsonFields.put("email", email);
                                jsonFields.put("cnpj", cnpj);
                                jsonFields.put("senha", password);
                                jsonFields.put("descricao", descricao);
                                jsonFields.put("ramo", ramo);
                                jsonFields.put("operacao", "cadastrarEmpresa");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 2: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("operacao", "visualizarEmpresa");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 3: {
                                System.out.println("Razão Social: ");
                                String razaoSocial = scanner.nextLine();
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Senha: ");
                                String password = scanner.nextLine();
                                System.out.println("cnpj: ");
                                String cnpj = scanner.nextLine();
                                System.out.println("Descriçaõ: ");
                                String descricao = scanner.nextLine();
                                System.out.println("Ramo: ");
                                String ramo = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("razaoSocial", razaoSocial);
                                jsonFields.put("email", email);
                                jsonFields.put("senha", password);
                                jsonFields.put("cnpj", cnpj);
                                jsonFields.put("descricao", descricao);
                                jsonFields.put("ramo", ramo);
                                jsonFields.put("operacao", "atualizarEmpresa");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 4: {
                                System.out.println("Email do candidato a ser deletado: ");
                                String email = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("operacao", "apagarEmpresa");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 5: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "cadastrarVaga");

                                System.out.println("Digite o nome da vaga:");
                                jsonFields.put("nome", scanner.nextLine());

                                System.out.println("Digite a faixa salarial:");
                                jsonFields.put("faixaSalarial", scanner.nextLine());

                                System.out.println("Digite a descrição da vaga:");
                                jsonFields.put("descricao", scanner.nextLine());

                                System.out.println("Digite o estado da vaga (Disponível/Divulgavel):");
                                jsonFields.put("estado", scanner.nextLine());

                                ObjectMapper mapper = new ObjectMapper();
                                ArrayNode competenciasArray = mapper.createArrayNode();

                                System.out.println("Digite as competências da vaga (separadas por vírgula):");
                                String[] competencias = scanner.nextLine().split(",");
                                for (String competencia : competencias) {
                                    competenciasArray.add(competencia.trim());
                                }

                                jsonFields.put("competencias", String.valueOf(competenciasArray));
                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 6: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "visualizarVaga");

                                System.out.println("Digite o ID da vaga:");
                                String idVaga = scanner.nextLine();
                                jsonFields.put("idVaga", idVaga);

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 7: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                ObjectMapper mapper = new ObjectMapper();
                                ObjectNode json = mapper.createObjectNode();
                                json.put("email", email);
                                json.put("token", token);
                                json.put("operacao", "visualizarVaga");


                                System.out.println("Digite o ID da vaga:");
                                int idVaga = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Digite o novo nome da vaga:");
                                String novoNome = scanner.nextLine();

                                System.out.println("Digite a nova faixa salarial da vaga:");
                                double novaFaixaSalarial = scanner.nextDouble();
                                scanner.nextLine();

                                System.out.println("Digite a nova descrição da vaga:");
                                String novaDescricao = scanner.nextLine();

                                System.out.println("Digite o novo estado da vaga: Disponível / Divulgavel");
                                String novoEstado = scanner.nextLine();

                                System.out.println("Digite as novas competências (separadas por vírgula):");
                                String competenciasInput = scanner.nextLine();
                                String[] competenciasArray = competenciasInput.split(",");


                                json.put("operacao", "atualizarVaga");
                                json.put("idVaga", String.valueOf(idVaga));
                                json.put("nome", novoNome);
                                json.put("faixaSalarial", String.valueOf(novaFaixaSalarial)); // Corrigido para usar double
                                json.put("descricao", novaDescricao);
                                json.put("estado", novoEstado);

                                ArrayNode competenciasJsonArray = json.putArray("competencias");
                                for (String competencia : competenciasArray) {
                                    competenciasJsonArray.add(competencia.trim());
                                }

                                out.println(json.toString());
                                break;
                            }
                            case 8: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "apagarVaga");

                                System.out.println("Digite o ID da vaga que deseja deletar:");
                                String idVaga = scanner.nextLine();

                                jsonFields.put("idVaga", idVaga);

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 9: {
                                System.out.println("Email: ");
                                String email = scanner.nextLine();
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("email", email);
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "listarVagas");

                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
                            case 10: {
                                System.out.println("Token: ");
                                String token = scanner.nextLine();

                                Map<String, String> jsonFields = new HashMap<>();
                                jsonFields.put("token", token);
                                jsonFields.put("operacao", "filtrarVagas");

                                ObjectMapper mapper = new ObjectMapper();
                                ObjectNode filtrosNode = mapper.createObjectNode();
                                ArrayNode competenciasNode = filtrosNode.putArray("competencias");

                                System.out.println("Digite as competências para filtrar (separadas por vírgula):");
                                String competenciasInput = scanner.nextLine();
                                String[] competenciasArray = competenciasInput.split(",");
                                for (String competencia : competenciasArray) {
                                    competenciasNode.add(competencia.trim());
                                }

                                System.out.println("Digite o tipo de filtro (AND/OR):");
                                String tipo = scanner.nextLine();
                                filtrosNode.put("tipo", tipo);

                                jsonFields.put("filtros", String.valueOf(filtrosNode));
                                out.println(JsonMiddleware.mapToJson(jsonFields));
                                System.out.println(in.readLine());
                                break;
                            }
//                            case 5: {
//                                System.out.println("Email: ");
//                                String email = scanner.nextLine();
//                                System.out.println("Senha: ");
//                                String password = scanner.nextLine();
//
//                                Map<String, String> jsonFields = new HashMap<>();
//                                jsonFields.put("email", email);
//                                jsonFields.put("senha", password);
//                                jsonFields.put("operacao", "loginEmpresa");
//
//                                out.println(JsonMiddleware.mapToJson(jsonFields));
//                                System.out.println(in.readLine());
//                                break;
//                            }
//                            case 6: {
//                                System.out.println("Token: ");
//                                String token = scanner.nextLine();
//
//                                Map<String, String> jsonFields = new HashMap<>();
//                                jsonFields.put("token", token);
//                                jsonFields.put("operacao", "logout");
//
//                                out.println(JsonMiddleware.mapToJson(jsonFields));
//                                System.out.println(in.readLine());
//                                break;
//                            }
//                            case 7: {
//                                System.out.println("Fechando conexão e desligando servidor");
//                                return;
//                            }
                            default: {
                                System.out.println("Opção inválida");
                                break;
                            }
                        }
                    }
                    break;
                }
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
