package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class BrandRepository {

    private final CrudRepository crudRepository;


    /**
     * Добавление модели в контекст персистенции.
     * @param brand
     * @return boolean
     */
    public boolean create(Brand brand) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(brand));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param brand
     */
    public boolean update(Brand brand) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(brand));
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
                    "DELETE Brand WHERE id = :fId",
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
     * @return List<Brand>
     */
    public List<Brand> findAllBrand() {
        return crudRepository.query(
                "from Brand", Brand.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Engine>
     */
    public Optional<Brand> findBrandById(int id) {
        Optional<Brand> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Brand as b where b.id = :fId", Brand.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

}
