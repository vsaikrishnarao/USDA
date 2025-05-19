package com.example.updated_test.repository;

import com.example.updated_test.model.AniName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AniNameRepository extends JpaRepository<AniName, Integer> {
}
