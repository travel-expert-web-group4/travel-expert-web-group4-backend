package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.Triptype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriptypeRepo extends JpaRepository<Triptype, Integer> {
    Triptype findByTriptypeid(String triptypeid);
}
