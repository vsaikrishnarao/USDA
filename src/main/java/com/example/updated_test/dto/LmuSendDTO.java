package com.example.updated_test.dto;

import java.math.BigDecimal;

public class LmuSendDTO {

    private Integer gid;
    private String label;
    private String type;
    private String geom;
    private BigDecimal areaHa;
    private String nameGrpShortList;   // Replaces ag_id_lst
    private String classLabelList;     // Replaces combined_cls_ids
    private BigDecimal totalAueGrp;
    private BigDecimal audGrz;
    private BigDecimal noOfDays;
    private String forage;

    public LmuSendDTO() {}

    public LmuSendDTO(Integer gid, String label, String type, String geom,
                      BigDecimal areaHa, String nameGrpShortList, String classLabelList,
                      BigDecimal totalAueGrp, BigDecimal audGrz, BigDecimal noOfDays, String forage) {
        this.gid = gid;
        this.label = label;
        this.type = type;
        this.geom = geom;
        this.areaHa = areaHa;
        this.nameGrpShortList = nameGrpShortList;
        this.classLabelList = classLabelList;
        this.totalAueGrp = totalAueGrp;
        this.audGrz = audGrz;
        this.noOfDays = noOfDays;
        this.forage = forage;
    }

    public Integer getGid() { return gid; }
    public void setGid(Integer gid) { this.gid = gid; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getGeom() { return geom; }
    public void setGeom(String geom) { this.geom = geom; }

    public BigDecimal getAreaHa() { return areaHa; }
    public void setAreaHa(BigDecimal areaHa) { this.areaHa = areaHa; }

    public String getNameGrpShortList() { return nameGrpShortList; }
    public void setNameGrpShortList(String nameGrpShortList) { this.nameGrpShortList = nameGrpShortList; }

    public String getClassLabelList() { return classLabelList; }
    public void setClassLabelList(String classLabelList) { this.classLabelList = classLabelList; }

    public BigDecimal getTotalAueGrp() { return totalAueGrp; }
    public void setTotalAueGrp(BigDecimal totalAueGrp) { this.totalAueGrp = totalAueGrp; }

    public BigDecimal getAudGrz() { return audGrz; }
    public void setAudGrz(BigDecimal audGrz) { this.audGrz = audGrz; }

    public BigDecimal getNoOfDays() { return noOfDays; }
    public void setNoOfDays(BigDecimal noOfDays) { this.noOfDays = noOfDays; }

    public String getForage() { return forage; }
    public void setForage(String forage) { this.forage = forage; }
}
