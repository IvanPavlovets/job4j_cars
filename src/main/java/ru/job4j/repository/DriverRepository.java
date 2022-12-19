package ru.job4j.repository;

import lombok.AllArgsConstructor;
import ru.job4j.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class DriverRepository {

    private final CrudRepository crudRepository;

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
