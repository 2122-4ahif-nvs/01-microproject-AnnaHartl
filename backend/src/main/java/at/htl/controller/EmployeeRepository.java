package at.htl.controller;

import at.htl.entity.Employee;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    @Transactional
    public void addEmp(@Valid Employee newEmp) {
        this.persist(newEmp);
    }

    @Transactional
    public void deleteAllEmployees(){
        this.deleteAll();
    }

    @Transactional
    public void updateEmployee(Employee e) {
        Employee empl = findById(e.id);
        empl.firstName = e.firstName;
        empl.lastName = e.lastName;
        empl.salary = e.salary;
        persist(empl);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        deleteById(id);
    }
}
