package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class UserRepository {

    private final CrudRepository crudRepository;

    /**
     * Добавление модели в контекст персистенции.
     * @param user
     * @return boolean
     */
    public boolean create(User user) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(user));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public boolean update(User user) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(user));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Удаление модели.
     * @return boolean
     */
    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE User WHERE id = :fId",
                    Map.of("fId", id)
            );
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User as u order by u.id asc", User.class);
    }

    /**
     * Найти пользователя по ID.
     * В query.setParameter() указываем
     * привязку параметров извне.
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "from User as u where u.id = :fId", User.class,
                Map.of("fId", id)
        );
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
        return crudRepository.query(
                "from User as u where u.login like concat('%', :fKey, '%')", User.class,
                Map.of("fKey", key)
        );
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User as u where u.login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }

}
