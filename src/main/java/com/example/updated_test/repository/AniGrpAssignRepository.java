package com.example.updated_test.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AniGrpAssignRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> fetchAssignmentsByGroupIds(List<Integer> groupIds) {
        Query query = entityManager.createNativeQuery("""
        SELECT DISTINCT ON (agid)
            agid,
            ani_id_lst::text,
            cls_ids::text,
            head,
            aue_grp
        FROM mv_ani_grp_assign
        WHERE upper(date_valid) IS NULL
          AND agid IN (:groupIds)
        ORDER BY agid, date_valid DESC
    """);
        query.setParameter("groupIds", groupIds);
        return query.getResultList();
    }

}
