package com.nab.icommerce.service;

import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.mongodb.ProductInformation;
import com.nab.icommerce.exception.APIException;
import com.nab.icommerce.exception.ErrorConstant;
import com.nab.icommerce.model.ProductChangeRequest;
import com.nab.icommerce.model.ProductFilterRequest;
import com.nab.icommerce.repository.BrandRepository;
import com.nab.icommerce.repository.CategoryRepository;
import com.nab.icommerce.repository.ColourRepository;
import com.nab.icommerce.repository.ProductRepository;
import com.nab.icommerce.repository.mongodb.ProductInformationRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductInformationRepository productInformationRepository;
    private final MongoTemplate mongoTemplate;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ColourRepository colourRepository;
    private final RabbitMQSender rabbitMQSender;

    public ProductService(ProductRepository productRepository, ProductInformationRepository productInformationRepository, MongoTemplate mongoTemplate,
                          BrandRepository brandRepository, CategoryRepository categoryRepository, ColourRepository colourRepository, RabbitMQSender rabbitMQSender) {
        this.productRepository = productRepository;
        this.productInformationRepository = productInformationRepository;
        this.mongoTemplate = mongoTemplate;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.colourRepository = colourRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Transactional
    public Product addProduct(ProductChangeRequest request) {
        var product = new Product();
        product.setName(request.getName());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        validateAndSetForeignData(product, request);

        var productSaved = productRepository.save(product);
        changeProductToMongoDB(productSaved);
        return productSaved;
    }

    public List<ProductInformation> filterProduct(ProductFilterRequest request){
        Query query = new Query();

        if(request.getCategory() != null && !request.getCategory().isBlank()){
            query.addCriteria(Criteria.where("categories").is(request.getCategory()));
        }

        if(request.getName() != null && !request.getName().isBlank()){
            query.addCriteria(Criteria.where("name").is(request.getName()));
        }

        if(request.getBrand() != null && !request.getBrand().isBlank()){
            query.addCriteria(Criteria.where("brand").is(request.getBrand()));
        }

        if(request.getColour() != null && !request.getColour().isBlank()){
            query.addCriteria(Criteria.where("colours").is(request.getColour()));
        }

        if(request.getPriceLessThan() != null && request.getPriceLessThan() > 0){
            query.addCriteria(Criteria.where("price").lt(request.getPriceLessThan()));
        }

        if(request.getPriceGreaterThan() != null && request.getPriceGreaterThan() > 0){
            query.addCriteria(Criteria.where("price").gt(request.getPriceGreaterThan()));
        }

        return mongoTemplate.find(query, ProductInformation.class);

    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    private void changeProductToMongoDB(Product product){
        rabbitMQSender.send(product);
    }

    private Product validateAndSetForeignData(Product product, ProductChangeRequest request){
        var brand = brandRepository.findBrandByName(request.getBrand()).orElseThrow(
                () -> new APIException(ErrorConstant.ERR_PARAMETER_NOT_CORRECT, ErrorConstant.ERR_PARAMETER_NOT_CORRECT_MSG));
        product.setBrand(brand);

        if(request.getCategories() != null && !request.getCategories().isEmpty()){
            var categories = categoryRepository.findCategoriesByName(request.getCategories());
            if(categories.size() != request.getCategories().size()){
                throw new APIException(ErrorConstant.ERR_PARAMETER_NOT_CORRECT, ErrorConstant.ERR_PARAMETER_NOT_CORRECT_MSG);
            } else {
                product.setCategories(categories);
            }
        }

        if(request.getColours() != null && !request.getColours().isEmpty()){
            var colours = colourRepository.findColoursByName(request.getColours());
            if(colours.size() != request.getColours().size()){
                throw new APIException(ErrorConstant.ERR_PARAMETER_NOT_CORRECT, ErrorConstant.ERR_PARAMETER_NOT_CORRECT_MSG);
            } else {
                product.setColours(colours);
            }
        }

        return product;
    }
}
