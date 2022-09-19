package com.nab.icommerce.controller;

import com.nab.icommerce.entity.Cart;
import com.nab.icommerce.entity.PurchaseOrder;
import com.nab.icommerce.model.CartAddProductRequest;
import com.nab.icommerce.model.CartConfirmRequest;
import com.nab.icommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add-product")
    public Cart addProductToCart(@RequestBody @Valid CartAddProductRequest request){
        return shoppingCartService.addProductToCart(request, request.getUserId());
    }

    @PostMapping("/confirm")
    public PurchaseOrder confirmCartAndMakeOrder(@RequestBody @Valid CartConfirmRequest request){
        return shoppingCartService.confirmCartAndMakeOrder(request);
    }
}
