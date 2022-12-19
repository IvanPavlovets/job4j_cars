package ru.job4j.repository;

import lombok.AllArgsConstructor;
import ru.job4j.model.Car;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param car car
     * @return car с id.
     */
    public Car create(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    /**
     * Обновить в car базе
     *
     * @param car
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить car по id.
     *
     * @param carId
     */
    public void delete(int carId) {
        crudRepository.run(
                "DELETE Car WHERE id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Достает все значения из хранилища (БД)
     *
     * @return List<Task>
     */
    public List<Car> findAll() {
        return crudRepository.query(
                "select distinct c from Car c left join fetch c.drivers join fetch t.engine order by c.id asc", Car.class
        );
    }


}
