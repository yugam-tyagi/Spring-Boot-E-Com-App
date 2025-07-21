package com.codewithyugam.ecom.controllers;

import com.codewithyugam.ecom.dtos.AddProductToCart;
import com.codewithyugam.ecom.dtos.CartDto;
import com.codewithyugam.ecom.dtos.CartItemDto;
import com.codewithyugam.ecom.dtos.UpdateCartItem;
import com.codewithyugam.ecom.exceptions.CartNotFoundException;
import com.codewithyugam.ecom.exceptions.ProductNotFoundException;
import com.codewithyugam.ecom.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name="Carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(){
        var cartDto = cartService.createCart();
        return ResponseEntity.ok().body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the cart")
    public ResponseEntity<CartItemDto> addProductToCart(@PathVariable(name="cartId") UUID cartId, @RequestBody AddProductToCart request){
        var cartItemDto = cartService.addProductToCart(cartId, request.getProductId());
        return ResponseEntity.ok().body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name="cartId") UUID cartId){
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok().body(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable(name="cartId") UUID cartId, @PathVariable(name="productId") Long productId, @Valid @RequestBody UpdateCartItem request){
        var cartItemDto = cartService.updateCartItem(cartId, productId, request.getQuantity());
        return ResponseEntity.ok().body(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable(name="cartId") UUID cartId, @PathVariable(name="productId") Long productId){
        cartService.deleteCartItem(cartId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable(name="cartId") UUID cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Product not found in cart"));
    }
}
