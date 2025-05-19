package com.example.updated_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "forage_crops")
public class ForageCrops {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "label", nullable = false, length = 50)
    private String label;

    @Column(name = "fy_start", nullable = false, length = 5)
    private String fyStart;

    @Column(name = "fy_end", nullable = false, length = 5)
    private String fyEnd;

    // Storing numeric[] as String for simplicity (e.g., "{0.25,0.75}")
    @Column(name = "frac_grown", nullable = false, columnDefinition = "numeric[]")
    private String fracGrown;

    @Column(name = "frac_fy", nullable = false, columnDefinition = "numeric[]")
    private String fracFy;

    // Default constructor
    public ForageCrops() {}

    // Parameterized constructor
    public ForageCrops(Integer id, String label, String fyStart, String fyEnd,
                       String fracGrown, String fracFy) {
        this.id = id;
        this.label = label;
        this.fyStart = fyStart;
        this.fyEnd = fyEnd;
        this.fracGrown = fracGrown;
        this.fracFy = fracFy;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFyStart() {
        return fyStart;
    }

    public void setFyStart(String fyStart) {
        this.fyStart = fyStart;
    }

    public String getFyEnd() {
        return fyEnd;
    }

    public void setFyEnd(String fyEnd) {
        this.fyEnd = fyEnd;
    }

    public String getFracGrown() {
        return fracGrown;
    }

    public void setFracGrown(String fracGrown) {
        this.fracGrown = fracGrown;
    }

    public String getFracFy() {
        return fracFy;
    }

    public void setFracFy(String fracFy) {
        this.fracFy = fracFy;
    }
}
