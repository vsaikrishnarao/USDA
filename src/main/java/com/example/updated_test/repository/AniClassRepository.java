package com.example.updated_test.repository;

import com.example.updated_test.model.AniClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AniClassRepository extends JpaRepository<AniClass, Long> {
    AniClass findByLabel(String label);
}
