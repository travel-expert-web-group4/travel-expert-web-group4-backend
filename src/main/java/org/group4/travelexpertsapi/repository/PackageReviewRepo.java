package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Package;
import org.group4.travelexpertsapi.entity.PackageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PackageReviewRepo extends JpaRepository<PackageReview,Integer> {
    PackageReview findByPkg(Package pkg);

    boolean existsByPkg(Package pkg);

    @Query("SELECT AVG(rating) FROM PackageReview WHERE pkg.id = :packageId")
    BigDecimal getAvgRatingByPkgId(@Param("packageId") Integer packageId);
}
