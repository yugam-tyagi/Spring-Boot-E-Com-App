package com.codewithyugam.ecom.services;

import com.codewithyugam.ecom.dtos.OrderDto;
import com.codewithyugam.ecom.dtos.OrderItemDto;
import com.codewithyugam.ecom.mappers.CartMapper;
import com.codewithyugam.ecom.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.codewithyugam.ecom.mappers.CartMapper;
import com.codewithyugam.ecom.repositories.OrderRepository;
import com.codewithyugam.ecom.services.AuthService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartMapper cartMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.findByCustomerId(user.getId());
        var response = new ArrayList<OrderDto>();
        orders.forEach(order -> {
            var orderDto = new OrderDto();
            orderDto.setId(BigInteger.valueOf(order.getId()));
            orderDto.setStatus(order.getStatus());
            orderDto.setCreatedAt(order.getCreatedAt());
            orderDto.setTotalPrice(order.getTotalPrice());
            order.getItems().forEach(item -> {
                var orderItemDto = new OrderItemDto();
                orderItemDto.setQuantity(item.getQuantity());
                orderItemDto.setTotalPrice(item.getTotalPrice());
                orderItemDto.setProduct(cartMapper.toDto(item.getProduct()));
                orderDto.getItems().add(orderItemDto);
            });
            response.add(orderDto);
        });
        return response;
    }

    public ResponseEntity<?> getOrder(Long orderId){
        var order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order doesn't exist");
        }
        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have access to the order");
        }
        var orderDto = new OrderDto();
        orderDto.setId(BigInteger.valueOf(order.getId()));
        orderDto.setStatus(order.getStatus());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setTotalPrice(order.getTotalPrice());
        order.getItems().forEach(item -> {
            var orderItemDto = new OrderItemDto();
            orderItemDto.setQuantity(item.getQuantity());
            orderItemDto.setTotalPrice(item.getTotalPrice());
            orderItemDto.setProduct(cartMapper.toDto(item.getProduct()));
            orderDto.getItems().add(orderItemDto);
        });
        return ResponseEntity.ok(orderDto);
    }
}
