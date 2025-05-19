package com.example.updated_test.dto;

import java.math.BigDecimal;
import java.util.List;

public class AniGrpAssignDTO {

    private Integer agid;
    private List<List<Long>> animalClassPairs;
    private Long head;
    private BigDecimal aueGrp;

    public AniGrpAssignDTO() {}

    public AniGrpAssignDTO(Integer agid, List<List<Long>> animalClassPairs, Long head, BigDecimal aueGrp) {
        this.agid = agid;
        this.animalClassPairs = animalClassPairs;
        this.head = head;
        this.aueGrp = aueGrp;
    }

    public Integer getAgid() {
        return agid;
    }

    public void setAgid(Integer agid) {
        this.agid = agid;
    }

    public List<List<Long>> getAnimalClassPairs() {
        return animalClassPairs;
    }

    public void setAnimalClassPairs(List<List<Long>> animalClassPairs) {
        this.animalClassPairs = animalClassPairs;
    }

    public Long getHead() {
        return head;
    }

    public void setHead(Long head) {
        this.head = head;
    }

    public BigDecimal getAueGrp() {
        return aueGrp;
    }

    public void setAueGrp(BigDecimal aueGrp) {
        this.aueGrp = aueGrp;
    }
}
