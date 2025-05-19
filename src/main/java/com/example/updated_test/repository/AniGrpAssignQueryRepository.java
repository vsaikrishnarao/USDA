package com.example.updated_test.repository;

import com.example.updated_test.model.AniGrpAssign;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AniGrpAssignQueryRepository extends JpaRepository<AniGrpAssign, Integer> {

    @Modifying
    @Query(value = "UPDATE ani_grp_assign " +
            "SET date_valid = daterange(lower(date_valid), CURRENT_DATE, '[)') " +
            "WHERE agid = :groupId AND upper(date_valid) IS NULL", nativeQuery = true)
    void expireActiveAssignment(@Param("groupId") Integer groupId);

    @Query(value = "SELECT unnest(ani_id_lst) " +
            "FROM ani_grp_assign " +
            "WHERE agid = :groupId AND upper(date_valid) IS NULL", nativeQuery = true)
    List<Long> findActiveAnimalIds(@Param("groupId") Integer groupId);

    @Modifying
    @Query(value = "INSERT INTO ani_grp_assign (agid, ani_id_lst, date_valid) " +
            "VALUES (:groupId, CAST(:animalArray AS bigint[]), CAST(:dateRange AS daterange))", nativeQuery = true)
    void insertNewAssignment(@Param("groupId") Integer groupId,
                             @Param("animalArray") String animalArray,
                             @Param("dateRange") String dateRange);
}
