package at.htl.boundary;

import at.htl.controller.EmployeeRepository;
import at.htl.controller.ProductRepository;
import at.htl.entity.Employee;
import at.htl.entity.Product;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

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
    public void AddEmp(Employee newEmp) {
        repo.persist(newEmp);
    }
}
