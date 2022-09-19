package com.nab.icommerce.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartRemoveProductRequest {
    Long userId;
    @NotNull
    Long cartId;

    @NotNull
    Long productId;
}
