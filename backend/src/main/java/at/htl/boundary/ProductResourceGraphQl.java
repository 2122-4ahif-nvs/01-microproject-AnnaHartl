package at.htl.boundary;

import at.htl.controller.ProductRepository;
import at.htl.entity.Product;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class ProductResourceGraphQl {
    @Inject
    ProductRepository repo;

    @Query()
    @Description("Get all Products")
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

}
