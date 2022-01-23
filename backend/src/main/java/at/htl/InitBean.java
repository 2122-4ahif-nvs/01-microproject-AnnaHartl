package at.htl;


import at.htl.controller.ProductRepository;
import at.htl.entity.Product;
import at.htl.entity.User_Access;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class InitBean {

    @Inject
    ProductRepository productRepository;

    @Transactional
    void init(@Observes StartupEvent event) {
        User_Access.add("employee1", "12345", "employee");
        User_Access.add("employee2", "12345", "employee");
        User_Access.add("admin", "4567", "admin");

        String line = "";
        final String delimiter = ";";
        try
        {
            String fileName = "products.csv";
            Path filePath = Paths.get(fileName);

            BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
            line = reader.readLine();
            line = reader.readLine();
            while (line != null)
            {
                String[] parameter = line.split(delimiter);
                Product p = new Product(parameter[0],Integer.parseInt(parameter[1]), parameter[3],Double.parseDouble(parameter[2]));
                productRepository.save(p);
                System.out.println(parameter[0] + " | "+ parameter[1]+ " | "+ parameter[2]+ " | "+ parameter[3]);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

}
