package at.htl.controller;

import at.htl.entity.Employee;
import at.htl.entity.Product;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

import org.assertj.db.type.Table;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ProductRepositoryTest {

    @Inject
    ProductRepository productRepository;

    @Inject
    AgroalDataSource ds;

    private Table productTable;

    @BeforeEach
    private void before() {
        productTable = new Table(ds, "Gar_Product");
        productRepository.deleteAllProducts();
    }

    private Product addDummyProduct(String uniqueString){
        Product p =
                new Product("Rosenranken"+uniqueString, 3, "Blühen im Frühling", 20.5);
        productRepository.add(p);
        return p;
    }

    private Product addDummyProduct(){
        return addDummyProduct("");
    }

    @Test
    void AddProductSuccess() {
        Product p =
                new Product("Orangen", 3, "Blühen im Frühling", 20.5);
        productRepository.add(p);

        org.assertj.db.api.Assertions.assertThat(productTable).hasNumberOfRows(1);

        org.assertj.db.api.Assertions.assertThat(productTable)
                .column("p_id").value().isEqualTo(p.id)
                .column("p_name").value().isEqualTo(p.name)
                .column("P_stock").value().isEqualTo(p.stock)
                .column("P_description").value().isEqualTo(p.description)
                .column("p_price").value().isEqualTo(p.price);
    }


    @Test
    void UpdateProductSuccess() {
        Product p = addDummyProduct();
        p.stock = 20;
        productRepository.update(p);

        org.assertj.db.api.Assertions.assertThat(productTable).hasNumberOfRows(1);

        org.assertj.db.api.Assertions.assertThat(productTable)
                .column("p_name").value().isEqualTo(p.name)
                .column("P_stock").value().isEqualTo(p.stock)
                .column("P_description").value().isEqualTo(p.description)
                .column("p_price").value().isEqualTo(p.price);
    }

    @Test
    void DeleteProductSuccess() {
        Product p = addDummyProduct();

        org.assertj.db.api.Assertions.assertThat(productTable).hasNumberOfRows(1);

        productRepository.delete(p.id);

        productTable = new Table(ds, "Gar_Product");
        org.assertj.db.api.Assertions.assertThat(productTable).hasNumberOfRows(0);
    }

    @Test
    void GetAllProductsSuccess() {
        addDummyProduct();
        addDummyProduct("1");

        org.assertj.db.api.Assertions.assertThat(productTable)
                .hasNumberOfRows(productRepository.findAllProducts().size());
    }

    @Test
    void findProductByIdExists() {
        Product prod = addDummyProduct();
        addDummyProduct("1");

        Product p = productRepository.findProduct(prod.id);
        assertEquals(prod.id, p.id);
        assertEquals(prod.description, p.description);
        assertEquals(prod.price, p.price);
        assertEquals(prod.stock, p.stock);
    }

    @Test
    void findProductByIdNotExists() {
        Product prod = addDummyProduct();
        addDummyProduct("1");

        Product p = productRepository.findProduct(-1);
        assertNull(p);
    }

    @Test
    void findProductByNameNotExists() {
        Product prod = addDummyProduct();
        addDummyProduct("1");

        Exception exception = assertThrows(NoResultException.class, () -> {
            Product p = productRepository.findByName("");
        });

        assertEquals("No entity found for query", exception.getMessage() );
    }

    @Test
    void findProductByNameExists() {
        Product prod = addDummyProduct();
        addDummyProduct("2");

        Product p = productRepository.findByName(prod.name);
        assertEquals(prod.id, p.id);
        assertEquals(prod.description, p.description);
        assertEquals(prod.price, p.price);
        assertEquals(prod.stock, p.stock);
    }

    @Test
    void countByInitialSuccess() {
        Product prod = addDummyProduct();
        addDummyProduct("2");
        addDummyProduct("1");

        Map<Character, Integer> initial = productRepository.countByInitial();
        assertEquals(1, initial.size());
        assertEquals(3, initial.get('R'));

        for (Map.Entry<Character, Integer> entry: initial.entrySet()) {
            System.out.println(entry.getKey() +"  "+ entry.getValue());
        }
    }

    @Test
    void validateNoBadWords(){
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            Product p = new Product("Böse Pflanze", 2, "bad", 12.99);
            productRepository.add(p);
        });
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("This is a Bad Word", exception.getConstraintViolations().iterator().next().getMessage());
    }
}
