package Services;

import DAO.EmpressDAO;
import DAO.UserDAO;
import Middlewares.JsonMiddleware;
import Model.Empress;
import Model.ResponseEntities.MessageResponseEntity;
import Model.ResponseEntities.ResponseEntity;
import Model.ResponseEntities.TokenResponseEntity;
import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;
import java.util.UUID;

import org.json.JSONObject;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final EmpressDAO empressDAO = new EmpressDAO();

    ObjectMapper mapper = new ObjectMapper();

    public String createUser(JsonNode node) throws JsonProcessingException {

        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setNome(node.get("nome").asText());
        user.setEmail(node.get("email").asText());
        user.setSenha(node.get("senha").asText());

        try {
            if (Objects.isNull(userDAO.getUserByEmail(node.get("email").asText()))) {
                userDAO.createUser(user);
                User userGetId = userDAO.getUserByEmail(user.getEmail());
                return JsonMiddleware.objectToJson(new TokenResponseEntity(201, "cadastrarCandidato", userGetId.getId()));
            }
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "cadastrarCandidato", "Email já cadastrado"));
        } catch (Exception e) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "cadastrarCandidato", "erro ao cadastrar candidato"));
        }
    }

    public JSONObject vizualizarCandidato(String email) throws JsonProcessingException {
        User user = userDAO.getUserByEmail(email);
        
        JSONObject json = new JSONObject();
        
        if (Objects.isNull(user)) {
            json.put("mensagem", "E-mail não encontrado");
            json.put("status", 404);
            json.put("operacao", "visualizarCandidato");
            
            return json;
        }
        
        json.put("nome", user.getNome());
        json.put("senha", user.getSenha());
        json.put("status", 201);
        json.put("operacao", "visualizarCandidato");
        
        return json;
    }

    // Não está atualizando
    public String updateUser(JsonNode node) throws JsonProcessingException {
        User user = userDAO.getUserByEmail(node.get("email").asText());

        if (Objects.isNull(user)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "vizualizarCandidato", "E-mail não encontrado"));
        }

        if (user.isLogado()) {
            System.out.println(userDAO.updateUser(user.getEmail(), user));
            return JsonMiddleware.objectToJson(new ResponseEntity(201, "atualizarCandidato"));
        }

        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "atualizarCandidato", "Não autorizado"));
    }

    public String deleteUser(JsonNode node) throws JsonProcessingException {
        User user = userDAO.getUserByEmail(node.get("email").asText());

        if (Objects.isNull(user)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "apagarCandidato", "E-mail não encontrado"));
        }

        if (user.isLogado()) {
            userDAO.deleteUser(node.get("email").asText());

            ObjectNode jsonNode = mapper.createObjectNode();

            jsonNode.put("operacao", "apagarCandidato");
            jsonNode.put("status", "201");

            return mapper.writeValueAsString(jsonNode);
        }

        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "apagarCandidato", "Não autorizado"));
    }

    public String userLogin(String email, String senha) throws JsonProcessingException {
        var user = userDAO.getUserByEmail(email);

        if (email.equals(user.getEmail()) && senha.equals(user.getSenha())) {
            user.setLogado(true);

            userDAO.updateUser(email, user);
            return JsonMiddleware.objectToJson(new TokenResponseEntity(200, "loginCandidato", user.getId()));
        }
        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "loginCandidato", "Login e/ou senha incorretos"));
    }

    public String logout(String token) throws JsonProcessingException {
        User user = userDAO.getUSerById(token);

        if (Objects.isNull(user)) {
            Empress empress = empressDAO.getUSerById(token);

            if (Objects.isNull(empress)) {
                return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "logout", "Token inválido"));
            }

            empress.setLogado(false);
            empressDAO.updateEmpress(empress.getEmail(), empress);
            return JsonMiddleware.objectToJson(new TokenResponseEntity(204, "logout", empress.getId()));
        }

        user.setLogado(false);
        userDAO.updateUser(user.getEmail(), user);
        return JsonMiddleware.objectToJson(new TokenResponseEntity(204, "logout", user.getId()));
    }
}
