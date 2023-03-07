package ru.job4j.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Repository
@AllArgsConstructor
public class ModelRepository {

    private static final String HQL_MODEL = new StringBuilder()
            .append("select distinct m from Model m ")
            .append("left join fetch m.brand")
            .toString();

    private final CrudRepository crudRepository;


    /**
     * Добавление модели в контекст персистенции.
     * @param model
     * @return boolean
     */
    public boolean create(Model model) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.save(model));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /**
     * Обновить.
     *
     * @param model
     */
    public boolean update(Model model) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(model));
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
    public boolean delete(Model model) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.delete(model));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }


    /**
     * Достает все значения из хранилища (БД)
     * @return List<Model>
     */
    public List<Model> findAllModel() {
        return crudRepository.query(
                HQL_MODEL + " order by m.id asc", Model.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Model>
     */
    public Optional<Model> findModelById(int id) {
        Optional<Model> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    HQL_MODEL + " where m.id = :fId", Model.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

}
