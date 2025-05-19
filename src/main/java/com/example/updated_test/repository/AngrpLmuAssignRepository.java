package com.example.updated_test.repository;

import com.example.updated_test.model.AngrpLmuAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AngrpLmuAssignRepository extends JpaRepository<AngrpLmuAssign, Integer> {
}
