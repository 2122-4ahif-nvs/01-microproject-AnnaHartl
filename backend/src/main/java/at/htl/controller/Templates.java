package at.htl.controller;

import at.htl.entity.Product;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Location;
import io.quarkus.qute.TemplateInstance;

import java.util.List;

@CheckedTemplate
public class Templates {
    //Funktioniert nicht
    //@Location("/ProductResource/")
    //public static native TemplateInstance product(Product product);
    //public static native TemplateInstance productList(List<Product> products);
}
