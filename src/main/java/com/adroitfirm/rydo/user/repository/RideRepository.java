package com.adroitfirm.rydo.user.repository;

import com.adroitfirm.rydo.user.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}