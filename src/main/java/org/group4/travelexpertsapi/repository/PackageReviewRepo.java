package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.entity.PackageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageReviewRepo extends JpaRepository<PackageReview,Integer> {
    PackageReview findByPkg(Package pkg);
}
