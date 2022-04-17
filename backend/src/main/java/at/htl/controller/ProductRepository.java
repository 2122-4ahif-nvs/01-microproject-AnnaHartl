package at.htl.controller;

import at.htl.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    @Inject
    EntityManager em;

    @Transactional
    public void save(@Valid Product p){
        em.persist(p);
    }

    public Product findProduct(long id)
    {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> products = query.getResultList();

        for (Product p: products) {
            if(p.id == id)
                return p;
        }
        return null;
    }

    public List<Product> findAllProducts()
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


    @Transactional
    public void delete(Long id){
        Product p = findProduct(id);
        em.remove(p);
    }

    @Transactional
    public void deleteAllProducts(){
        em.createQuery("DELETE FROM Product").executeUpdate();
    }

    @Transactional
    public void add(@Valid Product p){
        persist(p);
    }

    @Transactional
    public void update(Product p){
        Product prod = findById(p.id);
        prod.stock = p.stock;
        prod.description = p.description;
        prod.price = p.price;
        prod.name = p.name;
        persist(prod);
    }

}
