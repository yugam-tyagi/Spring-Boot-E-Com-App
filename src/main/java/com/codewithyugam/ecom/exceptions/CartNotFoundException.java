package com.codewithyugam.ecom.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(){
        super("Cart not found");
    }
}
