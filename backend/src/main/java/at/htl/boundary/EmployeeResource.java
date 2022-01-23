package at.htl.boundary;

import at.htl.EmployeeRequest;
import at.htl.EmployeeSeeker;
import at.htl.Greeter;
import at.htl.HelloRequest;
import at.htl.controller.EmployeeRepository;
import at.htl.controller.ProductRepository;
import at.htl.entity.Employee;
import at.htl.entity.Product;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.print.Book;
import java.util.List;

@RolesAllowed("employee")
@Path("/employee")
public class EmployeeResource {

    @Inject
    EmployeeRepository repo;

    @GET
    @Path("/getAll")
    public List<Employee> getAll() {
        return repo.listAll();
    }

    @POST
    @Path("/add")
    @Transactional
    public void AddEmp( Employee newEmp) {
        repo.addEmp(newEmp);
    }


    @GrpcClient
    Greeter hello;

    @GET
    @Path("hello/{name}")
    public Uni<String> hello(@PathParam("name") String name) {
        return hello.sayHello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform(helloReply -> helloReply.getMessage());
    }

    @GrpcClient
    EmployeeSeeker seeker;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Uni<Employee> getEmployee(@PathParam("id") int id) {
        return seeker.getEmp(EmployeeRequest.newBuilder().setId(id).build())
                .onItem().transform(reply ->
                        new Employee(
                                reply.getFirstName(),
                                reply.getLastName(),
                                reply.getSalary()));
    }

}
