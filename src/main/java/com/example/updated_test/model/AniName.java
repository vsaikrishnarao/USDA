package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ani_name")
public class AniName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // Storing bigint[] as a String representation (e.g., "{101,102}")
    @Column(name = "ani_id", nullable = false, columnDefinition = "bigint[]")
    private String aniId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "loc", length = 20)
    private String loc;

    @Column(name = "label", nullable = false, length = 100)
    private String label;

    @Column(name = "tag_color", length = 20)
    private String tagColor;

    /**
     * Storing the Postgres daterange as a String.
     * Example: "[2022-01-01,2025-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    @Column(name = "note", columnDefinition = "text")
    private String note;

    // Default constructor
    public AniName() {}

    // Parameterized constructor
    public AniName(Integer id, String aniId, String type, String loc, String label,
                   String tagColor, String dateValid, String note) {
        this.id = id;
        this.aniId = aniId;
        this.type = type;
        this.loc = loc;
        this.label = label;
        this.tagColor = tagColor;
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

    public String getAniId() {
        return aniId;
    }

    public void setAniId(String aniId) {
        this.aniId = aniId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
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
