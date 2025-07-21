package com.codewithyugam.ecom.payments;

import com.codewithyugam.ecom.entities.Order;
import com.codewithyugam.ecom.entities.OrderStatus;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {

    @Value("${websiteUrl}")
    private String websiteUrl;
    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try{
            //Creating a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("order_id", order.getId().toString());

            order.getItems().forEach(item -> {
                var lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity(Long.valueOf(item.getQuantity()))
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("INR")
                                        .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName()).build()
                                        ).build()
                        ).build();
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        }

        catch (StripeException ex) {
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try{
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            return switch(event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.PAID));
                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.FAILED));
                default -> Optional.empty();
            };
        }
        catch(SignatureVerificationException e){
            throw new PaymentException("Invalid Signature");
        }
    }

    private Long extractOrderId(Event event){
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(() -> new PaymentException("Couldn't deserilaize Stripe event"));
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }
}
