package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import ru.job4j.model.Brand;
import ru.job4j.model.Model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final ModelRepository modelRepository = new ModelRepository(crudRepository);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);

    @Test
    public void whenCreateAndFindModelThenDelete() throws Exception {
        Model model = new Model();
        model.setName("fielder1");
        Brand brand = new Brand();
        brand.setName("toyota1");
        model.setBrand(brand);
        boolean result = modelRepository.create(model);
        assertTrue(result);
        Model modelDb = modelRepository.findModelById(model.getId()).get();
        assertEquals(modelDb.getBrand(), model.getBrand());
        modelRepository.delete(model);
    }

    @Test
    public void whenUpdateModelThenDelete() throws Exception {
        Model model = new Model();
        model.setName("1");
        Brand brand = new Brand();
        brand.setName("toyota");
        model.setBrand(brand);
        modelRepository.create(model);
        model.setName("2");
        modelRepository.update(model);
        Model modelDb = modelRepository.findModelById(model.getId()).get();
        assertEquals(modelDb.getName(), "2");
        modelRepository.delete(model);
    }

    @Test
    public void whenFindAllModelThenDelete() throws Exception {
        Brand brand = new Brand();
        brand.setName("toyota");
        Model model1 = new Model();
        model1.setName("1");
        model1.setBrand(brand);
        modelRepository.create(model1);
        Model model2 = new Model();
        model2.setName("2");
        model2.setBrand(brand);
        modelRepository.create(model2);
        List<String> modelNamesDb = modelRepository.findAllModel()
                .stream().map(m -> m.getName()).collect(Collectors.toList());
        assertEquals(Arrays.asList("1", "2"), modelNamesDb);
        model1.setBrand(null);
        model2.setBrand(null);
        modelRepository.delete(model1);
        modelRepository.delete(model2);
        brandRepository.delete(brand.getId());
    }


}
