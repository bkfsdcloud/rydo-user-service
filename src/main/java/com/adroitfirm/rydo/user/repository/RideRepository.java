package com.adroitfirm.rydo.user.repository;

import com.adroitfirm.rydo.user.entity.Ride;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RideRepository extends JpaRepository<Ride, Long> {
	
	@Query("SELECT r FROM Ride r JOIN RideAssignment ra ON (r.id = ra.rideId) WHERE ra.status = 'REQUESTED' and r.customer.id = :id")
	Optional<Ride> findActiveRideByUser(@Param("id") Long id);
}