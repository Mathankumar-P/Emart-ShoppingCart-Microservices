package com.emart.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductRequest {
    private String productName;
    private Long price;
    private Long quantity;
}
