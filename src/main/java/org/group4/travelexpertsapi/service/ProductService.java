package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Product;
import org.group4.travelexpertsapi.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DarylWxc
 * @Date 3/29/2025
 * @Description Service
 */

@Service
public class ProductService {
    private final ProductRepo productRepo;

    ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    public Product getProductById(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> getProductByName(String name) {
        return productRepo.findByProdnameContainingIgnoreCase(name);
    }

    public Product updateProduct(int id,Product product) {
        Product productToUpdated = productRepo.findById(id).orElse(null);
        if (productToUpdated != null) {
            productToUpdated.setProdname(product.getProdname());
        }
        return productRepo.save(productToUpdated);
    }
}
