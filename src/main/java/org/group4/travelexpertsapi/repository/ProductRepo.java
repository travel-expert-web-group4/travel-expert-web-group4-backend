package org.group4.travelexpertsapi.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.travelexpertsapi.entity.Product;
import org.group4.travelexpertsapi.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author DarylWxc
 * @Date 3/29/2025
 * @Description repository
 */

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByProdnameContainingIgnoreCase(String prodname);
}
