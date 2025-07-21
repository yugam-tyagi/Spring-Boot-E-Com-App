package com.codewithyugam.ecom.dtos;

import com.codewithyugam.ecom.entities.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    public BigInteger id;
    public OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
