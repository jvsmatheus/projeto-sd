package Auth;

import DAO.UserDAO;
import Model.User;

public class LoginUtil {

    static UserDAO userDAO = new UserDAO();

    public static void login (String email, String senha) {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            if (JwtService.checkPassword(senha, user.getSenha())) {
                userDAO.updateUser(user.getId(), user);
                System.out.println("Logado com sucesso");
            } else {
                System.out.println("Login e/ou senha incorreto");
            }
        } else {
            System.out.println("Usuário não encontrado");
        }
    }

    public static void logout(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            userDAO.updateUser(user.getId(), user);
            System.out.println("Deslogado com sucesso");
        } else {
            System.out.println("Usuário não encontrado");
        }
    }
}
