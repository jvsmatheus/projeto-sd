package DAO;

import java.util.ArrayList;
import java.util.List;

import Middlewares.HibernateUtils;
import Model.CompetenciaExperiencia;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CompetenciaExperienciaDAO {
	
	HibernateUtils db = new HibernateUtils();

    public List<CompetenciaExperiencia> getAllCompetenciaExperiencias() {
        return db.getManager().createQuery("FROM CompetenciaExperiencia u", CompetenciaExperiencia.class).getResultList();
    }

    public Boolean cadastrarCompetenciaExperiencia(CompetenciaExperiencia competenciaExperiencia) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(competenciaExperiencia);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean atualizarCompetenciaExperiencia(Long id, CompetenciaExperiencia newCompetenciaExperienciaDetails) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            CompetenciaExperiencia existingCompetenciaExperiencia = getCompetenciaExperienciaById(id);

            if (existingCompetenciaExperiencia == null) {
                db.getManager().getTransaction().rollback(); // Desfaz a transação se o usuário não for encontrado
                return false; // Retorna false se o usuário não for encontrado
            }

            existingCompetenciaExperiencia.setCompetencia(newCompetenciaExperienciaDetails.getCompetencia());
            existingCompetenciaExperiencia.setEmail(newCompetenciaExperienciaDetails.getEmail());
            existingCompetenciaExperiencia.setExperiencia(newCompetenciaExperienciaDetails.getExperiencia());

            CompetenciaExperiencia mergedCompetenciaExperiencia = db.getManager().merge(existingCompetenciaExperiencia); // Atualiza o usuário
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
    
    public Boolean atualizarCompetenciaExperienciaList(String email, CompetenciaExperiencia newCompetenciaExperienciaDetails) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            List<CompetenciaExperiencia> existingCompetencias = getCompetenciasExperienciasByEmail(email);

            if (existingCompetencias.isEmpty()) {
                db.getManager().getTransaction().rollback(); // Desfaz a transação se o usuário não for encontrado
                return false; // Retorna false se o usuário não for encontrado
            }

            for (CompetenciaExperiencia existingCompetenciaExperiencia : existingCompetencias) {
                existingCompetenciaExperiencia.setCompetencia(newCompetenciaExperienciaDetails.getCompetencia());
                existingCompetenciaExperiencia.setEmail(newCompetenciaExperienciaDetails.getEmail());
                existingCompetenciaExperiencia.setExperiencia(newCompetenciaExperienciaDetails.getExperiencia());

                db.getManager().merge(existingCompetenciaExperiencia); // Atualiza o usuário
            }

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


    public Boolean apagarCompetenciaExperiencia(Long id) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            CompetenciaExperiencia competenciaExperiencia =  getCompetenciaExperienciaById(id); // Busca o usuário pelo email
            if (competenciaExperiencia == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            db.getManager().remove(competenciaExperiencia); // Deleta o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public CompetenciaExperiencia getCompetenciaExperienciaByEmail(String email) {
        try {
            String query = "SELECT u FROM CompetenciaExperiencia u WHERE u.email = :email"; // Cria a consulta JPQL
            CompetenciaExperiencia competenciaExperiencia = db.getManager().createQuery(query, CompetenciaExperiencia.class)
                    .setParameter("email", email) // Define o parâmetro de e-mail
                    .getSingleResult(); // Executa a consulta e retorna o resultado único
            return competenciaExperiencia; // Retorna o usuário encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }
    
    public List<CompetenciaExperiencia> getCompetenciaExperienciaByEmailList(String email) {
        try {
            String query = "SELECT u FROM CompetenciaExperiencia u WHERE u.email = :email"; // Cria a consulta JPQL
            List<CompetenciaExperiencia> competenciasExperiencia = db.getManager().createQuery(query, CompetenciaExperiencia.class)
                    .setParameter("email", email) // Define o parâmetro de e-mail
                    .getResultList(); // Executa a consulta e retorna a lista de resultados
            return competenciasExperiencia; // Retorna a lista de competências encontradas
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
        }
    }
    
    public List<CompetenciaExperiencia> getCompetenciaExperienciaByEmailAndCompetenciaList(String email, String competencia) {
        String queryStr = "SELECT c FROM CompetenciaExperiencia c WHERE c.email = :email AND c.competencia = :competencia";
        TypedQuery<CompetenciaExperiencia> query = db.getManager().createQuery(queryStr, CompetenciaExperiencia.class);
        query.setParameter("email", email);
        query.setParameter("competencia", competencia);
        return query.getResultList();
    }

    
    public List<CompetenciaExperiencia> getCompetenciasExperienciasByEmail(String email) {
        try {
            String query = "SELECT ce FROM CompetenciaExperiencia ce WHERE ce.email = :email";
            TypedQuery<CompetenciaExperiencia> typedQuery = db.getManager().createQuery(query, CompetenciaExperiencia.class);
            typedQuery.setParameter("email", email);
            return typedQuery.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public CompetenciaExperiencia getCompetenciaExperienciaByEmailAndCompetencia(String email, String competencia) {
        try {
            String query = "SELECT u FROM CompetenciaExperiencia u WHERE u.email = :email AND u.competencia = :competencia"; // Cria a consulta JPQL
            CompetenciaExperiencia competenciaExperiencia = db.getManager().createQuery(query, CompetenciaExperiencia.class)
                    .setParameter("email", email) // Define o parâmetro de e-mail
                    .setParameter("competencia", competencia) // Define o parâmetro de competência
                    .getSingleResult(); // Executa a consulta e retorna o resultado único
            return competenciaExperiencia; // Retorna o resultado encontrado
        } catch (NoResultException nre) {
            return null; // Retorna null se nenhum resultado for encontrado
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime o rastreamento de pilha se ocorrer outro erro
            return null;
        }
    }
    
    public CompetenciaExperiencia getCompetenciaExperienciaById(Long id) {
        try {
            String query = "SELECT u FROM CompetenciaExperiencia u WHERE u.id = :id"; // Cria a consulta JPQL
            return db.getManager().createQuery(query, CompetenciaExperiencia.class)
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
