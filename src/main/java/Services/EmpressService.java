package Services;

import DAO.EmpressDAO;
import Middlewares.JsonMiddleware;
import Model.ResponseEntities.MessageResponseEntity;
import Model.ResponseEntities.TokenResponseEntity;
import Model.Empress;
import Model.Empress;
import Model.Empress;
import Model.Empress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EmpressService {

    private final EmpressDAO empressDAO = new EmpressDAO();

    ObjectMapper mapper = new ObjectMapper();


    public String createEmpress(JsonNode node) throws JsonProcessingException {
        Empress empress = new Empress();
        empress.setId(String.valueOf(UUID.randomUUID()));
        empress.setRazaoSocial(node.get("razaoSocial").asText());
        empress.setCnpj(node.get("cnpj").asText());
        empress.setEmail(node.get("email").asText());
        empress.setSenha(node.get("senha").asText());
        empress.setDescricao(node.get("descricao").asText());
        empress.setRamo(node.get("ramo").asText());

        try {
            if (Objects.isNull(empressDAO.getEmpressByEmail(node.get("email").asText()))) {
                empressDAO.createEmpress(empress);
                Empress empressGetId = empressDAO.getEmpressByEmail(empress.getEmail());
                return JsonMiddleware.objectToJson(new TokenResponseEntity(201, "cadastrarEmpresa", empressGetId.getId()));
            }
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "cadastrarEmpresa", "Email já cadastrado"));
        } catch (Exception e) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "cadastrarEmpresa", "erro ao cadastrar candidato"));
        }
    }
    
    public String getEmpressByEmail(JsonNode node) throws JsonProcessingException {
        Empress empress = empressDAO.getEmpressByEmail(node.get("email").asText());

        if (Objects.isNull(empress)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "vizualizarEmpresa", "E-mail não encontrado"));
        }

        if (empress.isLogado()) {
            ObjectNode jsonNode = mapper.createObjectNode();

            jsonNode.put("operacao", "vizualizarEmpresa");
            jsonNode.put("status", "201");
            jsonNode.put("razaoSocial", empress.getRazaoSocial());
            jsonNode.put("cnpj", empress.getCnpj());
            jsonNode.put("senha", empress.getSenha());
            jsonNode.put("descricao", empress.getDescricao());
            jsonNode.put("ramo", empress.getRamo());

            return mapper.writeValueAsString(jsonNode);
        }

        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "vizualizarEmpresa", "Não autorizado"));
    }

    public boolean updateEmpress(String email, Empress empress) {
        return empressDAO.updateEmpress(email, empress);
    }

    public String deleteEmpress(JsonNode node) throws JsonProcessingException {
        Empress empress = empressDAO.getEmpressByEmail(node.get("email").asText());

        if (Objects.isNull(empress)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "apagarEmpresa", "E-mail não encontrado"));
        }

        if (empress.isLogado()) {
            empressDAO.deleteEmpress(node.get("email").asText());

            ObjectNode jsonNode = mapper.createObjectNode();

            jsonNode.put("operacao", "apagarEmpresa");
            jsonNode.put("status", "201");

            return mapper.writeValueAsString(jsonNode);
        }

        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "apagarEmpresa", "Não autorizado"));
    }

    public String empressLogin(String email, String senha) throws JsonProcessingException {
        var empress = empressDAO.getEmpressByEmail(email);

        if (Objects.isNull(empress)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(404, "loginEmpresa", "E-mail não encontrado"));
        }

        if (email.equals(empress.getEmail()) && senha.equals(empress.getSenha())) {
            empress.setLogado(true);

            empressDAO.updateEmpress(email, empress);
            return JsonMiddleware.objectToJson(new TokenResponseEntity(200, "loginEmpresa", empress.getId()));
        }
        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "loginEmpresa", "Login e/ou senha incorretos"));
    }
}
