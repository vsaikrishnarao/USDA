package com.example.updated_test.controller;

import com.example.updated_test.model.AniClass;
import com.example.updated_test.repository.AniClassRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class AniClassController {

    private final AniClassRepository aniClassRepository;

    public AniClassController(AniClassRepository aniClassRepository) {
        this.aniClassRepository = aniClassRepository;
    }

    /**
     * Get all animal classes.
     * Example: GET http://localhost:8095/api/classes/all
     */
    @GetMapping("/all")
    public List<AniClass> getAllClasses() {
        return aniClassRepository.findAll();
    }

    /**
     * Add a new animal class.
     * Example: POST http://localhost:8095/api/classes/add
     */
    @PostMapping("/add")
    @Transactional
    public String addClass(@RequestBody AniClass newClass) {
        AniClass existing = aniClassRepository.findByLabel(newClass.getLabel());
        if (existing != null) {
            return "Class with the same label already exists.";
        }

        aniClassRepository.save(newClass);
        return "Class added successfully.";
    }

    /**
     * Edit an existing animal class.
     * Example: POST http://localhost:8095/api/classes/edit
     */
    @PostMapping("/edit")
    @Transactional
    public String editClass(@RequestBody AniClass updatedClass) {
        AniClass existing = aniClassRepository.findByLabel(updatedClass.getLabel());
        if (existing != null && !existing.getId().equals(updatedClass.getId())) {
            return "Another class with the same label already exists.";
        }

        aniClassRepository.save(updatedClass);
        return "Class updated successfully.";
    }
}
