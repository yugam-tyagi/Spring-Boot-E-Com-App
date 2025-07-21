package com.codewithyugam.ecom.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItems> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(CartItems item : cartItems){
            totalPrice = totalPrice.add(item.getTotalPrice());
        }
        return totalPrice;
    }

    public CartItems getItem(Long productId){
        return cartItems.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElse(null);
    }

    public CartItems addItem(Product product){
        var cartItem = getItem(product.getId());
        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }
        else{
            cartItem = new CartItems();
            cartItem.setCart(this);
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cartItems.add(cartItem);
        }
        return cartItem;
    }

    public void removeItem(Long productId){
        var cartItem = getItem(productId);
        if(cartItem!=null) {
            cartItems.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clearCart(){
        cartItems.clear();
    }

    public boolean isEmpty(){
        return cartItems.isEmpty();
    }
}


