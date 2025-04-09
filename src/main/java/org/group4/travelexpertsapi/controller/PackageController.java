package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.service.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/package")
public class PackageController {

    // DI to access PackageService class
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    // Endpoint to get all packages
    @GetMapping
    public List<Package> getPackageList() {
        return packageService.getAllPackages();
    }

    // Endpoint to get package by ID
    // Added logic to automatically update the average rating based on reviews
    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackage(@PathVariable Integer id) {
        Optional<Package> existingPackage = packageService.getPackageById(id);
        return existingPackage.map(aPackage -> new ResponseEntity<>(aPackage, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to add a new package
    @PostMapping("/new")
    public ResponseEntity<Package> newPackage(@RequestBody Package newPackage) {
        Package pkg = packageService.addPackage(newPackage);
        return new ResponseEntity<>(pkg, HttpStatus.CREATED);
    }

    // Endpoint to update a package
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Integer id, @RequestBody Package updatedPackage) {
        Optional<Package> existingPackage = packageService.updatePackage(id, updatedPackage);
        return existingPackage.map(aPackage -> new ResponseEntity<>(aPackage, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to upload/overwrite an image for a package
    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<Package> uploadImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
        Optional<Package> existingPackage = packageService.addPackageImage(id, image);
        return existingPackage.map(aPackage -> new ResponseEntity<>(aPackage, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to delete an image for a package
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Package> deleteImage(@PathVariable Integer id) {
        Optional<Package> existingPackage = packageService.deletePackageImage(id);
        return existingPackage.map(aPackage -> new ResponseEntity<>(aPackage, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to delete a package
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Integer id) {
        if (packageService.deletePackage(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}