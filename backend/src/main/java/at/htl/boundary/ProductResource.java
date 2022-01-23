package at.htl.boundary;

import at.htl.controller.ProductRepository;
import at.htl.entity.Product;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@PermitAll
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
    public String add(
            @FormParam("name") String name,
            @FormParam("description") String desc,
            @FormParam("price") double price,
            @FormParam("stock") int num
    ) {
        Product p = new Product(name, num, desc,price);
        repo.save(p);
        return p.toString();
    }

    @POST
    @Path("/addProduct")
    public void addProduct(Product p) {
        repo.save(p);
    }


    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance product(Product product);
        public static native TemplateInstance productList(List<Product> products);
    }

    @GET
    @Path("qute/get/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("id") Integer id) {
        return Templates.product(repo.findProduct(id));
    }

    @GET
    @Path("qute/getAll")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getList() {
        return Templates.productList(repo.findAll());
    }

}
