package at.htl.controller;

import at.htl.entity.Product;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Product findByName(String name)
    {
        TypedQuery<Product> query = em.createNamedQuery("Product.findByName", Product.class)
                .setParameter("name", name);
        return query.getSingleResult();
    }

    public Map<Character, Integer> countByInitial()
    {
        Map<Character, Integer> hashMap = new HashMap<>();
        Query query = em.createNamedQuery("Product.countByInitial");
        List<Object[]> list = query.getResultList();
        for (Object[] intial : list) {
            hashMap.put(
                    intial[0].toString().charAt(0),
                    Integer.parseInt(intial[1].toString())
                    );
        }
        return hashMap;
    }
}
