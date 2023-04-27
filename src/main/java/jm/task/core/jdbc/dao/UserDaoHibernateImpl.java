package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getConnectionHibernate();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)")
                            .addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
