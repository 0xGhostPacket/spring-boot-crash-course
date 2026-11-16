package com.local.springbootrestapi.controller;

import com.local.springbootrestapi.model.Product;
import com.local.springbootrestapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product)
    {
        Product newProduct = productService.saveProduct(product);

        return  new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@Valid @RequestBody Product product)
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex)
    {
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(
                (err) -> {
                    String fieldName = ((FieldError) err).getField();
                    String message = err.getDefaultMessage();
                    errors.put(fieldName,message);
                });
        return errors;

    }








}