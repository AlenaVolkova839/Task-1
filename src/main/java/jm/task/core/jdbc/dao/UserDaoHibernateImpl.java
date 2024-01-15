package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS \"User1\" (" +
                        "id BIGSERIAL PRIMARY KEY," +
                        "name VARCHAR(15) NOT NULL," +
                        "lastName VARCHAR(15) NOT NULL," +
                        "age INTEGER NOT NULL)").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println("Возникла ошибка при создании таблицы: " + e.getMessage());
            }
        } catch (HibernateException e) {
            System.out.println("Ошибка создания сессии: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS \"User1\"").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println("Ошибка удаления таблицы пользователей: " + e.getMessage());
            }
        } catch (HibernateException e) {
            System.out.println("Ошибка создания сессии: " + e.getMessage());
        }
    }

        @Override
        public void saveUser (String name, String lastName,byte age){
            try (Session session = Util.getSessionFactory().openSession()) {
                try {
                    session.beginTransaction();
                    User user = new User(name, lastName, age);
                    session.save(user);
                    session.getTransaction().commit();
                } catch (HibernateException e) {
                    System.out.println("Возникла ошибка при добавлении пользователей: " + e.getMessage());
                }
            } catch (HibernateException e) {
                System.out.println("Ошибка создания сессии: " + e.getMessage());
            }
        }

        @Override
        public void removeUserById ( long id){
            try (Session session = Util.getSessionFactory().openSession()) {
                try {
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
            } catch (HibernateException e) {
                System.out.println("Ошибка создания сессии: " + e.getMessage());
            }
        }

        @Override
        public List getAllUsers () {
            try (Session session = Util.getSessionFactory().openSession()) {
                return session.createQuery("SELECT u FROM User u").list();
            } catch (HibernateException e) {
                System.out.println("Ошибка при получении всех пользователей: " + e.getMessage());
                return null;
            }
        }

        @Override
        public void cleanUsersTable () {
            try (Session session = Util.getSessionFactory().openSession()) {
                try {
                    session.beginTransaction();
                    session.createQuery("DELETE FROM User").executeUpdate();
                    session.getTransaction().commit();
                } catch (HibernateException e) {
                    System.out.println("Ошибка при очистки таблицы пользователей: " + e.getMessage());
                    session.getTransaction().rollback();
                }
            } catch (HibernateException e) {
                System.out.println("Ошибка создания сессии: " + e.getMessage());
            }
        }
    }
