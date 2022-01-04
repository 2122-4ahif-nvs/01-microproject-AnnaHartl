package at.htl;

import at.htl.entity.Product;
import io.quarkus.qute.TemplateExtension;

@TemplateExtension
public class TemplateExtensions {

    public static double discountedPrice(Product item) {
        return item.price * 0.9;
    }
}
