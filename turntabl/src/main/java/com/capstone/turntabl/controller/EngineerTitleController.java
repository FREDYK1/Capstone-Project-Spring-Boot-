package com.capstone.turntabl.controller;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.services.EngineerTitleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/engineer-titles")
public class EngineerTitleController {
    private final EngineerTitleService engineerTitleService;

    public EngineerTitleController(EngineerTitleService engineerTitleService) {
        this.engineerTitleService = engineerTitleService;
    }

    @PostMapping
    public ResponseEntity<EngineerTitleDto> createEngineerTitle(@Valid @RequestBody EngineerTitleDto engineerTitleDto) {
        EngineerTitleDto saved = engineerTitleService.createEngineerTitle(engineerTitleDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EngineerTitleDto> deleteEngineerTitle(@PathVariable("id") Long id) {
        engineerTitleService.deleteEngineerTitle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngineerTitleDto> getEngineerTitleById(@PathVariable("id") Long id) {
        EngineerTitleDto dto = engineerTitleService.getEngineerTitleById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EngineerTitleDto>> getAllEngineerTitles(
            @RequestParam(value = "isActive", required = false) Boolean isActive) {
        List<EngineerTitleDto> titles = engineerTitleService.getAllEngineerTitles();
        if (isActive == null) {
            return ResponseEntity.ok(titles);
        }
        List<EngineerTitleDto> filtered = titles.stream()
                .filter(dto -> Boolean.TRUE.equals(dto.getIsActive()) == Boolean.TRUE.equals(isActive))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EngineerTitleDto> updateEngineerTitle(@PathVariable("id") Long id, @Valid @RequestBody EngineerTitleDto engineerTitleDto) {
        EngineerTitleDto updatedDto = engineerTitleService.updateEngineerTitle(id, engineerTitleDto);
        return ResponseEntity.ok(updatedDto);
    }
}
