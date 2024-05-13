package DAO;

import Model.User;
import jakarta.persistence.NoResultException;
import Middlewares.HibernateUtils;

import java.util.List;

public class UserDAO {

    HibernateUtils db = new HibernateUtils();

    public List<User> getAllUsers() {
        return db.getManager().createQuery("FROM User u", User.class).getResultList();
    }

    public Boolean createUser(User user) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(user);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback();
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean updateUser(String email, User newUserDetails) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            User existingUser = db.getManager().find(User.class, email); // Busca o usuário pelo ID
            if (existingUser == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            // Atualiza os dados do usuário com os novos detalhes
            existingUser.setNome(newUserDetails.getNome());
            existingUser.setEmail(newUserDetails.getEmail());
            existingUser.setSenha(newUserDetails.getSenha());
            // Adicione outros campos conforme necessário

            db.getManager().merge(existingUser); // Atualiza o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public Boolean deleteUser(String email) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            User user =  getUserByEmail(email); // Busca o usuário pelo email
            if (user == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            db.getManager().remove(user); // Deleta o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public User getUserByEmail(String email) {
        try {
            String query = "SELECT u FROM User u WHERE u.email = :email"; // Cria a consulta JPQL
            User user = db.getManager().createQuery(query, User.class)
                    .setParameter("email", email) // Define o parâmetro de e-mail
                    .getSingleResult(); // Executa a consulta e retorna o resultado único
            return user; // Retorna o usuário encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }
}
