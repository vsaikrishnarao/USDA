package com.example.updated_test.repository;

import com.example.updated_test.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    // Additional custom queries if needed
    UserInfo findByEmail(String email);
    boolean existsByUsername(String username);
}
