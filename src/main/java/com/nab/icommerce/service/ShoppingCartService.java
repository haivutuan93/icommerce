package com.nab.icommerce.service;

import com.nab.icommerce.entity.Cart;
import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.PurchaseOrder;
import com.nab.icommerce.entity.User;
import com.nab.icommerce.exception.APIException;
import com.nab.icommerce.model.CartAddProductRequest;
import com.nab.icommerce.model.CartConfirmRequest;
import com.nab.icommerce.repository.CartRepository;
import com.nab.icommerce.repository.ProductRepository;
import com.nab.icommerce.repository.PurchaseOrderRepository;
import com.nab.icommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.nab.icommerce.exception.ErrorConstant.*;
import static com.nab.icommerce.util.Constants.*;

@Service
@Slf4j
public class ShoppingCartService {
    private final CartRepository cartRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RabbitMQSender rabbitMQSender;

    public ShoppingCartService(CartRepository cartRepository, PurchaseOrderRepository purchaseOrderRepository, ProductRepository productRepository, UserRepository userRepository, RabbitMQSender rabbitMQSender) {
        this.cartRepository = cartRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Transactional
    public Cart addProductToCart(CartAddProductRequest request, String username){
        log.info("Add product to Cart: productId: {}, cartId: {}, username: {}", request.getProductId(), request.getCartId(), username);
        Cart cart;
        if(request.getCartId() == null){
            cart = new Cart();
            cart.setUser(userRepository.findByUsername(username).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG)));
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
        Cart result = cartRepository.save(cart);
        rabbitMQSender.send(product);

        return result;
    }

    @Transactional
    public PurchaseOrder confirmCartAndMakeOrder(CartConfirmRequest request, String username){
        log.info("Confirm cart to make new order for cartId:{}", request.getCartId());
        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new APIException(ERR_PARAMETER_NOT_CORRECT, ERR_PARAMETER_NOT_CORRECT_MSG));
        if(!cart.getUser().getId().equals(user.getId())){
            throw new APIException(ERR_CART_USER_NOT_MATCH, ERR_CART_USER_NOT_MATCH_MSG);
        }

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
