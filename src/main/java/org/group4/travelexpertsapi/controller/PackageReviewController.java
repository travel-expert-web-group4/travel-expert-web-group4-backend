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
    public ResponseEntity<Void> postReview(@RequestPart PackageReview packageReview,
                                           @RequestPart("package_id") Integer package_id,
                                           @RequestPart("user_email") String user_email ) {
        packageReviewService.newPackageReview(packageReview, package_id, user_email);
        return new  ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/package/{package_id}")
    public List<PackageReview> getPackageReviewsByPackageId(@PathVariable("package_id") Integer package_id) {
        List<PackageReview> reviewsOfSelectedPackage = packageReviewService.getPackageReviewByPackage(package_id);
        return reviewsOfSelectedPackage;

    }

    @GetMapping("/{review_id}")
    public PackageReview getPackageReviewById(@PathVariable("review_id") Integer review_id) {
        return packageReviewService.getPackageReviewById(review_id);
    }


}
