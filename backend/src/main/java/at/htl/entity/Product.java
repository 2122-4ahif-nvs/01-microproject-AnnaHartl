package at.htl.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

@Entity
@NamedQueries(
        @NamedQuery(
                name = "Product.findAll",
                query = "Select p from Product p"
        )
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("product_id")
    Long id;

    String name;
    int productNr;
    String description;
    double price;

    public Product(String name, int productNr, String description, double price) {
        this.name = name;
        this.productNr = productNr;
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

    public int getProductNr() {
        return productNr;
    }

    public void setProductNr(int productNr) {
        this.productNr = productNr;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productNr=" + productNr +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
