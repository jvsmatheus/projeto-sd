package Middlewares;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtils {

    EntityManagerFactory emf;
    EntityManager manager;

    public HibernateUtils() {
        try {
            this.emf = Persistence.createEntityManagerFactory("database");
            this.manager = emf.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public EntityManager getManager() {
        return manager;
    }

}
