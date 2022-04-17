package at.htl.controller;

import at.htl.entity.Employee;
import at.htl.entity.Product;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EmployeeRepositoryTest {
    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    AgroalDataSource ds;

    private Table employeeTable;

    @BeforeEach
    private void before() {
        employeeTable = new Table(ds, "Gar_Employee");
        employeeRepository.deleteAllEmployees();
    }

    private Employee addDummyEmployee(){
        Employee e = new Employee("Sophie", "Gernu", 1200);
        employeeRepository.addEmp(e);
        return e;
    }

    @Test
    void AddEmployeeSuccess() {
        Employee e = new Employee("Sophie", "Gernu", 1200);
        employeeRepository.addEmp(e);

        org.assertj.db.api.Assertions.assertThat(employeeTable).hasNumberOfRows(1);

        org.assertj.db.api.Assertions.assertThat(employeeTable)
                .column("e_id").value().isEqualTo(e.id)
                .column("e_firstname").value().isEqualTo(e.firstName)
                .column("e_lastName").value().isEqualTo(e.lastName)
                .column("e_salary").value().isEqualTo(e.salary);
    }

    @Test
    void UpdateEmployeeSuccess() {
        Employee e = addDummyEmployee();
        e.salary = e.salary+100;
        employeeRepository.updateEmployee(e);

        org.assertj.db.api.Assertions.assertThat(employeeTable).hasNumberOfRows(1);

        org.assertj.db.api.Assertions.assertThat(employeeTable)
                .column("e_firstname").value().isEqualTo(e.firstName)
                .column("e_lastName").value().isEqualTo(e.lastName)
                .column("e_salary").value().isEqualTo(e.salary);
    }

    @Test
    void DeleteEmployeeSuccess() {
        Employee e = addDummyEmployee();

        employeeTable = new Table(ds, "Gar_Employee");
        org.assertj.db.api.Assertions.assertThat(employeeTable).hasNumberOfRows(1);

        employeeRepository.deleteEmployee(e.id);

        employeeTable = new Table(ds, "Gar_Employee");
        org.assertj.db.api.Assertions.assertThat(employeeTable).hasNumberOfRows(0);
    }

    @Test
    void GetAllEmployeeSuccess() {
        addDummyEmployee();
        addDummyEmployee();

        org.assertj.db.api.Assertions.assertThat(employeeTable)
                .hasNumberOfRows(employeeRepository.findAll().list().size());
    }

    @Test
    void validateFailFirstName() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            Employee emp = new Employee("", "Serl", 1200);
            employeeRepository.addEmp(emp);
        });
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("darf nicht leer sein", exception.getConstraintViolations().iterator().next().getMessage());
    }

    @Test
    void validateFailLastName() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            Employee emp = new Employee("Ferl", "", 1200);
            employeeRepository.addEmp(emp);
        });
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("Lastname should not be blank", exception.getConstraintViolations().iterator().next().getMessage());
    }


    @Test
    void validateFailSalary() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            Employee emp = new Employee("Ferl", "Serl", 200);
            employeeRepository.addEmp(emp);
        });
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("muss größer-gleich 500 sein", exception.getConstraintViolations().iterator().next().getMessage());
    }
}
