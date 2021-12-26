package at.htl.controller;

import at.htl.entity.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {


}
