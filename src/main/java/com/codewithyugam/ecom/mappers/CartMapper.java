package com.codewithyugam.ecom.mappers;

import com.codewithyugam.ecom.dtos.CartDto;
import com.codewithyugam.ecom.dtos.CartItemDto;
import com.codewithyugam.ecom.dtos.CartProductDto;
import com.codewithyugam.ecom.dtos.OrderProductDto;
import com.codewithyugam.ecom.entities.Cart;
import com.codewithyugam.ecom.entities.CartItems;
import com.codewithyugam.ecom.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItems cartItem);

    OrderProductDto toDto(Product product);
}
