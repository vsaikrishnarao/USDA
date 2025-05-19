package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lmu_forage_crop_alloc")
public class LmuForageCropAlloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "lmu_id")
    private Integer lmuId;

    @Column(name = "fc_id")
    private Integer fcId;

    /**
     * Storing the Postgres daterange as a String.
     * Example: "[2023-01-01,2025-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    @Column(name = "e_for_pro_lbac", nullable = false)
    private Integer eForProLbac;

    @Column(name = "targ_use_f", nullable = false)
    private Double targUseF;

    @Column(name = "has_h2o", nullable = false)
    private Boolean hasH2o;

    @Column(name = "has_n_shd", nullable = false)
    private Boolean hasNShd;

    @Column(name = "has_shd_str", nullable = false)
    private Boolean hasShdStr;

    @Column(name = "note", columnDefinition = "text")
    private String note;

    // Default constructor
    public LmuForageCropAlloc() {}

    // Parameterized constructor
    public LmuForageCropAlloc(Integer id, Integer lmuId, Integer fcId, String dateValid,
                              Integer eForProLbac, Double targUseF, Boolean hasH2o,
                              Boolean hasNShd, Boolean hasShdStr, String note) {
        this.id = id;
        this.lmuId = lmuId;
        this.fcId = fcId;
        this.dateValid = dateValid;
        this.eForProLbac = eForProLbac;
        this.targUseF = targUseF;
        this.hasH2o = hasH2o;
        this.hasNShd = hasNShd;
        this.hasShdStr = hasShdStr;
        this.note = note;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLmuId() {
        return lmuId;
    }

    public void setLmuId(Integer lmuId) {
        this.lmuId = lmuId;
    }

    public Integer getFcId() {
        return fcId;
    }

    public void setFcId(Integer fcId) {
        this.fcId = fcId;
    }

    public String getDateValid() {
        return dateValid;
    }

    public void setDateValid(String dateValid) {
        this.dateValid = dateValid;
    }

    public Integer getEForProLbac() {
        return eForProLbac;
    }

    public void setEForProLbac(Integer eForProLbac) {
        this.eForProLbac = eForProLbac;
    }

    public Double getTargUseF() {
        return targUseF;
    }

    public void setTargUseF(Double targUseF) {
        this.targUseF = targUseF;
    }

    public Boolean getHasH2o() {
        return hasH2o;
    }

    public void setHasH2o(Boolean hasH2o) {
        this.hasH2o = hasH2o;
    }

    public Boolean getHasNShd() {
        return hasNShd;
    }

    public void setHasNShd(Boolean hasNShd) {
        this.hasNShd = hasNShd;
    }

    public Boolean getHasShdStr() {
        return hasShdStr;
    }

    public void setHasShdStr(Boolean hasShdStr) {
        this.hasShdStr = hasShdStr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
