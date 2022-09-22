package com.nab.icommerce.controller;

import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.mongodb.ProductInformation;
import com.nab.icommerce.model.ProductChangeRequest;
import com.nab.icommerce.model.ProductFilterRequest;
import com.nab.icommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductChangeRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductInformation>> filterProduct(@RequestBody @Valid ProductFilterRequest request){
        return ResponseEntity.ok(productService.filterProduct(request));
    }

}
