package com.example.updated_test.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ani_weight")
public class AniWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ani_id")
    private Long aniId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "shrunk", nullable = false)
    private Boolean shrunk;

    @Column(name = "weight_lb", nullable = false)
    private Integer weightLb;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;

    // Default constructor
    public AniWeight() {}

    // Parameterized constructor
    public AniWeight(Integer id, Long aniId, LocalDate date, String type,
                     Boolean shrunk, Integer weightLb, String notes) {
        this.id = id;
        this.aniId = aniId;
        this.date = date;
        this.type = type;
        this.shrunk = shrunk;
        this.weightLb = weightLb;
        this.notes = notes;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getShrunk() {
        return shrunk;
    }

    public void setShrunk(Boolean shrunk) {
        this.shrunk = shrunk;
    }

    public Integer getWeightLb() {
        return weightLb;
    }

    public void setWeightLb(Integer weightLb) {
        this.weightLb = weightLb;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
