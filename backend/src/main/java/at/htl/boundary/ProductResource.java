package at.htl.boundary;

import at.htl.controller.ProductRepository;
import at.htl.entity.Product;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.TEXT_PLAIN)
    public String addProduct(
            @FormParam("name") String name,
            @FormParam("description") String desc,
            @FormParam("price") double price,
            @FormParam("stock") int num
    ) {
        Product p = new Product(name, num, desc,price);
        repo.save(p);
        return p.toString();
    }

}
