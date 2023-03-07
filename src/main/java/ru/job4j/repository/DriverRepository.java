package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class DriverRepository {

    private final CrudRepository crudRepository;


    /**
     * Добавление модели в контекст персистенции.
     * @param driver
     * @return boolean
     */
    public boolean create(Driver driver) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(driver));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param driver
     */
    public boolean update(Driver driver) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(driver));
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
                    "DELETE Driver WHERE id = :fId",
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
     * @return List<Driver>
     */
    public List<Driver> findAllDrivers() {
        return crudRepository.query(
                "from Driver", Driver.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Driver>
     */
    public Optional<Driver> findDriverById(int id) {
        Optional<Driver> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Driver as d where d.id = :fId", Driver.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

}
