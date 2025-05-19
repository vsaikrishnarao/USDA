// ✅ MoveAnimalsDTO.java
package com.example.updated_test.dto;

import java.util.List;

public class MoveAnimalsDTO {
    private String sg; // source group short name
    private String tg; // target group short name
    private List<Long> animalIds;

    public MoveAnimalsDTO() {}

    public MoveAnimalsDTO(String sg, String tg, List<Long> animalIds) {
        this.sg = sg;
        this.tg = tg;
        this.animalIds = animalIds;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public List<Long> getAnimalIds() {
        return animalIds;
    }

    public void setAnimalIds(List<Long> animalIds) {
        this.animalIds = animalIds;
    }
}
