package at.htl.entity;

import at.htl.NoBadWordsConstraint;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

@Entity
@Table(name = "GAR_PRODUCT")
@NamedQueries({
        @NamedQuery(
                name = "Product.findAll",
                query = "Select p from Product p"
        ),
        @NamedQuery(
                name = "Product.findByName",
                query = "Select p from Product p where p.name = :name"
        ),
        @NamedQuery(
                name = "Product.countByInitial",
                query = "Select substring(p.name,1,1), count(p) from Product p group by substring(p.name , 1,1)"
        )
}
)
public class Product extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("product_id")
    @Column(name = "P_ID")
    public Long id;

    @Column(name = "P_name", unique = true)
    public String name;

    @NoBadWordsConstraint
    @Column(name = "P_description")
    public String description;
    @Column(name = "P_price")
    public double price;
    @Column(name = "P_stock")
    public int stock;

    public Product(String name, int stock, String description, double price) {
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
