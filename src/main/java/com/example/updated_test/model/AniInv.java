package com.example.updated_test.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ani_inv")
public class AniInv {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "dam_id")
    private Long damId;

    @Column(name = "sire_id")
    private Long sireId;

    @Column(name = "sex", nullable = false, length = 1)
    private char sex;

    @Column(name = "intact")
    private Boolean intact;

    @Column(name = "owner", length = 50)
    private String owner;

    /**
     * Storing the Postgres daterange as a String for simplicity.
     * Example value: "[2020-01-01,2025-01-01)"
     */
    @Column(name = "date_valid", columnDefinition = "daterange")
    private String dateValid;

    @Column(name = "note", columnDefinition = "text")
    private String note;

    // Default constructor
    public AniInv() {}

    // Parameterized constructor
    public AniInv(Long id, LocalDate dob, Long damId, Long sireId, char sex,
                  Boolean intact, String owner, String dateValid, String note) {
        this.id = id;
        this.dob = dob;
        this.damId = damId;
        this.sireId = sireId;
        this.sex = sex;
        this.intact = intact;
        this.owner = owner;
        this.dateValid = dateValid;
        this.note = note;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Long getDamId() {
        return damId;
    }

    public void setDamId(Long damId) {
        this.damId = damId;
    }

    public Long getSireId() {
        return sireId;
    }

    public void setSireId(Long sireId) {
        this.sireId = sireId;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        if (sex != 'm' && sex != 'f') {
            throw new IllegalArgumentException("Sex must be 'm' or 'f'");
        }
        this.sex = sex;
    }

    public Boolean getIntact() {
        return intact;
    }

    public void setIntact(Boolean intact) {
        this.intact = intact;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
