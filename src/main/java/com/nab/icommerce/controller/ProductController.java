package com.nab.icommerce.controller;

import com.nab.icommerce.entity.Brand;
import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.mongodb.ProductInformation;
import com.nab.icommerce.model.ProductChangeRequest;
import com.nab.icommerce.model.ProductFilterRequest;
import com.nab.icommerce.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    public Product addProduct(@RequestBody @Valid ProductChangeRequest request) {
        return productService.addProduct(request);
    }

    @GetMapping("/filter")
    public List<ProductInformation> filterProduct(@RequestBody @Valid ProductFilterRequest request){
        return productService.filterProduct(request);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getProduct(){
        log.info("get");
        return ResponseEntity.ok(productService.getAll());
    }
    @GetMapping("/get2")
    public ResponseEntity<Brand> getBrand(){
        log.info("get2");
        Brand brand = new Brand();
        brand.setName("brand");
        brand.setTitle("title");
        return ResponseEntity.ok(brand);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testProduct(){
        log.info("test");
        return ResponseEntity.ok("Done");
    }
}
