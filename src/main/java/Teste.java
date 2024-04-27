//import config.JpaConfiguration;
import DAO.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
        import Model.Endereco;
import Model.User;

        import java.io.IOException;

public class Teste {

    public static void main(String[] args) throws IOException {
//        Inicializando o contexto do Spring com a classe de configuração JPA
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Teste.class.getPackage().getName());

//        UserServices userServices = context.getBean(UserServices.class);
//
//        userServices.save(user);

        // Fechando o contexto ao finalizar a aplicação
//        context.close();
        User user = new User("Matheus", "42999935401", new Endereco("Ponta Grossa", "Paraná", "Brasil", "Spartaco Gambassi", 31));
        User user2 = new User("João", "42999935401", new Endereco("Ponta Grossa", "Paraná", "Brasil", "Spartaco Gambassi", 31));
        User user3 = new User("Pedro", "42999935401", new Endereco("Ponta Grossa", "Paraná", "Brasil", "Spartaco Gambassi", 31));
//
//        ArrayList<Object> users = new ArrayList<>();
//        users.add(user);
//        users.add(user2);
//        users.add(user3);
//        Cliente.iniciar(user);
//        System.out.println(objectToJson(user));

        UserDAO userDAO = new UserDAO();

        System.out.println(userDAO.getAllUsers());
    }
}
