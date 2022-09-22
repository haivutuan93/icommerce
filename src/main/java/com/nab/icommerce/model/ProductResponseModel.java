package com.nab.icommerce.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ProductResponseModel {
    @NotNull
    String name;
    String title;
    String description;
    @NotNull
    String brand;
    @NotNull
    Float price;
    Integer quantity;
}
