//import config.JpaConfiguration;

import Auth.JwtService;
import DAO.UserDAO;
import Model.User;

import java.io.IOException;

import static Middlewares.Utils.isEmailValid;

public class Teste {

    public static void main(String[] args) throws IOException {
//        Inicializando o contexto do Spring com a classe de configuração JPA
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Teste.class.getPackage().getName());

//        UserServices userServices = context.getBean(UserServices.class);
//
//        userServices.save(user);

        // Fechando o contexto ao finalizar a aplicação
//        context.close();
        User user = new User("Matheus", "matheus@gmail.com", "12345");
//        User user2 = new User("Matheus", "matheus222@gmail.com", "12345");
//        User user2 = new User("João", "42999935401");
//        User user3 = new User("Pedro", "42999935401");
//
//        ArrayList<Object> users = new ArrayList<>();
//        users.add(user);
//        users.add(user2);
//        users.add(user3);
//        Cliente.iniciar(user);
//        System.out.println(objectToJson(user));

        UserDAO userDAO = new UserDAO();

//        System.out.println(userDAO.createUser(user));
//
//        System.out.println(userDAO.getAllUsers());

//        System.out.println(userDAO.getUserByEmail("matheus@gmail.com"));

//        LoginUtil.login(user.getEmail(), "12345");
//        LoginUtil.logout("matheus@gmail.com");

//        System.out.println(JwtService.checkPassword("12345", user.getSenha()));

//        System.out.println(isEmailValid("example@example.com")); // true
//        System.out.println(isEmailValid("e@e.co~ç")); // false, menos de 7 caracteres
//        System.out.println(isEmailValid("too-short@a.co")); // false, menos de 7 caracteres
//        System.out.println(isEmailValid("thisisaverylongemailaddress@example.com")); // true
//        System.out.println(isEmailValid("incorrect-email-format@com")); // false

        System.out.println(JwtService.checkPassword("12345", JwtService.hashPassword("12345")));
    }
}
