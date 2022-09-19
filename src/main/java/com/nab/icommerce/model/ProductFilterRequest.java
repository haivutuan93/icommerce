package com.nab.icommerce.model;

import lombok.Data;

@Data
public class ProductFilterRequest {
    String category;
    String name;
    String brand;
    String colour;
    Float priceLessThan;
    Float priceGreaterThan;
}
