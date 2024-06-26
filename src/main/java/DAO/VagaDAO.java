package DAO;

import Middlewares.HibernateUtils;
import Model.Vaga;
import jakarta.persistence.NoResultException;
import java.util.List;

public class VagaDAO {

    private HibernateUtils db = new HibernateUtils();

    public List<Vaga> getAllVagas() {
        return db.getManager().createQuery("FROM Vaga", Vaga.class).getResultList();
    }

    public Boolean createVaga(Vaga vaga) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(vaga);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (db.getManager().getTransaction().isActive()) {
                db.getManager().getTransaction().rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean atualizarVaga(long id, Vaga newVagaDetails) {
        try {
            db.getManager().getTransaction().begin();

            Vaga existingVaga = getVagaById(id);
            if (existingVaga == null) {
                db.getManager().getTransaction().rollback();
                return false;
            }

            existingVaga.setNome(newVagaDetails.getNome());
            existingVaga.setFaixaSalarial(newVagaDetails.getFaixaSalarial());
            existingVaga.setDescricao(newVagaDetails.getDescricao());
            existingVaga.setEstado(newVagaDetails.getEstado());

            db.getManager().merge(existingVaga);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (db.getManager().getTransaction().isActive()) {
                db.getManager().getTransaction().rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean apagarVaga(long id) {
        try {
            db.getManager().getTransaction().begin();

            Vaga vaga = getVagaById(id);
            if (vaga == null) {
                db.getManager().getTransaction().rollback();
                return false;
            }

            db.getManager().remove(vaga);
            db.getManager().getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (db.getManager().getTransaction().isActive()) {
                db.getManager().getTransaction().rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }

    public Vaga getVagaById(long id) {
        try {
            String query = "SELECT u FROM Vaga u WHERE u.id = :id";
            return db.getManager().createQuery(query, Vaga.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Vaga getVagaByNome(String nome) {
        try {
            String query = "SELECT u FROM Vaga u WHERE u.nome = :nome";
            return db.getManager().createQuery(query, Vaga.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Vaga> getVagasByEmail(String email) {
        try {
            String query = "SELECT u FROM Vaga u WHERE u.empresa.email = :email";
            return db.getManager().createQuery(query, Vaga.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Boolean cadastrarVaga(Vaga vaga) {
        return createVaga(vaga);
    }
}
