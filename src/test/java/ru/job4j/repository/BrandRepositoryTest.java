package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import ru.job4j.model.Brand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BrandRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);

    @Test
    public void whenCreateAndFindBrand() throws Exception {
        Brand brand = new Brand();
        brand.setName("1");
        boolean result = brandRepository.create(brand);
        assertTrue(result);
        Brand brandDb = brandRepository.findBrandById(brand.getId()).get();
        assertEquals(brandDb, brand);
        brandRepository.delete(brand.getId());
        List<Brand> brands = brandRepository.findAllBrand();
        System.out.println(brands);
    }

    @Test
    public void whenUpdateBrand() throws Exception {
        Brand brand = new Brand();
        brand.setName("1");
        brandRepository.create(brand);
        brand.setName("2");
        brandRepository.update(brand);
        Brand brandDb = brandRepository.findBrandById(brand.getId()).get();
        assertEquals(brandDb.getName(), "2");
        brandRepository.delete(brand.getId());
    }

    @Test
    public void whenFindAllBrand() throws Exception {
        Brand brand1 = new Brand();
        brand1.setName("1");
        brandRepository.create(brand1);
        Brand brand2 = new Brand();
        brand2.setName("2");
        brandRepository.create(brand2);
        List<String> brandNamesDb = brandRepository.findAllBrand()
                .stream().map(b -> b.getName()).collect(Collectors.toList());
        assertEquals(Arrays.asList("1", "2"), brandNamesDb);
        brandRepository.delete(brand1.getId());
        brandRepository.delete(brand2.getId());
    }

}
