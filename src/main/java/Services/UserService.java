package Services;

import Auth.JwtService;
import DAO.LogedUserDAO;
import DAO.UserDAO;
import Middlewares.JsonMiddleware;
import Model.LogedUSer;
import Model.ResponseEntity;
import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Objects;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final LogedUserDAO logedUserDAO = new LogedUserDAO();

    public boolean createUser(User user) {
        return userDAO.createUser(user);
    }

    public List<User> findAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean updateUser(String email, User user) {
        return userDAO.updateUser(email, user);
    }

    public boolean deleteUser(String email) {
        return userDAO.deleteUser(email);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public String userLogin(String email, String senha) throws JsonProcessingException {
        User user = userDAO.getUserByEmail(email);
        System.out.println(user);

        String userJson = JsonMiddleware.objectToJson(user);

        if (email.equals(user.getEmail()) && JwtService.checkPassword(senha, user.getSenha())) {
            LogedUSer logedUSer = new LogedUSer();
            logedUSer.setId(user.getId());
            logedUSer.setEmail(email);
            logedUserDAO.createLogedUser(logedUSer);
            return JsonMiddleware.objectToJson(new ResponseEntity(200, "loginCandidato", logedUSer.getId()));
        }
        return JsonMiddleware.objectToJson(new ResponseEntity(401, "loginCandidato", "Login e/ou senha incorretos"));
    }

    public String userLogout(String email) throws JsonProcessingException {
        User user = userDAO.getUserByEmail(email);

        if (Objects.isNull(user)) {
            return JsonMiddleware.objectToJson(new ResponseEntity(401, "logoutCandidato", "Email não cadastrado"));
        }

        LogedUSer logedUser = logedUserDAO.getUserLogedUSerByEmail(user.getEmail());

        if (Objects.isNull(logedUser)) {
            return JsonMiddleware.objectToJson(new ResponseEntity(401, "logoutCandidato", "Usuário não está logado"));
        }

        logedUserDAO.deleteLogedUSer(logedUser.getEmail());
        return JsonMiddleware.objectToJson(new ResponseEntity(200, "logoutCandidato", logedUser.getId()));
    }
}
