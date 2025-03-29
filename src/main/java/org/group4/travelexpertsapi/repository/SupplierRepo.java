package org.group4.travelexpertsapi.repository;

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
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {
    List<Supplier> findBySupnameContainingIgnoreCase(String supname);
}
