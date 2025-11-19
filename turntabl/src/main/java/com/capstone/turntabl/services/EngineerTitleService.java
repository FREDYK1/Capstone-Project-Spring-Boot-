package com.capstone.turntabl.services;

import com.capstone.turntabl.dto.EngineerTitleDto;

import java.util.List;


public interface EngineerTitleService {
    EngineerTitleDto createEngineerTitle(EngineerTitleDto engineerTitleDto);
    void deleteEngineerTitle(Long id);
    List<EngineerTitleDto> getAllEngineerTitles();
    EngineerTitleDto getEngineerTitleById(Long id);
    EngineerTitleDto updateEngineerTitle(Long id, EngineerTitleDto  engineerTitleDto);
}
