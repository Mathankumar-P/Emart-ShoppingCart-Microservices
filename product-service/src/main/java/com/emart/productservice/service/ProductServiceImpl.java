package com.emart.productservice.service;

import com.emart.productservice.entity.Product;
import com.emart.productservice.exception.ProductServiceCustomException;
import com.emart.productservice.model.ProductRequest;
import com.emart.productservice.model.ProductResponse;
import com.emart.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository ;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding Product ");
        Product product = Product.builder().productName(productRequest.getProductName())
                .price(productRequest.getPrice()).quantity(productRequest.getQuantity())
                .build();
        product = productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Get the product for Product id   : : "+ productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("Product with given  id : : "+ productId + " is not found", "PRODUCT NOT FOUND"));
        ProductResponse response = new ProductResponse();
        copyProperties(product, response);
        return response;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("reduce Quantity {} fot Id : { }",quantity,productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException(
                        "Product with product id {} is not found", "PRODUCT NOT FOUND"
                ));
        if(product.getQuantity() < quantity) {
            throw new ProductServiceCustomException(
                    "Product has insufficient Quantity " , "INSUFFICIENT_QUANTITY"
            );
        }
        product.setQuantity( product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated successfully");
    }
}
