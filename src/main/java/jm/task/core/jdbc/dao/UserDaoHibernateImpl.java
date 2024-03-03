package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.PropertiesUtil;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    static Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private final SessionFactory sessionFactory;
    public UserDaoHibernateImpl(Util util) {this.sessionFactory = util.getSessionFactory();}
    @Override
    public void createUsersTable() {
        String crTab = "db.crTab";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(PropertiesUtil.get(crTab)).executeUpdate();
            logger.log(Level.INFO,"Table has created!");
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO,"Table already exists");
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
    }
    @Override
    public void dropUsersTable() {
        String drTab = "db.drTab";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(PropertiesUtil.get(drTab)).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"ERROR: Table has no deleted!!");
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем %s добавлен в таблицу\n", name);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE,"ERROR: User " + name +" has no added to the table!");
        }
    }
    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User userToDelete = session.find(User.class, id);
            logger.log(Level.INFO,"Removing user with ID: " + id);
            if (userToDelete != null) {
                session.remove(userToDelete);
            }
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"ERROR: User with ID: " + id + " has NOT remove from table");
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
    }
    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM " + User.class.getSimpleName(), User.class);
            list = query.getResultList();
            logger.log(Level.INFO,"Users: " + Arrays.toString(list.toArray()));
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"ERROR: Users not found!");
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
        return list;
    }
    @Override
    public void cleanUsersTable() {
        String clTab = "db.clTab";
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(PropertiesUtil.get(clTab));  //
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"ERROR: Table has not cleaned!");
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }

    }
}
