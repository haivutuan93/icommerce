package com.nab.icommerce.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddProductRequest {
    Long cartId;

    @NotNull
    Long productId;
}
