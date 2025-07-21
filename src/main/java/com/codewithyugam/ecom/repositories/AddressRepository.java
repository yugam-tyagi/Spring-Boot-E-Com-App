package com.codewithyugam.ecom.repositories;

import com.codewithyugam.ecom.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}