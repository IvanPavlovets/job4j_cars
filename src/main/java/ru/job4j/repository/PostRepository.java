package ru.job4j.repository;

import lombok.AllArgsConstructor;
import ru.job4j.model.Brand;
import ru.job4j.model.Post;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PostRepository {

    private static final String HQL_POST = new StringBuilder()
            .append("select distinct p from Post p ")
            .append("join fetch p.car c ")
            .append("join fetch c.model mo ")
            .append("join fetch mo.brand br ")
            .append("join fetch c.carbody cb ")
            .append("join fetch c.engine e ")
            .append("join fetch c.drivers d ")
            .append("join fetch p.user u where ").toString();

    private final CrudRepository crudRepository;

    /**
     * Показать объявления за последний день.
     *
     * @return List<Post>
     */
    public List<Post> findToday() {
        return crudRepository.query(
                HQL_POST + "p.created > CURRENT_DATE", Post.class
        );
    }

    /**
     * Показать объявления с фото.
     *
     * @return List<Post>
     */
    public List<Post> findWithPhoto() {
        return crudRepository.query(
                HQL_POST + "c.photo.size != null", Post.class
        );
    }

    /**
     * Показать объявления определенной марки.
     *
     * @return List<Post>
     */
    public List<Post> findByBrand(Brand brand) {
        return crudRepository.query(
                HQL_POST + "mo.brand = :brand", Post.class,
                Map.of(":brand", brand)
        );
    }

}
