package com.codewithyugam.ecom.mappers;

import com.codewithyugam.ecom.dtos.ProductDto;
import com.codewithyugam.ecom.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target="categoryId")
    ProductDto toDto(Product product);
    Product toEntity(ProductDto request);
    @Mapping(target = "id", ignore = true)
    void updateProduct(ProductDto request, @MappingTarget Product product);
}
