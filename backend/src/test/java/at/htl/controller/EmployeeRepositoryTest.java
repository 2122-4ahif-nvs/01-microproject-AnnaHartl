package at.htl.controller;

import at.htl.entity.Employee;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EmployeeRepositoryTest {
    @Inject
    EmployeeRepository repo;

    @Test
    void validate_fail_fn() {
        try {
            Employee emp = new Employee("", "Serl", 1200);
            repo.addEmp(emp);
        }catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
            return;
        }
         fail();
    }

    @Test
    void validate_fail_ln() {
        try {
            Employee emp = new Employee("Susi", "", 1200);
            repo.addEmp(emp);
        }catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
            return;
        }
        fail();
    }


    @Test
    void validate_fail_salary() {
        try {
            Employee emp = new Employee("Susi", "Serl", 20);
            repo.addEmp(emp);
        }catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
            return;
        }
        fail();
    }
}
