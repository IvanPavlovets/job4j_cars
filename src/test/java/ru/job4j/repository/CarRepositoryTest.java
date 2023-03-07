package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import ru.job4j.model.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final ModelRepository modelRepository = new ModelRepository(crudRepository);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);
    private final CarBodyRepository carBodyRepository = new CarBodyRepository(crudRepository);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);

    @Test
    public void whenCreateAndFindCarThenDelete() throws Exception {
        Model model = new Model();
        model.setName("fielder");
        Brand brand = new Brand();
        brand.setName("toyota");
        model.setBrand(brand);
        CarBody body = new CarBody();
        body.setName("station wagon");
        Engine engine = new Engine();
        engine.setName("1ZZ-FE");
        Driver driver = new Driver();
        driver.setName("Ivan");
        Car car = new Car();
        car.setDescription("my car");
        car.setPhoto(new byte[]{1});
        car.setModel(model);
        car.setCarBody(body);
        car.setEngine(engine);
        driverRepository.create(driver);
        car.setDrivers(Set.of(driver));
        boolean result = carRepository.create(car);
        assertTrue(result);
        Car found = carRepository.findCarById(car.getId()).get();
        assertNotNull(found);
        carRepository.delete(found);
        driverRepository.delete(driver.getId());
    }

    @Test
    public void whenFindAllCarsThenDeleteById() throws Exception {
        Model model1 = new Model();
        model1.setName("fielder");
        Model model2 = new Model();
        model2.setName("corolla");
        Brand brand = new Brand();
        brand.setName("toyota");
        model1.setBrand(brand);
        model2.setBrand(brand);
        CarBody body1 = new CarBody();
        body1.setName("station wagon");
        CarBody body2 = new CarBody();
        body2.setName("sedan");
        Engine engine = new Engine();
        engine.setName("1ZZ-FE");
        Driver driver = new Driver();
        driver.setName("Ivan");
        Car car1 = new Car();
        car1.setDescription("my car1");
        car1.setPhoto(new byte[]{1});
        car1.setModel(model1);
        car1.setCarBody(body1);
        car1.setEngine(engine);
        driverRepository.create(driver);
        car1.setDrivers(Set.of(driver));
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setDescription("my car2");
        car2.setPhoto(new byte[]{1});
        car2.setModel(model2);
        car2.setCarBody(body2);
        car2.setEngine(engine);
        driverRepository.create(driver);
        car2.setDrivers(Set.of(driver));
        carRepository.create(car2);
        List<Car> found = carRepository.findAll();
        assertNotNull(found);
        carRepository.delete(car1.getId());
        carRepository.delete(car2.getId());
        model1.setBrand(null);
        model2.setBrand(null);
        modelRepository.delete(model1);
        modelRepository.delete(model2);
        carBodyRepository.delete(body1.getId());
        carBodyRepository.delete(body2.getId());
        engineRepository.delete(engine.getId());
        for (Driver d : driverRepository.findAllDrivers()) {
            driverRepository.delete(d.getId());
        }
        brandRepository.delete(brand.getId());
    }


}
