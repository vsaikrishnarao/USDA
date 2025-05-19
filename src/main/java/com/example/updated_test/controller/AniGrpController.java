package com.example.updated_test.controller;

import com.example.updated_test.model.AniGrp;
import com.example.updated_test.repository.AniGrpRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/groups")
public class AniGrpController {

    private final AniGrpRepository aniGrpRepository;

    public AniGrpController(AniGrpRepository aniGrpRepository) {
        this.aniGrpRepository = aniGrpRepository;
    }

    /**
     * Get all animal groups.
     * You can call this from frontend to preload group name-id mapping.
     * Example: GET http://localhost:8095/api/groups/all
     */
    @GetMapping("/all")
    public List<AniGrp> getAllGroups() {
        return aniGrpRepository.findAll();
    }

    @PostMapping("/add")
    @Transactional
    public String addGroup(@RequestBody AniGrp newGroup) {
        Integer existingId = aniGrpRepository.findIdByShortName(newGroup.getNameGrpShort());
        if (existingId != null) {
            return "Group with the same short name already exists.";
        }

        aniGrpRepository.insertGroup(
                newGroup.getId(),
                newGroup.getNameGrpShort(),
                newGroup.getNameGrpLong(),
                newGroup.getActive(),
                newGroup.getDateValid()
        );

        return "Group added successfully.";
    }

    @PostMapping("/edit")
    @Transactional
    public String editGroup(@RequestBody AniGrp updatedGroup) {
        Integer existingId = aniGrpRepository.findIdByShortName(updatedGroup.getNameGrpShort());
        if (existingId != null && !existingId.equals(updatedGroup.getId())) {
            return "Another group with the same short name already exists.";
        }

        aniGrpRepository.updateGroup(
                updatedGroup.getId(),
                updatedGroup.getNameGrpShort(),
                updatedGroup.getNameGrpLong(),
                updatedGroup.getActive(),
                updatedGroup.getDateValid()
        );

        return "Group updated successfully.";
    }
}
