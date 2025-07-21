package com.codewithyugam.ecom.controllers;

import com.codewithyugam.ecom.dtos.ProductDto;
import com.codewithyugam.ecom.entities.Product;
import com.codewithyugam.ecom.mappers.ProductMapper;
import com.codewithyugam.ecom.repositories.CategoryRepository;
import com.codewithyugam.ecom.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(@RequestParam(name="categoryId", required = false) Byte categoryid){
        if(categoryid != null){
            return productRepository.findByCategoryId(categoryid).stream().map(productMapper::toDto).toList();
        }
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getproduct(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        if(product==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto request){
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if(category==null) {
            return ResponseEntity.notFound().build();
        }
        var product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProductDto request){
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if(category==null) {
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if(product==null){
            return ResponseEntity.notFound().build();
        }
        productMapper.updateProduct(request,product);
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        if(product==null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

}
