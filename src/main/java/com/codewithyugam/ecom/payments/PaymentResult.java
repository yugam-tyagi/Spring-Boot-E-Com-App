package com.codewithyugam.ecom.payments;

import com.codewithyugam.ecom.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private OrderStatus orderStatus;
}
