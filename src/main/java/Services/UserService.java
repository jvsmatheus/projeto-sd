package Services;

import DAO.UserDAO;
import Model.User;

import java.util.List;

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
}
