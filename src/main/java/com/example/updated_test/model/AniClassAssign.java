package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ani_class_assign")
public class AniClassAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ani_id")
    private Long aniId;

    @Column(name = "ani_cls_id")
    private Long aniClsId;

    /**
     * Storing the Postgres daterange as a String for simplicity.
     * Example: "[2021-01-01,2024-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    @Column(name = "note", columnDefinition = "text")
    private String note;

    // Default constructor
    public AniClassAssign() {}

    // Parameterized constructor
    public AniClassAssign(Integer id, Long aniId, Long aniClsId, String dateValid, String note) {
        this.id = id;
        this.aniId = aniId;
        this.aniClsId = aniClsId;
        this.dateValid = dateValid;
        this.note = note;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAniId() {
        return aniId;
    }

    public void setAniId(Long aniId) {
        this.aniId = aniId;
    }

    public Long getAniClsId() {
        return aniClsId;
    }

    public void setAniClsId(Long aniClsId) {
        this.aniClsId = aniClsId;
    }

    public String getDateValid() {
        return dateValid;
    }

    public void setDateValid(String dateValid) {
        this.dateValid = dateValid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
