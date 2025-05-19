package com.example.updated_test.repository;

import com.example.updated_test.model.AniInv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AniInvRepository extends JpaRepository<AniInv, Long> {
}
