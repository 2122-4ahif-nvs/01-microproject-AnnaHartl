package at.htl.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import java.awt.print.Book;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EmployeeTest {

    //TODO entity test
    @Test
    void createEmployeeValidationFail_01() {
        Employee emp = new Employee("Maria", "", 1200.80);
    }
}
