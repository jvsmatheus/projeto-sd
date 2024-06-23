package DAO;

import Middlewares.HibernateUtils;
import Model.Empresa;
import jakarta.persistence.NoResultException;

import java.util.List;

public class EmpresaDAO {

    HibernateUtils db = new HibernateUtils();

    public List<Empresa> getAllEmpresas() {
        return db.getManager().createQuery("FROM Empresa u", Empresa.class).getResultList();
    }

    public Boolean createEmpresa(Empresa empress) {
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

    public Boolean atualizarEmpresa(String email, Empresa newEmpresaDetails) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            Empresa existingEmpresa = getEmpresaByEmail(email);// Busca o usuário pelo ID
            if (existingEmpresa == null) {
                return false; // Retorna false se o usuário não for encontrado
            }

            // Atualiza os dados do usuário com os novos detalhes
            existingEmpresa.setRazaoSocial(newEmpresaDetails.getRazaoSocial());
            existingEmpresa.setEmail(newEmpresaDetails.getEmail());
            existingEmpresa.setCnpj(newEmpresaDetails.getCnpj());
            existingEmpresa.setSenha(newEmpresaDetails.getSenha());
            existingEmpresa.setDescricao(newEmpresaDetails.getDescricao());
            existingEmpresa.setRamo(newEmpresaDetails.getRamo());

            db.getManager().merge(existingEmpresa); // Atualiza o usuário
            db.getManager().getTransaction().commit(); // Completa a transação com um commit
            return true; // Retorna verdadeiro se a operação foi bem-sucedida
        } catch (Exception ex) {
            db.getManager().getTransaction().rollback(); // Desfaz a transação se ocorrer um erro
            ex.printStackTrace();
        }
        return false; // Retorna falso se a operação falhar
    }

    public Boolean apagarEmpresa(String email) {
        try {
            db.getManager().getTransaction().begin(); // Inicia uma transação

            Empresa empress =  getEmpresaByEmail(email); // Busca o usuário pelo email
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

    public Empresa getEmpresaByEmail(String email) {
        try {
            String query = "SELECT u FROM Empresa u WHERE u.email = :email"; // Cria a consulta JPQL
            Empresa empress = db.getManager().createQuery(query, Empresa.class)
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
    public Empresa getEmpresaById(String id) {
        try {
            String query = "SELECT u FROM Empresa u WHERE u.id = :id"; // Cria a consulta JPQL
            return db.getManager().createQuery(query, Empresa.class)
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
