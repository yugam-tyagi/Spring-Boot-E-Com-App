package com.codewithyugam.ecom.services;

import com.codewithyugam.ecom.dtos.CartDto;
import com.codewithyugam.ecom.dtos.CartItemDto;
import com.codewithyugam.ecom.entities.Cart;
import com.codewithyugam.ecom.exceptions.CartNotFoundException;
import com.codewithyugam.ecom.exceptions.ProductNotFoundException;
import com.codewithyugam.ecom.mappers.CartMapper;
import com.codewithyugam.ecom.repositories.CartRepository;
import com.codewithyugam.ecom.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addProductToCart(UUID cartId, Long productId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        var cartDto = cartMapper.toDto(cart);
        cart.getCartItems().forEach(item -> cartDto.getItems().add(cartMapper.toDto(item)));
        return cartDto;
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        var cartItem = cart.getItem(productId);
        if(cartItem==null){
            throw new ProductNotFoundException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void deleteCartItem(UUID cartId, Long productId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        cart.clearCart();
        cartRepository.save(cart);
    }
}
