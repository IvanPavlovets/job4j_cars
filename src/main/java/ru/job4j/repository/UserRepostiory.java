package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public class UserRepostiory {
    private final SessionFactory sf;

    public UserRepostiory(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                "DELETE User WHERE id = :fId")
                .setParameter("fId", userId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> usersSort = session.createQuery(
                "from User as u order by u.id asc", User.class).list();
        session.getTransaction().commit();
        session.close();
        return usersSort;
    }

    /**
     * Найти пользователя по ID.
     * В query.setParameter() указываем
     * привязку параметров извне.
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User as u where u.id = :fId", User.class);
            query.setParameter("fId", id);
            session.getTransaction().commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     * <p>
     * LIKE %key% - login-ы у которых key в середине
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> usersSort = session.createQuery(
                "from User as u where u.login like concat('%', :fKey, '%')", User.class)
                .setParameter("fKey", key)
                .list();
        session.getTransaction().commit();
        session.close();
        return usersSort;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User as u where u.login = :fLogin", User.class);
            query.setParameter("fLogin", login);
            session.getTransaction().commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
