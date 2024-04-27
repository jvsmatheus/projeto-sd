package DAO;

import Model.User;
import middlewares.HibernateUtils;

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
}
