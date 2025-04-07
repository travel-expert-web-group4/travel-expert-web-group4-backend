package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.entity.PackageReview;
import org.group4.travelexpertsapi.repository.PackageRepo;
import org.group4.travelexpertsapi.repository.PackageReviewRepo;
import org.group4.travelexpertsapi.repository.WebUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageReviewService {

    PackageRepo packageRepo;
    PackageReviewRepo packageReviewRepo;
    WebUserRepo webUserRepo;

    public PackageReviewService(PackageRepo packageRepo, PackageReviewRepo packageReviewRepo, WebUserRepo webUserRepo) {
        this.packageRepo = packageRepo;
        this.packageReviewRepo = packageReviewRepo;
        this.webUserRepo = webUserRepo;
    }

    public List<PackageReview> getPackageReviewByPackage(int packageId) {
        Package reviewedPackage = packageRepo.findById(packageId).orElse(null);
        if  (reviewedPackage != null) {
            List <PackageReview> reviewsOfSelectedPackage = reviewedPackage.getReviews();
            return reviewsOfSelectedPackage;
        }
        return null;

    }

    public void newPackageReview(PackageReview packageReview) {
        packageReviewRepo.save(packageReview);
        Package reviewedPackage = packageReview.getPkg();
//        reviewedPackage.getReviews().add(packageReview);
//        packageRepo.save(reviewedPackage);
    }


}
