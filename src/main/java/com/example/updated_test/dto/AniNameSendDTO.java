package com.example.updated_test.dto;

import java.time.LocalDate;

public class AniNameSendDTO {

    private Long aniId;
    private LocalDate dob;
    private char sex;
    private Boolean intact;
    private String owner;
    private String nameType;
    private String label;

    public AniNameSendDTO() {}

    public AniNameSendDTO(Long aniId, LocalDate dob, char sex, Boolean intact, String owner,
                          String nameType, String label) {
        this.aniId = aniId;
        this.dob = dob;
        this.sex = sex;
        this.intact = intact;
        this.owner = owner;
        this.nameType = nameType;
        this.label = label;
    }

    // Getters and Setters
    public Long getAniId() { return aniId; }
    public void setAniId(Long aniId) { this.aniId = aniId; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public char getSex() { return sex; }
    public void setSex(char sex) { this.sex = sex; }

    public Boolean getIntact() { return intact; }
    public void setIntact(Boolean intact) { this.intact = intact; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getNameType() { return nameType; }
    public void setNameType(String nameType) { this.nameType = nameType; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

}
