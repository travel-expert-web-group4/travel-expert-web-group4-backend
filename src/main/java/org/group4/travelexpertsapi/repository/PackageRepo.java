package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepo extends JpaRepository<Package, Long> {

}
