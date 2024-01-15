package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = null;
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try {
            session = Util.getSessionFactory().openSession();
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
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS \"User1\"").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка удаления таблицы пользователей: " + e.getMessage());
        }finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Возникла ошибка при добавлении пользователей: " + e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }



    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка удаления пользователя по идентификатору: " + e.getMessage());
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public List getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("SELECT u FROM User u").list();
        } catch (HibernateException e) {
            System.out.println("Ошибка при получении всех пользователей: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
            try {
                session = Util.getSessionFactory().openSession();
                session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println("Ошибка при очистки таблицы пользователей: " + e.getMessage());
                session.getTransaction().rollback();
            }finally {
                session.close();
            }
    }
}
