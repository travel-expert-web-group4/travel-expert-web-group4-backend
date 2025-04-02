package org.group4.travelexpertsapi.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.travelexpertsapi.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepo extends JpaRepository<Package, Integer> {

    Package findByPkgname(String pkgname);
}
