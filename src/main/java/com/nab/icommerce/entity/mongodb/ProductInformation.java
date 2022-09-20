package com.nab.icommerce.entity.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "product_information")
@Data
public class ProductInformation {
    @Id
    private Long id;

    @Indexed
    private String name;
    private String title;
    private String description;
    @Indexed
    Float price;
    Integer quantity;

    @Indexed
    private String brand;
    @Indexed
    private Set<String> categories;
    @Indexed
    private Set<String> colours;

}