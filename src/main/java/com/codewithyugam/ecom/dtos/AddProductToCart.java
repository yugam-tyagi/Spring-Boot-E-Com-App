package com.codewithyugam.ecom.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductToCart {
    @NotNull
    private Long productId;
}
