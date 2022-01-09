package at.htl.controller;

import at.htl.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    @Transactional
    public List<Customer> getAll(){
        return this.listAll();
    }
}
