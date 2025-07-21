package com.codewithyugam.ecom.repositories;

import com.codewithyugam.ecom.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
