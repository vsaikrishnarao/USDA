package com.example.updated_test.controller;

import com.example.updated_test.dto.LmuSendDTO;
import com.example.updated_test.repository.LMURepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/lmu")
public class LMUController {

    private final LMURepository lmuRepository;

    public LMUController(LMURepository lmuRepository) {
        this.lmuRepository = lmuRepository;
    }

    @GetMapping("/named-view")
    public List<LmuSendDTO> getLmuSendNamedData() {
        return lmuRepository.getNamedDTOs();
    }
}
