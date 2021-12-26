package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InvoiceItem extends PanacheEntityBase {

    @EmbeddedId
    public InvoiceItemId id;
    public int amount;

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
