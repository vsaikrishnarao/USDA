package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ani_grp")
public class AniGrp {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name_grp_short", nullable = false, length = 50)
    private String nameGrpShort;

    @Column(name = "name_grp_long", nullable = false, length = 100)
    private String nameGrpLong;

    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Storing the Postgres daterange as a String.
     * Example: "[2021-01-01,2025-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    // Default constructor
    public AniGrp() {}

    // Parameterized constructor
    public AniGrp(Integer id, String nameGrpShort, String nameGrpLong,
                  Boolean active, String dateValid) {
        this.id = id;
        this.nameGrpShort = nameGrpShort;
        this.nameGrpLong = nameGrpLong;
        this.active = active;
        this.dateValid = dateValid;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameGrpShort() {
        return nameGrpShort;
    }

    public void setNameGrpShort(String nameGrpShort) {
        this.nameGrpShort = nameGrpShort;
    }

    public String getNameGrpLong() {
        return nameGrpLong;
    }

    public void setNameGrpLong(String nameGrpLong) {
        this.nameGrpLong = nameGrpLong;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDateValid() {
        return dateValid;
    }

    public void setDateValid(String dateValid) {
        this.dateValid = dateValid;
    }
}
