package com.emart.orderservice.service;

import com.emart.orderservice.Repository.OrderRepository;
import com.emart.orderservice.entity.Order;
import com.emart.orderservice.external.client.ProductService;
import com.emart.orderservice.model.OrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;
    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        //Order Entity to save the order with Status Order Created
        //Product Service - block products(reduce Quantity) - Feign Client
        log.info("order qty {} product  id {}", orderRequest.getQuantity(),orderRequest.getProductId());
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating Order with status Created");
        // Payment Service  -> success ? complete : Else cancelled
        log.info("placing order request {} "+orderRequest);
        Order order = Order.builder().productId(orderRequest.getProductId())
                .orderQuantity(orderRequest.getQuantity())
                .orderStatus("CREATED").orderAmount(orderRequest.getOrderAmount()).build();

        order = orderRepository.save(order);
        return order.getOrderId();
    }
}
