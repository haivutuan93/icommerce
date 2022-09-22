package com.nab.icommerce.controller;

import com.nab.icommerce.entity.Cart;
import com.nab.icommerce.entity.PurchaseOrder;
import com.nab.icommerce.mapper.CartMapper;
import com.nab.icommerce.model.CartAddProductRequest;
import com.nab.icommerce.model.CartConfirmRequest;
import com.nab.icommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final HttpServletRequest httpServletRequest;

    public ShoppingCartController(ShoppingCartService shoppingCartService, HttpServletRequest httpServletRequest) {
        this.shoppingCartService = shoppingCartService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping(value = "/add-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> addProductToCart(@RequestBody @Valid CartAddProductRequest request){
        Cart cart = shoppingCartService.addProductToCart(request, httpServletRequest.getHeader("username"));
        return ResponseEntity.ok(cart);
    }

    @PostMapping(value = "/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PurchaseOrder> confirmCartAndMakeOrder(@RequestBody @Valid CartConfirmRequest request){
        return ResponseEntity.ok(shoppingCartService.confirmCartAndMakeOrder(request, httpServletRequest.getHeader("username")));
    }
}
