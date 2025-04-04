package org.group4.travelexpertsapi.repository;

import jakarta.validation.constraints.Size;
import org.group4.travelexpertsapi.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
    boolean existsByAgtemail(String agtemail);
    Agent findByAgtemail(String agtemail);
}
