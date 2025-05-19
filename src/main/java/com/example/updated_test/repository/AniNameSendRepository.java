package com.example.updated_test.repository;

import com.example.updated_test.dto.AniNameSendDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AniNameSendRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AniNameSendDTO> getAniNameSendDTOs() {
        Query query = entityManager.createNativeQuery("""
            SELECT 
                ai.ani_id,
                ai.dob,
                ai.sex,
                ai.intact,
                ai.owner,
                ai.name_type,
                ai.label
            FROM ani_name_send ai
        """);

        List<Object[]> rows = query.getResultList();

        return rows.stream().map(row -> new AniNameSendDTO(
                ((Number) row[0]).longValue(),
                ((java.sql.Date) row[1]).toLocalDate(),
                ((Character) row[2]),                      // ✅ FIXED LINE
                (Boolean) row[3],
                (String) row[4],
                (String) row[5],
                (String) row[6]
        )).collect(Collectors.toList());


    }
}
