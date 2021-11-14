package at.htl.controller;

import at.htl.entity.Product;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void save(Product p){
        em.persist(p);
    }

    public Product findProduct(long id)
    {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> products = query.getResultList();

        for (Product p: products) {
            if(p.getId() == id)
                return p;
        }
        return null;
    }

    public List<Product> findAll()
    {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        return query.getResultList();
    }
}
