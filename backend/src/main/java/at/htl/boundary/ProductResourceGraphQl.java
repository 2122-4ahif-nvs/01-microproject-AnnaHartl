package at.htl.boundary;

import at.htl.controller.ProductRepository;
import at.htl.entity.Product;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class ProductResourceGraphQl {
    @Inject
    ProductRepository repo;

    @Query
    @Description("Get all Products")
    public List<Product> getAllProducts() {
        return repo.findAllProducts();
    }

    @Query
    public Product getProduct(@Name("productId") Long id) {
        return repo.findProduct(id);
    }

    @Mutation
    public Product createProduct(Product newProduct) {
        repo.save(newProduct);
        return newProduct;
    }

    @Mutation
    public Long deleteProduct(Long id) {
        repo.delete(id);
        return id;
    }
}
