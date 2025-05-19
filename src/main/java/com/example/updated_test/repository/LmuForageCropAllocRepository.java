package com.example.updated_test.repository;

import com.example.updated_test.model.LmuForageCropAlloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LmuForageCropAllocRepository extends JpaRepository<LmuForageCropAlloc, Integer> {
}
