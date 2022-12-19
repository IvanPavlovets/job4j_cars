package ru.job4j.repository;

import lombok.AllArgsConstructor;
import ru.job4j.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository crudRepository;

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Engine>
     */
    public List<Engine> findAllEngine() {
        return crudRepository.query(
                "from Engine", Engine.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Engine>
     */
    public Optional<Engine> findEngineById(int id) {
        Optional<Engine> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Engine as e where e.id = :fId", Engine.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }
}
