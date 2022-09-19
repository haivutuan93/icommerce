package com.nab.icommerce.repository.mongodb;

import com.nab.icommerce.entity.mongodb.ProductInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductInformationRepository extends MongoRepository<ProductInformation, Long> {

}