package DAO;

import Middlewares.HibernateUtils;
import Model.Empress;
import jakarta.persistence.NoResultException;

import java.util.List;

public class EmpressDAO {

    HibernateUtils db = new HibernateUtils();

    public List<Empress> getAllEmpresss() {
        return db.getManager().createQuery("FROM Empress u", Empress.class).getResultList();
    }

    public Boolean createEmpress(Empress empress) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(empress);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean updateEmpress(String email, Empress newEmpressDetails) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            Empress existingEmpress = getEmpressByEmail(email);// Busca o usuário pelo ID
            System.out.println(existingEmpress);
            if (existingEmpress == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            // Atualiza os dados do usuário com os novos detalhes
            existingEmpress.setRazaoSocial(newEmpressDetails.getRazaoSocial());
            existingEmpress.setEmail(newEmpressDetails.getEmail());
            existingEmpress.setCnpj(newEmpressDetails.getCnpj());
            existingEmpress.setSenha(newEmpressDetails.getSenha());
            existingEmpress.setDescricao(newEmpressDetails.getDescricao());
            existingEmpress.setRamo(newEmpressDetails.getRamo());

            db.getManager().merge(existingEmpress); // Atualiza o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public Boolean deleteEmpress(String email) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            Empress empress =  getEmpressByEmail(email); // Busca o usuário pelo email
            if (empress == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            db.getManager().remove(empress); // Deleta o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public Empress getEmpressByEmail(String email) {
        try {
            String query = "SELECT u FROM Empress u WHERE u.email = :email"; // Cria a consulta JPQL
            Empress empress = db.getManager().createQuery(query, Empress.class)
                    .setParameter("email", email) // Define o parâmetro de e-mail
                    .getSingleResult(); // Executa a consulta e retorna o resultado único
            return empress; // Retorna o usuário encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }
    public Empress getUSerById(String id) {
        try {
            String query = "SELECT u FROM Empress u WHERE u.id = :id"; // Cria a consulta JPQL
            return db.getManager().createQuery(query, Empress.class)
                    .setParameter("id", id) // Define o parâmetro de e-mail
                    .getSingleResult(); // Retorna o usuário encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }

}
