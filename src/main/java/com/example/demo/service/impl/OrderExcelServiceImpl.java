package com.example.demo.service.impl;

import com.example.demo.entity.Order;
import com.example.demo.entity.Speciality;
import com.example.demo.helper.OrderExcelHelper;
import com.example.demo.helper.SpecialityExcelHelper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.SpecialityRepository;
import com.example.demo.service.OrderExcelService;
import com.example.demo.service.SpecialityExcelService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
public class OrderExcelServiceImpl implements OrderExcelService {

    private final OrderRepository orderRepository;

    public OrderExcelServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ByteArrayInputStream load(String keyword) {

        final List<Order> speciality;
        if (Objects.equals(keyword, "")) {
            speciality = orderRepository.findAll(Sort.by("name"));
        } else {
            speciality = orderRepository.findAllByKeyword(keyword);
        }

        return OrderExcelHelper.ordersToExcel(speciality);
    }
}
