package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class CrudRepository {

    private final SessionFactory sf;

    /**
     * Стандарнтый метод обертка для
     * типовых операций CRUD в одну команду
     * (.save(user), .update(user))
     * @param command
     */
    public void run(Consumer<Session> command) {
        tx(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    /**
     * Перегруженый метод run со строковым запросом
     * и картой устанавлеваемых параметров
     * (.setParameter("fId", userId))
     * @param query
     * @param args
     */
    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sq = session
                    .createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    /**
     * Метод описывает абстрактную операцию с:
     * 1. запросом
     * 2. инстансом класса.
     * @param query
     * @param cl
     * @param <T>
     * @return List<T>
     */
    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .list();
        return tx(command);
    }

    /**
     * Метод абстрактной операции с:
     * 1. запросом
     * 2. инстансом класса
     * 3. картой устанавливаемых параметров.
     * Возвращает Optional<T>
     * @param query
     * @param cl
     * @param args
     * @param <T>
     * @return <T> Optional<T>
     */
    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return Optional.ofNullable(sq.getSingleResult());
        };
        return tx(command);
    }

    /**
     * Метод абстрактной операции с:
     * 1. запросом
     * 2. инстансом класса
     * 3. картой устанавливаемых параметров.
     * Возвращает List<T>
     * @param query
     * @param cl
     * @param args
     * @param <T>
     * @return <T> List<T>
     */
    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            sq.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
            return sq.list();
        };
        return tx(command);
    }

    /**
     * Главный метод в классе.
     * Метод открывает сессию,
     * начинает транзакцию,
     * выполняет абстрактную операцию
     * @param command
     * @param <T>
     * @return <T>T
     */
    public <T> T tx(Function<Session, T> command) {
        var session = sf.openSession();
        try (session) {
            var tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            var tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

}
