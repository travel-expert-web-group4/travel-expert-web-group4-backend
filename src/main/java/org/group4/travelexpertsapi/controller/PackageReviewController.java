package org.group4.travelexpertsapi.controller;

import org.group4.travelexpertsapi.entity.PackageReview;
import org.group4.travelexpertsapi.service.PackageReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class PackageReviewController {

    PackageReviewService packageReviewService;

    public PackageReviewController(PackageReviewService packageReviewService) {
        this.packageReviewService = packageReviewService;
    }

    @PostMapping("/post")
    public ResponseEntity<Void> postReview(@RequestBody PackageReview packageReview) {
        packageReviewService.newPackageReview(packageReview);
        return new  ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/{package_id}")
    public List<PackageReview> getPackageReviewsByPackageId(@PathVariable("package_id") Integer package_id) {
        List<PackageReview> reviewsOfSelectedPackage = packageReviewService.getPackageReviewByPackage(package_id);
        return reviewsOfSelectedPackage;

    }


}
