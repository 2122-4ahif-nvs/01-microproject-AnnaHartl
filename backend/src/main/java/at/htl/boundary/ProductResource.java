package at.htl.boundary;

import at.htl.controller.ProductRepository;
import at.htl.entity.Product;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Path("/product")
public class ProductResource {

    @Inject
    ProductRepository repo;

    @GET
    @Path("/getAll")
    public List<Product> getAll() {
        return repo.findAll();
    }


    @GET
    @Path("/getProduct/{id}")
    public Product getProduct(@PathParam("id") long id) {
        return repo.findProduct(id);
    }

}
