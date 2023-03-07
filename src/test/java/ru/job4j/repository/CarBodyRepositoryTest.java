package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import ru.job4j.model.CarBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarBodyRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CarBodyRepository carBodyRepository = new CarBodyRepository(crudRepository);

    @Test
    public void whenCreateAndFindCarBodyThenDelete() throws Exception {
        CarBody carBody = new CarBody();
        carBody.setName("1");
        boolean result = carBodyRepository.create(carBody);
        assertTrue(result);
        CarBody carBodyDb = carBodyRepository.findCarBodyById(carBody.getId()).get();
        assertEquals(carBodyDb, carBody);
        carBodyRepository.delete(carBody.getId());
    }

    @Test
    public void whenUpdateCarBodyThenDelete() throws Exception {
        CarBody carBody = new CarBody();
        carBody.setName("1");
        carBodyRepository.create(carBody);
        carBody.setName("2");
        carBodyRepository.update(carBody);
        CarBody carBodyDb = carBodyRepository.findCarBodyById(carBody.getId()).get();
        assertEquals(carBodyDb.getName(), "2");
        carBodyRepository.delete(carBody.getId());
    }

    @Test
    public void whenFindAllCarBodyThenDelete() throws Exception {
        CarBody carBody1 = new CarBody();
        carBody1.setName("1");
        carBodyRepository.create(carBody1);
        CarBody carBody2 = new CarBody();
        carBody2.setName("2");
        carBodyRepository.create(carBody2);
        List<String> carBodyNamesDb = carBodyRepository.findAllCarBody()
                .stream().map(cb -> cb.getName()).collect(Collectors.toList());
        assertEquals(Arrays.asList("1", "2"), carBodyNamesDb);
        carBodyRepository.delete(carBody1.getId());
        carBodyRepository.delete(carBody2.getId());
    }

}
