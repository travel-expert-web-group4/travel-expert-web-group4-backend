package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.repository.PackageRepo;
import org.group4.travelexpertsapi.repository.PackageReviewRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    private final PackageRepo packageRepo;
    private final PackageReviewRepo packageReviewRepo;

    @Value("${packages.image.upload-dir}")
    private String imageUploadDir;

    // DI to access repository
    public PackageService(PackageRepo packageRepo, PackageReviewRepo packageReviewRepo) {
        this.packageRepo = packageRepo;
        this.packageReviewRepo = packageReviewRepo;
    }

    // Get all packages
    public List<Package> getAllPackages() {
        return packageRepo.findAll();
    }

    // Get package by ID
    public Optional<Package> getPackageById(Integer packageId) {
        Package existingPackage = packageRepo.findById(packageId).orElse(null);
        if (existingPackage != null) {
            // Check if reviews exist, if yes, then update average before returning
            if (packageReviewRepo.existsByPkg(existingPackage)) {
                BigDecimal average = packageReviewRepo.getAvgRatingByPkgId(packageId);
                existingPackage.setRating(average);
                Package saved = packageRepo.save(existingPackage);
                return Optional.of(saved);
            }
            // If no reviews, return package as-is
            return Optional.of(existingPackage);
        }
        return Optional.empty();
    }

    // Create a new package
    public Package addPackage(Package newPackage) {
        return packageRepo.save(newPackage);
    }

    // Update a package
    public Optional<Package> updatePackage(Integer packageId, Package updatedPackage) {
        Package existingPackage = packageRepo.findById(packageId).orElse(null);
        if (existingPackage != null) {
            existingPackage.setPkgname(updatedPackage.getPkgname());
            existingPackage.setPkgstartdate(updatedPackage.getPkgstartdate());
            existingPackage.setPkgenddate(updatedPackage.getPkgenddate());
            existingPackage.setPkgdesc(updatedPackage.getPkgdesc());
            existingPackage.setPkgbaseprice(updatedPackage.getPkgbaseprice());
            existingPackage.setPkgagencycommission(updatedPackage.getPkgagencycommission());
            existingPackage.setDestination(updatedPackage.getDestination());
            existingPackage.setLat(updatedPackage.getLat());
            existingPackage.setLng(updatedPackage.getLng());
            Package saved = packageRepo.save(existingPackage);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    // Upload an image
    public Optional<String> writePackageImage(Integer packageId, MultipartFile image) {
        Package existingPackage = packageRepo.findById(packageId).orElse(null);
        if (existingPackage != null) {
            String fileName = existingPackage.getPkgname() + ".jpg";
            try {
                File dir = new File(imageUploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Path path = Paths.get(imageUploadDir, fileName);
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String location = "/images/packages/" + fileName;
            return Optional.of(location);
        }
        return Optional.empty();
    }

    public Optional<Package> addPackageImage(Integer packageId, MultipartFile image) {
        Package existingPackage = packageRepo.findById(packageId).orElse(null);
        Optional<String> write = writePackageImage(packageId, image);
        if (existingPackage != null && write.isPresent()) {
            existingPackage.setImageUrl(write.get());
            Package saved = packageRepo.save(existingPackage);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public Optional<Package> deletePackageImage(Integer packageId) {
        Package existingPackage = packageRepo.findById(packageId).orElse(null);
        if (existingPackage != null) {
            String fileName = existingPackage.getPkgname() + ".jpg";
            Path path = Paths.get(imageUploadDir, fileName);
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            existingPackage.setImageUrl(null);
            Package saved = packageRepo.save(existingPackage);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    // Delete a package
    public boolean deletePackage(Integer packageId) {
        Package packageToDelete = packageRepo.findById(packageId).orElse(null);
        if (packageToDelete != null) {
            packageRepo.delete(packageToDelete);
            return true;
        }
        return false;
    }
}
