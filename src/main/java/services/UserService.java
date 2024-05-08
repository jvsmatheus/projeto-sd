package services;

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

    public boolean updateUser(Long id, User user) {
        return userDAO.updateUser(id, user);
    }

}
