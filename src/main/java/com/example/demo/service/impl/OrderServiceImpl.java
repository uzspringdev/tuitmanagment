package com.example.demo.service.impl;

import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<Order> createOrder(Order order) {
        try {
            Order newOrder = orderRepository.save(
                    new Order(
                            order.getOrderNumber(),
                            order.getName(),
                            order.getTypeOfOrder(),
                            order.getOrderDate()));
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orderList = orderRepository.findAll();
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Order> getOrderById(Long id) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Order order = optionalOrder.get();
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Order> updateOrder(Long id, Order order) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Order editedOrder = optionalOrder.get();
            editedOrder.setOrderNumber(order.getOrderNumber());
            editedOrder.setName(order.getName());
            editedOrder.setTypeOfOrder(order.getTypeOfOrder());
            editedOrder.setOrderDate(order.getOrderDate());
            Order updatedOrder = orderRepository.save(editedOrder);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteOrder(Long id) {
        try {
            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Order> findPaginated(Pageable pageable, String keyword) {
        final List<Order> orders;
        if (keyword.equals("")) {
            orders = orderRepository.findAll(Sort.by("name"));
        } else {
            orders = orderRepository.findAllByKeyword(keyword);
        }

        Page<Order> orderPage;
        try {
            List<Order> orderList;
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = pageSize * currentPage;
            if (startItem > orders.size()) {
                orderList = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, orders.size());
                orderList = orders.subList(startItem, toIndex);
            }
            orderPage = new PageImpl<>(orderList, PageRequest.of(startItem, pageSize), orders.size());
        } catch (Exception e) {
            orderPage = null;
        }

        return orderPage;
    }


}
