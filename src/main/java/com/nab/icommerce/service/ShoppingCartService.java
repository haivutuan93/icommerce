package com.nab.icommerce.service;

import com.nab.icommerce.entity.Cart;
import com.nab.icommerce.entity.PurchaseOrder;
import com.nab.icommerce.entity.Product;
import com.nab.icommerce.exception.APIException;
import com.nab.icommerce.model.CartAddProductRequest;
import com.nab.icommerce.model.CartConfirmRequest;
import com.nab.icommerce.repository.CartRepository;
import com.nab.icommerce.repository.PurchaseOrderRepository;
import com.nab.icommerce.repository.ProductRepository;
import com.nab.icommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.nab.icommerce.exception.ErrorConstant.*;
import static com.nab.icommerce.util.Constants.*;

@Service
public class ShoppingCartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Cart addProductToCart(CartAddProductRequest request, Long userId){
        Cart cart;
        if(request.getCartId() == null){
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG)));
            cart.setStatus(CART_STATUS_IN_PROGRESS);
        } else {
            cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG));
        }
        List<Product> cartProducts = cart.getProducts();
        if(cartProducts == null){
            cartProducts = new ArrayList<>();
        }

        Product product = validateAndGetProduct(request.getProductId());
        product.setQuantity(product.getQuantity() - 1);

        cartProducts.add(product);
        cart.setAmount(cartProducts.stream().mapToDouble(Product::getPrice).sum());
        return cartRepository.save(cart);
    }

    @Transactional
    public PurchaseOrder confirmCartAndMakeOrder(CartConfirmRequest request){
        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG));
        if(!CART_STATUS_IN_PROGRESS.equals(cart.getStatus())){
            throw new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG);
        }
        cart.setStatus(CART_STATUS_COMPLETED);

        PurchaseOrder order = new PurchaseOrder();
        order.setUser(cart.getUser());
        order.setStatus(ORDER_STATUS_IN_PROGRESS);
        order.setCart(cart);
        return purchaseOrderRepository.save(order);
    }

    private Product validateAndGetProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG));

        if(product.getQuantity() != null && product.getQuantity() <= 0){
            throw new APIException(ERR_PRODUCT_QUANTITY_IS_NOT_ENOUGH, ERR_PRODUCT_QUANTITY_IS_NOT_ENOUGH_MSG);
        }

        return product;
    }
}
