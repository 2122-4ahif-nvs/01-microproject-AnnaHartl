package at.htl.controller;

import at.htl.entity.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProductRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void save(Product p){
        em.persist(p);
    }
}
