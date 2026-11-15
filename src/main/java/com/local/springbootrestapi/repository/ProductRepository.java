package com.local.springbootrestapi.repository;

import com.local.springbootrestapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    // find product by category
    List<Product> findProductByCategory(String name);

   // find products with price less or equal to max price
    List<Product> findProductsByPriceLessThanEqual(Double price);

    // find products containing name (Case Sensitive)
    List<Product> findProductsByNameContainingIgnoreCase(String name);

}
