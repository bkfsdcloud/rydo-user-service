package com.adroitfirm.rydo.user.repository;

import com.adroitfirm.rydo.user.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}