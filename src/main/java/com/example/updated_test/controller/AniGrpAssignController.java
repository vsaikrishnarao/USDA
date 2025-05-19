package com.example.updated_test.controller;

import com.example.updated_test.dto.AniGrpAssignDTO;
import com.example.updated_test.repository.AniGrpAssignRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/group")
public class AniGrpAssignController {

    private final AniGrpAssignRepository repository;

    public AniGrpAssignController(AniGrpAssignRepository repository) {
        this.repository = repository;
    }

    /**
     * POST endpoint to fetch group assignments for selected group IDs.
     * URL: /api/group/assignments/by-ids
     */
    @PostMapping("/assignments/by-ids")
    public List<AniGrpAssignDTO> getAssignmentsByIds(@RequestBody List<Integer> groupIds) {
        return repository.fetchAssignmentsByGroupIds(groupIds).stream()
                .map(row -> {
                    Integer agid = (Integer) row[0];
                    List<Long> aniIds = parseLongArray((String) row[1]);
                    List<Long> clsIds = parseLongArray((String) row[2]);

                    List<List<Long>> pairs = new ArrayList<>();
                    for (int i = 0; i < Math.min(aniIds.size(), clsIds.size()); i++) {
                        pairs.add(List.of(aniIds.get(i), clsIds.get(i)));
                    }

                    return new AniGrpAssignDTO(
                            agid,
                            pairs,
                            ((Number) row[3]).longValue(),
                            (BigDecimal) row[4]
                    );
                }).collect(Collectors.toList());
    }

    private List<Long> parseLongArray(String arrayStr) {
        if (arrayStr == null || arrayStr.isBlank()) return List.of();
        return Arrays.stream(arrayStr.replaceAll("[{}]", "").split(","))
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
