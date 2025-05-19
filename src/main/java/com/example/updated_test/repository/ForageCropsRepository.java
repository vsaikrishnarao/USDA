package com.example.updated_test.repository;

import com.example.updated_test.model.ForageCrops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForageCropsRepository extends JpaRepository<ForageCrops, Integer> {
}
