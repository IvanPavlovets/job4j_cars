package ru.job4j.repository;

import lombok.Data;
import org.hamcrest.core.Is;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.job4j.model.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class PostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);
    private final PostRepository postRepository = new PostRepository(crudRepository);

    @Test
    public void whenCreatePostThenDelete() throws Exception {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory history = new PriceHistory();
        history.setBefore(BigInteger.ONE);
        history.setAfter(BigInteger.ONE);
        history.setCreated(LocalDateTime.now());
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
        Post post = new Post();
        post.setText("car sale");
        post.setUser(user);
        post.setPriceHistories(List.of(history));
        post.setParticipates(Set.of(user));
        post.setCar(car);
        boolean result = postRepository.create(post);
        Assertions.assertTrue(result);
        Post postDb = postRepository.findAll().get(0);
        assertEquals(postDb.getText(), "car sale");
        postRepository.delete(postDb);
        driverRepository.delete(driver.getId());
    }

    @Test
    public void whenFindAllPostsWithPhotoThenDelete() throws Exception {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory history = new PriceHistory();
        history.setBefore(BigInteger.ONE);
        history.setAfter(BigInteger.ONE);
        Brand brand = new Brand();
        brand.setName("toyota");
        Model model = new Model();
        model.setName("fielder");
        model.setBrand(brand);
        CarBody body = new CarBody();
        body.setName("station wagon");
        Engine engine = new Engine();
        engine.setName("1ZZ-FE");
        Driver driver = new Driver();
        driver.setName("Ivan");
        driverRepository.create(driver);
        Car car = new Car();
        car.setDescription("my car");
        car.setPhoto(new byte[]{1});
        car.setModel(model);
        car.setCarBody(body);
        car.setEngine(engine);
        car.setDrivers(Set.of(driver));
        Post post = new Post();
        post.setText("car sale");
        post.setUser(user);
        post.setPriceHistories(List.of(history));
        post.setParticipates(Set.of(user));
        post.setCar(car);
        postRepository.create(post);
        List<Post> postDbList = postRepository.findWithPhoto();
        assertThat(List.of(post), is(postDbList));
        postRepository.delete(postDbList.get(0));
        driverRepository.delete(driver.getId());
    }

    @Test
    public void whenFindByBrandThenDelete() throws Exception {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory history = new PriceHistory();
        history.setBefore(BigInteger.ONE);
        history.setAfter(BigInteger.ONE);
        Brand brand = new Brand();
        brand.setName("toyota");
        Model model = new Model();
        model.setName("fielder");
        model.setBrand(brand);
        CarBody body = new CarBody();
        body.setName("station wagon");
        Engine engine = new Engine();
        engine.setName("1ZZ-FE");
        Driver driver = new Driver();
        driver.setName("Ivan");
        driverRepository.create(driver);
        Car car = new Car();
        car.setDescription("my car");
        car.setPhoto(new byte[]{1});
        car.setModel(model);
        car.setCarBody(body);
        car.setEngine(engine);
        car.setDrivers(Set.of(driver));
        Post post = new Post();
        post.setText("car sale");
        post.setUser(user);
        post.setPriceHistories(List.of(history));
        post.setParticipates(Set.of(user));
        post.setCar(car);
        postRepository.create(post);
        Post postDb = postRepository.findByBrand("toyota").get(0);
        Car carDb = postDb.getCar();
        Model modelDb = carDb.getModel();
        Brand brandDb = modelDb.getBrand();
        assertThat("toyota", is(brandDb.getName()));
        postRepository.delete(postDb);
        driverRepository.delete(driver.getId());
    }

    @Test
    public void whenFindTodayThenDelete() throws Exception {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory history = new PriceHistory();
        history.setBefore(BigInteger.ONE);
        history.setAfter(BigInteger.ONE);
        Brand brand = new Brand();
        brand.setName("toyota");
        Model model = new Model();
        model.setName("fielder");
        model.setBrand(brand);
        CarBody body = new CarBody();
        body.setName("station wagon");
        Engine engine = new Engine();
        engine.setName("1ZZ-FE");
        Driver driver = new Driver();
        driver.setName("Ivan");
        driverRepository.create(driver);
        Car car = new Car();
        car.setDescription("my car");
        car.setPhoto(new byte[]{1});
        car.setModel(model);
        car.setCarBody(body);
        car.setEngine(engine);
        car.setDrivers(Set.of(driver));
        Post post = new Post();
        post.setText("car sale");
        post.setUser(user);
        post.setPriceHistories(List.of(history));
        post.setParticipates(Set.of(user));
        post.setCar(car);
        postRepository.create(post);
        Post postDb = postRepository.findToday().get(0);
        assertTrue((postDb.getCreated()).isAfter(LocalDateTime.now().minusHours(24)));
        postRepository.delete(postDb);
        driverRepository.delete(driver.getId());
    }


}
