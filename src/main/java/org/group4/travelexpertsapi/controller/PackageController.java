package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.service.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/package")
public class PackageController {

    // DI to access PackageService class
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    // Endpoint to get all packages
    @GetMapping("/list")
    public List<Package> getPackageList() {
        return packageService.getAllPackages();
    }

    // Endpoint to get package by ID
    @GetMapping("/{package_id}")
    public Package getPackage(@PathVariable Integer package_id) {
        return packageService.getPackageById(package_id);
    }

    // Endpoint to add a new package
    @PostMapping("/new")
    public ResponseEntity<Package> newPackage(@RequestBody Package newPackage) {
        packageService.addPackage(newPackage);
        return new ResponseEntity<>(newPackage, HttpStatus.CREATED);
    }

    // Endpoint to update a package
    @PutMapping("/{package_id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Integer package_id, @RequestBody Package updatedPackage) {
        Package toUpdate = packageService.updatePackage(package_id, updatedPackage);
        return new ResponseEntity<>(toUpdate, HttpStatus.OK);
    }

    // Endpoint to delete a package
    @DeleteMapping("/{package_id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Integer package_id) {
        packageService.deletePackage(package_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}