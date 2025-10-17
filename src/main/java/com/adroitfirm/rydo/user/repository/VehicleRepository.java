package com.adroitfirm.rydo.user.repository;

import com.adroitfirm.rydo.user.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}