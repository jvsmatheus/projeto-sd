package DAO;

import Middlewares.HibernateUtils;
import Model.LogedUSer;
import Model.User;
import jakarta.persistence.NoResultException;

import java.util.List;

public class LogedUserDAO {

    HibernateUtils db = new HibernateUtils();

    public List<LogedUSer> getAllLogedUser() {
        return db.getManager().createQuery("FROM LogedUser u", LogedUSer.class).getResultList();
    }

    public Boolean createLogedUser(LogedUSer logedUSer) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(logedUSer);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback();
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean deleteLogedUSer(String email) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            LogedUSer logedUSer =  getUserLogedUSerByEmail(email); // Busca o usuário pelo email
            if (logedUSer == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            db.getManager().remove(logedUSer); // Deleta o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public LogedUSer getUserLogedUSerByEmail(String email) {
        try {
            String query = "SELECT u FROM LogedUSer u WHERE u.email = :email"; // Cria a consulta JPQL
            LogedUSer logedUSer = db.getManager().createQuery(query, LogedUSer.class)
                    .setParameter("email", email) // Define o parâmetro de e-mail
                    .getSingleResult(); // Executa a consulta e retorna o resultado único
            return logedUSer; // Retorna o usuário encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }

    public LogedUSer getUserLogedUSerById(String id) {
        try {
            String query = "SELECT u FROM LogedUSer u WHERE u.email = :id"; // Cria a consulta JPQL
            LogedUSer logedUSer = db.getManager().createQuery(query, LogedUSer.class)
                    .setParameter("id", id) // Define o parâmetro de e-mail
                    .getSingleResult(); // Executa a consulta e retorna o resultado único
            return logedUSer; // Retorna o usuário encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }
}
