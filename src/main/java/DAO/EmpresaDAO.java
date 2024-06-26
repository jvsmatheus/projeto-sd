package DAO;

import Middlewares.HibernateUtils;
import Model.Empresa;
import jakarta.persistence.NoResultException;
import java.util.List;

public class EmpresaDAO {

    private HibernateUtils db = new HibernateUtils();

    public List<Empresa> getAllEmpresas() {
        return db.getManager().createQuery("FROM Empresa", Empresa.class).getResultList();
    }

    public Boolean createEmpresa(Empresa empresa) {
        try {
            db.getManager().getTransaction().begin();
            db.getManager().persist(empresa);
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

    public Boolean atualizarEmpresa(String email, Empresa newEmpresaDetails) {
        try {
            db.getManager().getTransaction().begin();

            Empresa existingEmpresa = getEmpresaByEmail(email);
            if (existingEmpresa == null) {
                db.getManager().getTransaction().rollback();
                return false;
            }

            existingEmpresa.setRazaoSocial(newEmpresaDetails.getRazaoSocial());
            existingEmpresa.setEmail(newEmpresaDetails.getEmail());
            existingEmpresa.setCnpj(newEmpresaDetails.getCnpj());
            existingEmpresa.setSenha(newEmpresaDetails.getSenha());
            existingEmpresa.setDescricao(newEmpresaDetails.getDescricao());
            existingEmpresa.setRamo(newEmpresaDetails.getRamo());

            db.getManager().merge(existingEmpresa);
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

    public Boolean apagarEmpresa(String email) {
        try {
            db.getManager().getTransaction().begin();

            Empresa empresa = getEmpresaByEmail(email);
            if (empresa == null) {
                db.getManager().getTransaction().rollback();
                return false;
            }

            db.getManager().remove(empresa);
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

    public Empresa getEmpresaByEmail(String email) {
        try {
            String query = "SELECT u FROM Empresa u WHERE u.email = :email";
            return db.getManager().createQuery(query, Empresa.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Empresa getEmpresaById(int id) {
        try {
            String query = "SELECT u FROM Empresa u WHERE u.id = :id";
            return db.getManager().createQuery(query, Empresa.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
