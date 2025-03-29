package org.group4.travelexpertsapi.controller;
import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.entity.Product;
import org.group4.travelexpertsapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author DarylWxc
 * @Date 3/29/2025
 * @Description controller
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("list")
    public List<Product> getProductList() {
        return productService.getAllProducts();
    }

    @GetMapping("/{product_id}")
    public Product getProduct(@PathVariable int product_id) {
        return productService.getProductById(product_id);
    }

    @PostMapping("/{product_name}")
    public List<Product> getProductByName(@PathVariable String product_name) {
        return productService.getProductByName(product_name);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int product_id, @RequestBody Product product) {
        Product productToUpdate = productService.updateProduct(product_id, product);
        return new ResponseEntity<>(productToUpdate, HttpStatus.OK);
    }
}
