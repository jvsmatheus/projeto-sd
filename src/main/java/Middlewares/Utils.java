package Middlewares;

public class Utils {

    // Função para validar o e-mail
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (email.length() < 7 || email.length() > 50) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    // Função para validar a senha
    public static boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.length() >= 3 && password.length() <= 8;
    }

    // Função para validar o nome do usuário
    public static boolean isUsernameValid(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        return username.length() >= 6 && username.length() <= 30;
    }

    // Função para validar todos os campos
    public static boolean areUserDetailsValid(String username, String email, String password) {
        return isUsernameValid(username) && isEmailValid(email) && isPasswordValid(password);
    }
}
