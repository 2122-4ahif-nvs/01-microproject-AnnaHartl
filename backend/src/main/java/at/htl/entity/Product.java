package at.htl.entity;

import org.jboss.resteasy.annotations.Form;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
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
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("product_id")
    Long id;

    @Column(name = "name", unique = true)
    String name;
    String description;
    double price;
    int stock;

    public Product(String name, int stock, String description, double price) {
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
