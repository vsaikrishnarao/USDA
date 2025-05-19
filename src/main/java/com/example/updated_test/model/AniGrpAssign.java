package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ani_grp_assign")
public class AniGrpAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "agid")
    private Integer agid;

    // Storing bigint[] as String for simplicity (e.g., "{101,102,103}")
    @Column(name = "ani_id_lst", nullable = false, columnDefinition = "bigint[]")
    private String aniIdLst;

    /**
     * Storing the Postgres daterange as a String.
     * Example: "[2023-01-01,2025-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    @Column(name = "in_ids")
    private Integer inIds;

    @Column(name = "note", columnDefinition = "text")
    private String note;

    // Default constructor
    public AniGrpAssign() {}

    // Parameterized constructor
    public AniGrpAssign(Integer id, Integer agid, String aniIdLst, String dateValid,
                        Integer inIds, String note) {
        this.id = id;
        this.agid = agid;
        this.aniIdLst = aniIdLst;
        this.dateValid = dateValid;
        this.inIds = inIds;
        this.note = note;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgid() {
        return agid;
    }

    public void setAgid(Integer agid) {
        this.agid = agid;
    }

    public String getAniIdLst() {
        return aniIdLst;
    }

    public void setAniIdLst(String aniIdLst) {
        this.aniIdLst = aniIdLst;
    }

    public String getDateValid() {
        return dateValid;
    }

    public void setDateValid(String dateValid) {
        this.dateValid = dateValid;
    }

    public Integer getInIds() {
        return inIds;
    }

    public void setInIds(Integer inIds) {
        this.inIds = inIds;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
