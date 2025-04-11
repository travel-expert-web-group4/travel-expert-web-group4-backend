package org.group4.travelexpertsapi.service;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.entity.PackageReview;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.group4.travelexpertsapi.repository.PackageRepo;
import org.group4.travelexpertsapi.repository.PackageReviewRepo;
import org.group4.travelexpertsapi.repository.auth.WebUserRepo;
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

    public void newPackageReview(PackageReview packageReview, Integer package_id, String user_email) {
        WebUser webUser = webUserRepo.findByEmail(user_email).orElse(null);
        Package reviewedPackage = packageRepo.findById(package_id).orElse(null);
        packageReview.setUser(webUser);
        packageReview.setPkg(reviewedPackage);


        packageReviewRepo.save(packageReview);

//        reviewedPackage.getReviews().add(packageReview);
//        packageRepo.save(reviewedPackage);
    }

    public PackageReview getPackageReviewById(int Id) {
        return packageReviewRepo.findById(Id).orElse(null);

    }


}
