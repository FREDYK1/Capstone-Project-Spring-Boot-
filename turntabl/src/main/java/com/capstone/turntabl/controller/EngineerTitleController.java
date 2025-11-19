package com.capstone.turntabl.controller;

import com.capstone.turntabl.dto.EngineerTitleDto;
 import com.capstone.turntabl.services.EngineerTitleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/engineer-titles")
public class EngineerTitleController {
    private final EngineerTitleService engineerTitleService;

    public EngineerTitleController(EngineerTitleService engineerTitleService) {
        this.engineerTitleService = engineerTitleService;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EngineerTitleDto> createEngineerTitle(@RequestBody EngineerTitleDto engineerTitleDto) {
            EngineerTitleDto saved = engineerTitleService.createEngineerTitle(engineerTitleDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEngineerTitle(@PathVariable("id") Long id) {
        engineerTitleService.deleteEngineerTitle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EngineerTitleDto> getEngineerTitleById(@PathVariable("id") Long id) {
        EngineerTitleDto dto = engineerTitleService.getEngineerTitleById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EngineerTitleDto> updateEngineerTitle(@PathVariable("id") Long id, @RequestBody EngineerTitleDto engineerTitleDto) {
        EngineerTitleDto updatedDto = engineerTitleService.updateEngineerTitle(id, engineerTitleDto);
        return ResponseEntity.ok(updatedDto);
    }
}
