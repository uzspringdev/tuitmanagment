package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderExcelService;
import com.example.demo.service.OrderService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderExcelService orderExcelService;

    public OrderController(OrderService orderService, OrderExcelService orderExcelService) {
        this.orderService = orderService;
        this.orderExcelService = orderExcelService;
    }

    //CREATE
    @PostMapping
    public String createOrder(@RequestParam Long orderNumber, @RequestParam String orderName, @RequestParam String typeOfOrder, @RequestParam String orderDate) {

        LocalDate date = LocalDate.parse(orderDate);

        Order order = new Order(orderNumber, orderName, typeOfOrder, date);
        orderService.createOrder(order);
        return "redirect:/";
    }

    //CREATE PAGE
    @GetMapping(value = "/add-order")
    public String createOrderPage() {
        return "/order/add-order";
    }

    //READ
    @GetMapping("/list-order")
    public String getAllOrders(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "size") Optional<Integer> size,
            @RequestParam(value = "keyword") Optional<String> keyword,
            Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String searchKeyword = keyword.orElse("");

        Page<Order> orderPage = orderService.findPaginated(PageRequest.of(currentPage - 1, pageSize), searchKeyword);

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("keyword", searchKeyword);

        if (orderPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, orderPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/order/list-order";
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    //UPDATE
    @PostMapping(value = "/update/{id}")
    public String updateOrder(
            @PathVariable Long id,
            @RequestParam Long orderNumber,
            @RequestParam String orderName,
            @RequestParam String typeOfOrder,
            @RequestParam String orderDate) {
        LocalDate date = LocalDate.parse(orderDate);
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setName(orderName);
        order.setTypeOfOrder(typeOfOrder);
        order.setOrderDate(date);
        orderService.updateOrder(id, order);
        return "redirect:/order/list-order";
    }

    //UPDATE
    @GetMapping(value = "/update/{id}")
    public String updateOrderPage(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id).getBody();
        model.addAttribute("order", order);
        return "/order/update-order";
    }

    //DELETE
    @GetMapping(value = "/delete/{id}")
    public String deleteOrder(@PathVariable Long id, @RequestParam(value = "keyword") Optional<String> keyword) {
        orderService.deleteOrder(id);
        String searchKeyword = keyword.orElse("");
        if (searchKeyword.equals("")) {
            return "redirect:/order/list-order";
        } else {
            return "redirect:/order/list-order?keyword=" + searchKeyword;
        }
    }

    //GET DATA AS EXCEL
    @GetMapping(value = "/download/excel")
    public ResponseEntity<Resource> getFile(@RequestParam Optional<String> keyword) {
        String fileName = "orders.xls";
        String searchKeyword = keyword.orElse("");
        InputStreamResource file = new InputStreamResource(orderExcelService.load(searchKeyword));

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
