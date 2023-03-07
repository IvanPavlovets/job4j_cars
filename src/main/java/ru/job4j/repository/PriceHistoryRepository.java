package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class PriceHistoryRepository {

    private final CrudRepository crudRepository;


    /**
     * Добавление модели в контекст персистенции.
     * @param history
     * @return boolean
     */
    public boolean create(PriceHistory history) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(history));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param history пользователь.
     */
    public boolean update(PriceHistory history) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(history));
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
                    "DELETE PriceHistory WHERE id = :fId",
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
     * @return List<PriceHistory>
     */
    public List<PriceHistory> findAllPriceHistory() {
        return crudRepository.query(
                "from PriceHistory", PriceHistory.class
        );
    }


    /**
     * Находит запись в БД по id
     * @param id
     * @return Optional<PriceHistory>
     */
    public Optional<PriceHistory> findPriceHistoryById(int id) {
        Optional<PriceHistory> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from PriceHistory as ph where ph.id = :fId", PriceHistory.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }
}
