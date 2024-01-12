package com.emart.productservice.service;

import com.emart.productservice.model.ProductRequest;
import com.emart.productservice.model.ProductResponse;
import com.emart.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface ProductService {

   public  Long addProduct(ProductRequest productRequest);

   ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, Long quantity);
}
