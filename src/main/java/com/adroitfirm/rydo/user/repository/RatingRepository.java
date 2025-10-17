package com.adroitfirm.rydo.user.repository;

import com.adroitfirm.rydo.user.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}