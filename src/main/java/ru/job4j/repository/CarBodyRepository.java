package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.CarBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class CarBodyRepository {

    private final CrudRepository crudRepository;

    /**
     * Добавление модели в контекст персистенции.
     * @param body
     * @return boolean
     */
    public boolean create(CarBody body) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(body));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param body
     */
    public boolean update(CarBody body) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(body));
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
                    "DELETE CarBody WHERE id = :fId",
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
     * @return List<CarBody>
     */
    public List<CarBody> findAllCarBody() {
        return crudRepository.query(
                "from CarBody", CarBody.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<CarBody>
     */
    public Optional<CarBody> findCarBodyById(int id) {
        Optional<CarBody> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from CarBody as cb where cb.id = :fId", CarBody.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }
}
