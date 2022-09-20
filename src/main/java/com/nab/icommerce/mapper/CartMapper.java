package com.nab.icommerce.mapper;

import com.nab.icommerce.entity.Cart;
import com.nab.icommerce.entity.Product;
import com.nab.icommerce.model.CartResponseModel;
import com.nab.icommerce.model.ProductResponseModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartMapper {

    private final ProductMapper productMapper;

    public CartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartResponseModel mapToCartResponseModel(Cart cart){
        CartResponseModel response = new CartResponseModel();
        response.setId(cart.getId());
        response.setUserId(cart.getUser().getId());
        response.setStatus(cart.getStatus());

        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        List<Product> products = cart.getProducts();
        for (Product product : products){
            productResponseModels.add(productMapper.mapToProductResponseModel(product));
        }
        response.setProducts(productResponseModels);

        response.setAmount(cart.getAmount());
        response.setDiscount(cart.getDiscount());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());
        response.setCreatedBy(cart.getCreatedBy());
        response.setUpdatedBy(cart.getUpdatedBy());

        return response;
    }
}
