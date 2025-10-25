package com.adroitfirm.rydo.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adroitfirm.rydo.user.entity.RideAssignment;

public interface RideAssignmentRepository extends JpaRepository<RideAssignment, Long> {
    List<RideAssignment> findByDriverIdAndStatus(Long driverId, String status);
}
