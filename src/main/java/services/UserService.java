package services;

import DAO.UserDAO;
import Model.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean createUser(User user) {
        return userDAO.createUser(user);
    }

    public User findAllUsers() {
        System.out.println("teste");
        return null;
    }
}
