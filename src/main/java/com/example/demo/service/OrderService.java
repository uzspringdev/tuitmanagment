package com.example.demo.service;

import com.example.demo.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    //CREATE
    ResponseEntity<Order> createOrder(Order order);

    //READ
    ResponseEntity<List<Order>> getAllOrders();

    //READ BY ID
    ResponseEntity<Order> getOrderById(Long id);

    //UPDATE
    ResponseEntity<Order> updateOrder(Long id, Order order);

    //DELETE
    ResponseEntity<HttpStatus> deleteOrder(Long id);

    //READ PAGINATED
    Page<Order> findPaginated(Pageable pageable, String keyword);
}
