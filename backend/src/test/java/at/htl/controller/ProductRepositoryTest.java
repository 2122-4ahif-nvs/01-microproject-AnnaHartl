package at.htl.controller;

import at.htl.entity.Product;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class ProductRepositoryTest {

    @Inject
    ProductRepository productRepository;

    @Test
    void findByName() {
        Product p = productRepository.findByName("Efeu");
        assertThat(p.getName(), is(equalTo("Efeu")));
    }

    @Test
    void groupByInitial() {
        Map<Character, Integer> map = productRepository.countByInitial();
        for (Map.Entry<Character, Integer> e: map.entrySet()) {
            System.out.printf("%s %d \n", e.getKey(), e.getValue());
        }
    }

    @Test
    void deleteProduct() {
        productRepository.delete(1L);
    }

    @Test
    void updatedProduct() {
        Product p = new Product("Update", 345, "tss", 20.0);
        productRepository.update(2L, p);
    }
}
