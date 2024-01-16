package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();

        try (session){
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS \"User1\" (" +
                    "id BIGSERIAL PRIMARY KEY," +
                    "name VARCHAR(15) NOT NULL," +
                    "lastName VARCHAR(15) NOT NULL," +
                    "age INTEGER NOT NULL)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Возникла ошибка при создании таблицы: " + e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();

        try (session){
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS \"User1\"").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка удаления таблицы пользователей: " + e.getMessage());
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();

        try(session) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Возникла ошибка при добавлении пользователей: " + e.getMessage());
            session.getTransaction().rollback();
        }
    }



    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        try (session){
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка удаления пользователя по идентификатору: " + e.getMessage());
            session.getTransaction().rollback();
        }
    }

    @Override
    public List getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("SELECT u FROM User u").list();
        } catch (HibernateException e) {
            System.out.println("Ошибка при получении всех пользователей: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
            try (session){
                session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println("Ошибка при очистки таблицы пользователей: " + e.getMessage());
                session.getTransaction().rollback();
            }
    }
}
