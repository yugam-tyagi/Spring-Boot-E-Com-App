package com.codewithyugam.ecom.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class WebhookRequest {
    private Map<String, String> headers;
    private String payload;
}
