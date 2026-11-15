package com.local.springbootrestapi.service;

import com.local.springbootrestapi.model.Product;
import com.local.springbootrestapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);
    }

    public List<Product> getProductByCategory(String name)
    {
        return productRepository.findProductByCategory(name);
    }

    public List<Product> findProductsByPriceLessThanEqual(Double price)
    {
        return productRepository.findProductsByPriceLessThanEqual(price);
    }

    public List<Product> findProductsByName(String name)
    {
        return productRepository.findProductsByNameContainingIgnoreCase(name);
    }

    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }

    public void deleteProduct(Product product)
    {
        productRepository.delete(product);
    }


}
