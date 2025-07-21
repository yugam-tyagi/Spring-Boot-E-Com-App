package com.codewithyugam.ecom.controllers;

import com.codewithyugam.ecom.dtos.OrderDto;
import com.codewithyugam.ecom.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable(name = "orderId") Long orderId){
        return orderService.getOrder(orderId);
    }

}
