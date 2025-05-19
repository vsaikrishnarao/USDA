package com.example.updated_test.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Polygon;
import java.math.BigDecimal;

@Entity
@Table(name = "lmu")
public class LMU {

    @Id
    @Column(name = "gid", nullable = false)
    private Integer gid;

    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    /**
     * Storing the Postgres daterange as a String.
     * Example: "[2022-01-01,2024-01-01)"
     */
    @Column(name = "date_valid", nullable = false, columnDefinition = "daterange")
    private String dateValid;

    @Column(name = "grzbl_f", precision = 4, scale = 2, nullable = false)
    private BigDecimal grzblFactor;

    @Column(name = "geom", nullable = false, columnDefinition = "geometry(Polygon,32614)")
    private Polygon geom;

    @Column(name = "area_ha", precision = 10, scale = 2, insertable = false, updatable = false)
    private BigDecimal areaHectares;

    // Default constructor
    public LMU() {}

    // Parameterized constructor
    public LMU(Integer gid, String label, String type, String dateValid,
               BigDecimal grzblFactor, Polygon geom, BigDecimal areaHectares) {
        this.gid = gid;
        this.label = label;
        this.type = type;
        this.dateValid = dateValid;
        this.grzblFactor = grzblFactor;
        this.geom = geom;
        this.areaHectares = areaHectares;
    }

    // Getters and Setters
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateValid() {
        return dateValid;
    }

    public void setDateValid(String dateValid) {
        this.dateValid = dateValid;
    }

    public BigDecimal getGrzblFactor() {
        return grzblFactor;
    }

    public void setGrzblFactor(BigDecimal grzblFactor) {
        this.grzblFactor = grzblFactor;
    }

    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geom) {
        this.geom = geom;
    }

    public BigDecimal getAreaHectares() {
        return areaHectares;
    }
}
