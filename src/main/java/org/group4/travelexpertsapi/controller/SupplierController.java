package org.group4.travelexpertsapi.controller;
import org.group4.travelexpertsapi.entity.Supplier;
import org.group4.travelexpertsapi.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author DarylWxc
 * @Date 3/29/2025
 * @Description Controller
 */

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("list")
    public List<Supplier> getSupplierList() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{supplier_id}")
    public Supplier getSupplier(@PathVariable int supplier_id) {
        return supplierService.getSupplierById(supplier_id);
    }

    @PostMapping("/{supplier_name}")
    public List<Supplier> getSupplierByName(@PathVariable String supplier_name) {
        return supplierService.getSupplierByName(supplier_name);
    }

    @PutMapping("/{supplier_id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable int supplier_id, @RequestBody Supplier supplier) {
        Supplier supplierToUpdate = supplierService.updateSupplier(supplier_id, supplier);
        return new ResponseEntity<>(supplierToUpdate, HttpStatus.OK);
    }
}
