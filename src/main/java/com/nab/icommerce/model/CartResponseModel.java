package com.nab.icommerce.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "cart")
@Data
public class CartResponseModel {
    Long id;
    Long userId;
    String status;
    List<ProductResponseModel> products = new ArrayList<>();
    Double amount;
    Double discount;
    Timestamp createdAt;
    Timestamp updatedAt;
    String createdBy;
    String updatedBy;
}
