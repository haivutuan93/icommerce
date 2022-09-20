package com.nab.icommerce.controller;

import com.nab.icommerce.entity.Cart;
import com.nab.icommerce.entity.PurchaseOrder;
import com.nab.icommerce.mapper.CartMapper;
import com.nab.icommerce.model.CartAddProductRequest;
import com.nab.icommerce.model.CartConfirmRequest;
import com.nab.icommerce.model.CartResponseModel;
import com.nab.icommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    CartMapper cartMapper;

    @PostMapping(value = "/add-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponseModel> addProductToCart(@RequestBody @Valid CartAddProductRequest request){
        Cart cart = shoppingCartService.addProductToCart(request, request.getUserId());
        return ResponseEntity.ok(cartMapper.mapToCartResponseModel(cart));
    }

    @PostMapping(value = "/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder confirmCartAndMakeOrder(@RequestBody @Valid CartConfirmRequest request){
        return shoppingCartService.confirmCartAndMakeOrder(request);
    }
}
