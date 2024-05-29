package Services;

import DAO.EmpressDAO;
import Middlewares.JsonMiddleware;
import Model.ResponseEntities.MessageResponseEntity;
import Model.ResponseEntities.TokenResponseEntity;
import Model.Empress;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Objects;

public class EmpressService {

    private final EmpressDAO empressDAO = new EmpressDAO();

    public boolean createEmpress(Empress empress) {
        return empressDAO.createEmpress(empress);
    }

    public List<Empress> findAllEmpresss() {
        return empressDAO.getAllEmpresss();
    }

    public boolean updateEmpress(String email, Empress empress) {
        return empressDAO.updateEmpress(email, empress);
    }

    public boolean deleteEmpress(String email) {
        return empressDAO.deleteEmpress(email);
    }

    public Empress getEmpressByEmail(String email) {
        return empressDAO.getEmpressByEmail(email);
    }

    public String empressLogin(String email, String senha) throws JsonProcessingException {
        var empress = empressDAO.getEmpressByEmail(email);
        System.out.println(empress);
        System.out.println(senha);
        System.out.println(senha);
        System.out.println(empress.getSenha());

        if (email.equals(empress.getEmail()) && senha.equals(empress.getSenha())) {
            empress.setLogado(true);

            empressDAO.updateEmpress(email, empress);
            return JsonMiddleware.objectToJson(new TokenResponseEntity(200, "loginCandidato", empress.getId()));
        }
        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "loginCandidato", "Login e/ou senha incorretos"));
    }

    public String empressLogout(String token) throws JsonProcessingException {
        Empress empress = empressDAO.getUSerById(token);

        if (Objects.isNull(empress)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "logout", "Email não cadastrado"));
        }

        empress.setLogado(false);
        empressDAO.updateEmpress(empress.getEmail(), empress);
        return JsonMiddleware.objectToJson(new TokenResponseEntity(200, "logout", empress.getId()));
    }
}
