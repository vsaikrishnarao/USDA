package com.example.updated_test.repository;

import com.example.updated_test.dto.LmuSendDTO;
import com.example.updated_test.model.LMU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LMURepository extends JpaRepository<LMU, Integer> {

    @Query(value = """
        SELECT 
            gid, label, type, ST_AsGeoJSON(geom), area_ha,
            name_grp_short_list::text,
            class_label_list::text,
            total_aue_grp, aud_grz, no_of_days, forage
        FROM lmu_send_1
        """, nativeQuery = true)
    List<Object[]> fetchLmuSendNamedRaw();

    default List<LmuSendDTO> getNamedDTOs() {
        return fetchLmuSendNamedRaw().stream().map(row -> new LmuSendDTO(
                (Integer) row[0],
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (java.math.BigDecimal) row[4],
                (String) row[5],
                (String) row[6],
                (java.math.BigDecimal) row[7],
                (java.math.BigDecimal) row[8],
                (java.math.BigDecimal) row[9],
                (String) row[10]
        )).toList();
    }

            @Query(value = """
            SELECT 
                gid, 
                label, 
                type, 
                date_valid, 
                grzbl_f as grzblFactor,
                area_ha as area_ha
            FROM lmu
        """, nativeQuery = true)
            List<Object[]> findAllLMUsWithGeoJSON();

    @Modifying
    @Query(value = """
    UPDATE lmu
    SET label = :label,
        type = :type,
        grzbl_f = :grzblFactor,
        date_valid = CAST(:dateValid AS daterange)
    WHERE gid = :gid
""", nativeQuery = true)
    void updateLMU(
            @Param("gid") Integer gid,
            @Param("label") String label,
            @Param("type") String type,
            @Param("grzblFactor") BigDecimal grzblFactor,
            @Param("dateValid") String dateValid
    );


}
