package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Supplier;
import org.group4.travelexpertsapi.repository.SupplierRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DarylWxc
 * @Date 3/29/2025
 * @Description Service
 */

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;

    SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }
    public Supplier getSupplierById(int id) {
        return supplierRepo.findById(id).orElse(null);
    }

    public List<Supplier> getSupplierByName(String name) {
        return supplierRepo.findBySupnameContainingIgnoreCase(name);
    }

    public Supplier updateSupplier(int id,Supplier supplier) {
        Supplier supplierToUpdate = supplierRepo.findById(id).orElse(null);
        if (supplierToUpdate != null) {
            supplierToUpdate.setSupname(supplier.getSupname());
        }
        return supplierRepo.save(supplierToUpdate);
    }
}
