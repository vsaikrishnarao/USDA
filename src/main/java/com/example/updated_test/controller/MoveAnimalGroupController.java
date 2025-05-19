// ✅ MoveAnimalGroupController.java
package com.example.updated_test.controller;

import com.example.updated_test.dto.MoveAnimalsDTO;
import com.example.updated_test.repository.AniGrpAssignQueryRepository;
import com.example.updated_test.repository.AniGrpAssignRepository;
import com.example.updated_test.repository.AniGrpRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/group")
@CrossOrigin(origins = "*")
public class MoveAnimalGroupController {

    private final AniGrpAssignQueryRepository repository;

    private final AniGrpRepository groupRepository;

    public MoveAnimalGroupController(AniGrpAssignQueryRepository repository, AniGrpRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
    }


    @PostMapping("/move-animals")
    @Transactional
    public String moveAnimalsToNewGroup(@RequestBody MoveAnimalsDTO dto) {
        String sourceGroupShortName = dto.getSg();
        String targetGroupShortName = dto.getTg();
        List<Long> movingAnimalIds = dto.getAnimalIds();

        // Step 1: Resolve group IDs by short name
        Integer sourceGroupId = groupRepository.findIdByShortName(sourceGroupShortName);
        Integer targetGroupId = groupRepository.findIdByShortName(targetGroupShortName);

        if (sourceGroupId == null || targetGroupId == null) {
            return "One or both group names are invalid.";
        }

        LocalDate today = LocalDate.now();
        String todayRange = String.format("[%s,)", today);



        // Step 3: Fetch current animal lists from active assignments
        List<Long> currentSource = repository.findActiveAnimalIds(sourceGroupId);
        List<Long> currentTarget = repository.findActiveAnimalIds(targetGroupId);

        // Step 2: Expire existing assignments
        repository.expireActiveAssignment(sourceGroupId);
        repository.expireActiveAssignment(targetGroupId);

        List<Long> updatedSource = new ArrayList<>(currentSource);
        updatedSource.removeAll(movingAnimalIds);

        List<Long> updatedTarget = new ArrayList<>(currentTarget);
        updatedTarget.addAll(movingAnimalIds);

        // Step 4: Insert new assignments
        if(!updatedSource.isEmpty()) {
            repository.insertNewAssignment(sourceGroupId, toPostgresArray(updatedSource), todayRange);
        }
        repository.insertNewAssignment(targetGroupId, toPostgresArray(updatedTarget), todayRange);

        return "Animals moved successfully.";
    }

    private String toPostgresArray(List<Long> list) {
        return "{" + list.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
    }
}