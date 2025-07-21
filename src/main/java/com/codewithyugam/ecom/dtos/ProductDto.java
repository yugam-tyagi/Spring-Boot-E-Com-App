package com.codewithyugam.ecom.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ProductDto {
    @JsonProperty("Product_id")
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Byte categoryId;
}
