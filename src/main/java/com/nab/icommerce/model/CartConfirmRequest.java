package com.nab.icommerce.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartConfirmRequest {
    @NotNull
    Long cartId;
}
