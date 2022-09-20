package com.nab.icommerce.controller;

import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.mongodb.ProductInformation;
import com.nab.icommerce.model.ProductChangeRequest;
import com.nab.icommerce.model.ProductFilterRequest;
import com.nab.icommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody @Valid ProductChangeRequest request) {
        return productService.addProduct(request);
    }

    @GetMapping("/filter")
    public List<ProductInformation> filterProduct(@RequestBody @Valid ProductFilterRequest request){
        return productService.filterProduct(request);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getProduct(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/test")
    public ResponseEntity<String> testProduct(){
        return ResponseEntity.ok("Done");
    }
}
