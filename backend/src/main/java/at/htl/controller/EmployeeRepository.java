package at.htl.controller;

import at.htl.entity.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.List;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
    public void addEmp(@Valid Employee newEmp) {
        this.persist(newEmp);
    }
}
