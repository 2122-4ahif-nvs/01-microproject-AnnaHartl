package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InvoiceItem extends PanacheEntityBase {

    //Embeded id
    @EmbeddedId
    InvoiceItemId id;

    int amount;
}
