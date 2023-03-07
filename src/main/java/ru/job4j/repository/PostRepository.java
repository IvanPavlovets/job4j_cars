package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Brand;
import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class PostRepository {

    private static final String HQL_POST = new StringBuilder()
            .append("select distinct p from Post p ")
            .append("join fetch p.user us ")
            .append("join fetch p.priceHistories ps ")
            .append("left join fetch p.participates pr ")
            .append("join fetch p.car c ")
            .append("join fetch c.model mo ")
            .append("join fetch mo.brand br ")
            .append("join fetch c.carBody cb ")
            .append("join fetch c.engine e ")
            .append("join fetch c.drivers d")
            .toString();

    private final CrudRepository crudRepository;


    public boolean create(Post post) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(post));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param post
     */
    public boolean update(Post post) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(post));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Удаление модели.
     *
     * @param post
     * @return boolean
     */
    public boolean delete(Post post) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.delete(post));
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
                    "DELETE Post WHERE id = :fId",
                    Map.of("fId", id)
            );
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Post>
     */
    public List<Post> findAll() {
        return crudRepository.query(
                "from Post fetch all properties order by id asc", Post.class
        );
    }

    /**
     * Показать объявления за последний день.
     *
     * @return List<Post>
     */
    public List<Post> findToday() {
        return crudRepository.query(
                HQL_POST + " where p.created > :lastDay", Post.class,
                Map.of("lastDay", LocalDateTime.now().minusHours(24))
        );
    }

    /**
     * Показать объявления с фото.
     *
     * @return List<Post>
     */
    public List<Post> findWithPhoto() {
        return crudRepository.query(
                HQL_POST + " where c.photo != null", Post.class
        );
    }

    /**
     * Показать объявления определенной марки.
     *
     * @return List<Post>
     */
    public List<Post> findByBrand(String brandName) {
        return crudRepository.query(
                HQL_POST + " where br.name = :brandName", Post.class,
                Map.of("brandName", brandName)
        );
    }


    /**
     * Поиск обьявления по id.
     *
     * @return Optional<Post>
     */
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                HQL_POST + " where p.id = :fId", Post.class,
                Map.of("fId", id)
        );
    }


}
