package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Car;
import ru.job4j.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    /**
     * Добавление модели в контекст персистенции.
     * @param car
     * @return boolean
     */
    public boolean create(Car car) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(car));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить в car базе
     *
     * @param car
     */
    public boolean update(Car car) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(car));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Удаление модели.
     *
     * @param car
     * @return boolean
     */
    public boolean delete(Car car) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.delete(car));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Удаление модели по id,
     * без удаления внутрених
     * сущностей.
     *
     * @return boolean
     */
    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE Car WHERE id = :fId",
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
     *
     * @return List<Task>
     */
    public List<Car> findAll() {
        return crudRepository.query(
                "from Car fetch all properties order by id asc", Car.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Engine>
     */
    public Optional<Car> findCarById(int id) {
        Optional<Car> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Car fetch all properties where id = :fId", Car.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }


}
