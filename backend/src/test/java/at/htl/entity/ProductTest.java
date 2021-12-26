package at.htl.entity;

import at.htl.controller.ProductRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@QuarkusTest
class ProductTest {
    @Inject
    EntityManager em;

    @Inject
    ProductRepository repo;

    @Inject
    Logger logger;

    @Test
    @Transactional
    void createProduct() {
        Product newProduct = new Product(
                "Sonnenblume", 1,"Sonne, Licht, Wasser", 10);
        em.persist(newProduct);

        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> products = query.getResultList();
        logger.info(products);

        assertThat(products.size(), is(equalTo(1)));

    }

    @Test
    void createProductWithRepo() {
        Product newProduct = new Product(
                "Kresse", 4,"Feuchtes Tuch", 1.5);

        repo.save(newProduct);

        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> products = query.getResultList();
        logger.info(products);

        assertThat(products.size(), is(equalTo(1)));
    }

    @BeforeEach
    @Transactional
    void setUp() {
        em.createQuery("DELETE FROM Product p").executeUpdate();
    }
}
