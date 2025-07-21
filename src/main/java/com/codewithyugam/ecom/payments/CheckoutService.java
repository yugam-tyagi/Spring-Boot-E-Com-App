package com.codewithyugam.ecom.payments;

import com.codewithyugam.ecom.entities.Order;
import com.codewithyugam.ecom.exceptions.CartEmptyException;
import com.codewithyugam.ecom.exceptions.CartNotFoundException;
import com.codewithyugam.ecom.repositories.CartRepository;
import com.codewithyugam.ecom.repositories.OrderRepository;
import com.codewithyugam.ecom.services.AuthService;
import com.codewithyugam.ecom.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        if(cart.isEmpty()){
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try{
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }
        catch(PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway.parseWebhookRequest(request).ifPresent(paymentResult -> {
            var order = orderRepository.findById(Long.valueOf(paymentResult.getOrderId())).orElseThrow();
            order.setStatus(paymentResult.getOrderStatus());
            orderRepository.save(order);
        });
    }
}
