package com.local.springbootrestapi.controller;

import com.local.springbootrestapi.model.Product;
import com.local.springbootrestapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id)
    {
        Product product = productService.getProductById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Product not found")
                );
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName)
    {
        return ResponseEntity.ok(productService.getProductByCategory(categoryName));
    }

    @GetMapping("/price/{maxPrice}")
    public ResponseEntity<List<Product>> getProductByMaxPrice(@PathVariable Double maxPrice)
    {
        return ResponseEntity.ok(productService.findProductsByPriceLessThanEqual(maxPrice));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String name)
    {
        return  ResponseEntity.ok(productService.findProductsByName(name));
    }

    // add new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product)
    {
        Product newProduct = productService.saveProduct(product);

        return  new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product)
    {
        Product updateProduct = productService.getProductById(id) .orElseThrow(
                ()-> new RuntimeException("Product not found")
        );
        // update product if it is existed
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setCategory(product.getCategory());

        Product updatedProduct = productService.saveProduct(updateProduct);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);

    }

    // delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id)
    {
        Product productToDelete = productService.getProductById(id) .orElseThrow(
                ()-> new RuntimeException("Product not Found")
        );

        productService.deleteProduct(productToDelete);
        return ResponseEntity.ok("Product Deleted");
    }







}