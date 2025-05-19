package com.example.updated_test.repository;

import com.example.updated_test.model.AniClassAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AniClassAssignRepository extends JpaRepository<AniClassAssign, Integer> {
}
