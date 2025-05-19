package com.example.updated_test.repository;

import com.example.updated_test.model.AniGrp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AniGrpRepository extends JpaRepository<AniGrp, Integer> {
    @Query("SELECT g.id FROM AniGrp g WHERE g.nameGrpShort = :shortName")
    Integer findIdByShortName(@Param("shortName") String shortName);

    @Modifying
    @Query(value = "INSERT INTO ani_grp (id, name_grp_short, name_grp_long, active, date_valid) " +
            "VALUES (:id, :shortName, :longName, :active, CAST(:dateValid AS daterange))",
            nativeQuery = true)
    void insertGroup(@Param("id") Integer id,
                     @Param("shortName") String shortName,
                     @Param("longName") String longName,
                     @Param("active") Boolean active,
                     @Param("dateValid") String dateValid);
    @Modifying
    @Query(value = "UPDATE ani_grp SET " +
            "name_grp_short = :shortName, " +
            "name_grp_long = :longName, " +
            "active = :active, " +
            "date_valid = CAST(:dateValid AS daterange) " +
            "WHERE id = :id", nativeQuery = true)
    void updateGroup(@Param("id") Integer id,
                     @Param("shortName") String shortName,
                     @Param("longName") String longName,
                     @Param("active") Boolean active,
                     @Param("dateValid") String dateValid);

}
