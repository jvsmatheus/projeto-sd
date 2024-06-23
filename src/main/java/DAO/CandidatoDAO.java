package DAO;

import java.util.List;

import Middlewares.HibernateUtils;
import Model.Candidato;
import jakarta.persistence.NoResultException;

public class CandidatoDAO {

    HibernateUtils db = new HibernateUtils();

    public List<Candidato> getAllCandidatos() {
        return db.getManager().createQuery("FROM Candidato u", Candidato.class).getResultList();
    }

    public Boolean cadastrarCandidato(Candidato user) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(user);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean atualizarCandidato(String email, Candidato newCandidatoDetails) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            Candidato existingCandidato = getCandidatoByEmail(email);

            if (existingCandidato == null) {
                db.getManager().getTransaction().rollback(); // Desfaz a transação se o usuário não for encontrado
                return false; // Retorna false se o usuário não for encontrado
            }

            // Atualiza os dados do usuário com os novos detalhes
            existingCandidato.setNome(newCandidatoDetails.getNome());
            existingCandidato.setEmail(newCandidatoDetails.getEmail());
            existingCandidato.setSenha(newCandidatoDetails.getSenha());
            // Adicione outros campos conforme necessário

            Candidato mergedCandidato = db.getManager().merge(existingCandidato); // Atualiza o usuário
            db.getManager().flush(); // Garante que as alterações são "flushadas" para o banco de dados
            db.getManager().getTransaction().commit(); // Completa a transação com um commit

            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            if (db.getManager().getTransaction().isActive()) {
                db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            }
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public Boolean apagarCandidato(String email) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            Candidato user =  getCandidatoByEmail(email); // Busca o usuário pelo email
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

    public Candidato getCandidatoByEmail(String email) {
        try {
            String query = "SELECT u FROM Candidato u WHERE u.email = :email"; // Cria a consulta JPQL
            Candidato user = db.getManager().createQuery(query, Candidato.class)
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
    public Candidato getCandidatoById(String id) {
        try {
            String query = "SELECT u FROM Candidato u WHERE u.id = :id"; // Cria a consulta JPQL
            return db.getManager().createQuery(query, Candidato.class)
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
