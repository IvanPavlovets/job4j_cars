package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository crudRepository;

    /**
     * Добавление модели в контекст персистенции.
     * @param engine
     * @return boolean
     */
    public boolean create(Engine engine) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(engine));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param engine
     */
    public boolean update(Engine engine) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(engine));
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
                    "DELETE Engine WHERE id = :fId",
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
