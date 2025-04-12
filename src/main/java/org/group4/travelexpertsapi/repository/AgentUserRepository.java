package org.group4.travelexpertsapi.repository;

import org.group4.travelexpertsapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentUserRepository extends JpaRepository<User,Integer> {
}
