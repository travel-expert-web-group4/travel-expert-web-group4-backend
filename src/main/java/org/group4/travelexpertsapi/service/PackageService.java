package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.repository.PackageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final PackageRepo packageRepo;

    // DI to access repository
    public PackageService(PackageRepo packageRepo) {
        this.packageRepo = packageRepo;
    }

    // Get all packages
    public List<Package> getAllPackages() {
        return packageRepo.findAll();
    }

    // Get package by ID
    public Package getPackageById(Long packageId) {
        return packageRepo.findById(packageId).orElse(null);
    }

    // Create a package
    public void addPackage(Package newPackage) {
        packageRepo.save(newPackage);
    }

    // Update a package
    public Package updatePackage(Long packageId, Package updatedPackage) {
        Package existingPackage = packageRepo.findById(packageId).orElse(null);
        if (existingPackage != null) {
            existingPackage.setPkgname(updatedPackage.getPkgname());
            existingPackage.setPkgstartdate(updatedPackage.getPkgstartdate());
            existingPackage.setPkgenddate(updatedPackage.getPkgenddate());
            existingPackage.setPkgdesc(updatedPackage.getPkgdesc());
            existingPackage.setPkgbaseprice(updatedPackage.getPkgbaseprice());
            existingPackage.setPkgagencycommission(updatedPackage.getPkgagencycommission());
        }
        return packageRepo.save(existingPackage);
    }

    // Delete a package
    public void deletePackage(Long packageId) {
        packageRepo.deleteById(packageId);
    }
}
