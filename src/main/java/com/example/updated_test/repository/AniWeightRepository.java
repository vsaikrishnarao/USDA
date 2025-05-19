package com.example.updated_test.repository;

import com.example.updated_test.model.AniWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AniWeightRepository extends JpaRepository<AniWeight, Integer> {
}
