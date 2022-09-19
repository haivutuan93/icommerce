package com.nab.icommerce.mapper;

import com.nab.icommerce.entity.Category;
import com.nab.icommerce.entity.Colour;
import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.mongodb.ProductInformation;
import com.nab.icommerce.repository.mongodb.ProductInformationRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductMapper {

    private final ProductInformationRepository productInformationRepository;

    public ProductMapper(ProductInformationRepository productInformationRepository) {
        this.productInformationRepository = productInformationRepository;
    }

    public ProductInformation mapToProductInformation(Product product){
        ProductInformation productInfo;
        var productInfoOpt = productInformationRepository.findById(product.getId());
        if(!productInfoOpt.isPresent()){
            productInfo = new ProductInformation();
        } else {
            productInfo = productInfoOpt.get();
        }

        productInfo.setId(product.getId());
        productInfo.setName(product.getName());
        productInfo.setTitle(product.getTitle());
        productInfo.setDescription(product.getDescription());
        productInfo.setPrice(product.getPrice());
        productInfo.setQuantity(product.getQuantity());
        productInfo.setBrand(product.getBrand() != null ? product.getBrand().getName() : null);

        Set<Category> categories = product.getCategories();
        if(categories != null && !categories.isEmpty()){
            productInfo.setCategories(categories.stream().map(Category::getName).collect(Collectors.toSet()));
        }

        Set<Colour> colours = product.getColours();
        if(colours != null && !colours.isEmpty()){
            productInfo.setColours(colours.stream().map(Colour::getName).collect(Collectors.toSet()));
        }

//        productInfo.setCreatedAt(product.getCreatedAt());
//        productInfo.setUpdatedAt(product.getUpdatedAt());
//        productInfo.setCreatedBy(product.getCreatedBy());
//        productInfo.setUpdatedBy(product.getUpdatedBy());

        return productInfo;
    }
}
