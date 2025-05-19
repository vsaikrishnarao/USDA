package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "angrp_lmu_assign")
public class AngrpLmuAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // Storing integer[] as String for simplicity (e.g., "{1,2,3}")
    @Column(name = "ag_id_lst", nullable = false, columnDefinition = "integer[]")
    private String agIdLst;

    @Column(name = "lmu_id_lst", nullable = false, columnDefinition = "integer[]")
    private String lmuIdLst;

    /**
     * Storing the Postgres daterange as a String.
     * Example: "[2022-01-01,2024-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    // Default constructor
    public AngrpLmuAssign() {}

    // Parameterized constructor
    public AngrpLmuAssign(Integer id, String agIdLst, String lmuIdLst, String dateValid) {
        this.id = id;
        this.agIdLst = agIdLst;
        this.lmuIdLst = lmuIdLst;
        this.dateValid = dateValid;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgIdLst() {
        return agIdLst;
    }

    public void setAgIdLst(String agIdLst) {
        this.agIdLst = agIdLst;
    }

    public String getLmuIdLst() {
        return lmuIdLst;
    }

    public void setLmuIdLst(String lmuIdLst) {
        this.lmuIdLst = lmuIdLst;
    }

    public String getDateValid() {
        return dateValid;
    }

    public void setDateValid(String dateValid) {
        this.dateValid = dateValid;
    }
}
