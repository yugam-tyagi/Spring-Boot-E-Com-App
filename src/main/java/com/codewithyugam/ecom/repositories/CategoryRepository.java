package com.codewithyugam.ecom.repositories;

import com.codewithyugam.ecom.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}