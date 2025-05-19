package com.example.updated_test.controller;

import com.example.updated_test.dto.AniNameSendDTO;
import com.example.updated_test.repository.AniNameSendRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/animal")
public class AniNameSendController {

    private final AniNameSendRepository aniNameSendRepository;

    public AniNameSendController(AniNameSendRepository aniNameSendRepository) {
        this.aniNameSendRepository = aniNameSendRepository;
    }

    @GetMapping("/named-data")
    public List<AniNameSendDTO> getNamedAnimalData() {
        return aniNameSendRepository.getAniNameSendDTOs();
    }
}
