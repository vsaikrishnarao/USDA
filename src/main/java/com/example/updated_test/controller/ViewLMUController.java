package com.example.updated_test.controller;

import com.example.updated_test.model.LMU;
import com.example.updated_test.repository.LMURepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lmu")
public class ViewLMUController {

    private final LMURepository lmuRepository;

    public ViewLMUController(LMURepository lmuRepository) {
        this.lmuRepository = lmuRepository;
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllLMUs() {
        List<Object[]> rows = lmuRepository.findAllLMUsWithGeoJSON();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("gid", row[0]);
            map.put("label", row[1]);
            map.put("type", row[2]);
            map.put("dateValid", row[3]);
            map.put("grzblFactor", row[4]);
            map.put("areaHectares", row[5]); // ✅ We send GeoJSON as a String
            result.add(map);
        }
        return result;
    }

    @PostMapping("/edit")
    @Transactional
    public String editLMU(@RequestBody Map<String, Object> updatedData) {
        Integer gid = (Integer) updatedData.get("gid");
        Optional<LMU> optionalLmu = lmuRepository.findById(gid);

        if (optionalLmu.isEmpty()) {
            return "LMU with the given GID not found.";
        }

        String label = (String) updatedData.get("label");
        String type = (String) updatedData.get("type");
        String dateValid = (String) updatedData.get("dateValid");
        BigDecimal grzblFactor = null;

        Object grzblObj = updatedData.get("grzblFactor");
        if (grzblObj instanceof Number) {
            grzblFactor = BigDecimal.valueOf(((Number) grzblObj).doubleValue());
        }

        lmuRepository.updateLMU(gid, label, type, grzblFactor, dateValid);

        return "LMU updated successfully.";
    }

}
