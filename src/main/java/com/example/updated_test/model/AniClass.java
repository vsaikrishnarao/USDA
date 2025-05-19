package com.example.updated_test.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ani_class")
public class AniClass {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "label", length = 50)
    private String label;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "aue", precision = 10, scale = 2)
    private BigDecimal aue;

    // Default constructor
    public AniClass() {}

    // Parameterized constructor
    public AniClass(Long id, String label, String description, BigDecimal aue) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.aue = aue;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAue() {
        return aue;
    }

    public void setAue(BigDecimal aue) {
        this.aue = aue;
    }
}
