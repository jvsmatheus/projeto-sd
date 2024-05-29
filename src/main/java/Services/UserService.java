package Services;

import DAO.UserDAO;
import Middlewares.JsonMiddleware;
import Model.ResponseEntities.MessageResponseEntity;
import Model.ResponseEntities.TokenResponseEntity;
import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Objects;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

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
        var user = userDAO.getUserByEmail(email);
        System.out.println(user);
        System.out.println(senha);
        System.out.println(senha);
        System.out.println(user.getSenha());

        if (email.equals(user.getEmail()) && senha.equals(user.getSenha())) {
            user.setLogado(true);

            userDAO.updateUser(email, user);
            return JsonMiddleware.objectToJson(new TokenResponseEntity(200, "loginCandidato", user.getId()));
        }
        return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "loginCandidato", "Login e/ou senha incorretos"));
    }

    public String userLogout(String token) throws JsonProcessingException {
        User user = userDAO.getUSerById(token);

        if (Objects.isNull(user)) {
            return JsonMiddleware.objectToJson(new MessageResponseEntity(401, "logout", "Email não cadastrado"));
        }

        user.setLogado(false);
        userDAO.updateUser(user.getEmail(), user);
        return JsonMiddleware.objectToJson(new TokenResponseEntity(200, "logout", user.getId()));
    }
}
