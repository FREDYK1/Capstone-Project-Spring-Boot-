package com.capstone.turntabl.services.impl;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.entity.EngineerTitle;
import com.capstone.turntabl.exception.ResourceNotFoundException;
import com.capstone.turntabl.mapper.EngineerTitleMapper;
import com.capstone.turntabl.repository.EngineerTitleRepository;
import com.capstone.turntabl.services.EngineerTitleService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EngineerTitleServiceImpl implements EngineerTitleService {
    private final EngineerTitleRepository engineerTitleRepository;

    public EngineerTitleServiceImpl(EngineerTitleRepository engineerTitleRepository) {
        this.engineerTitleRepository = engineerTitleRepository;
    }

    @Override
    public EngineerTitleDto createEngineerTitle(EngineerTitleDto engineerTitleDto) {
        try {
            EngineerTitle engineer = EngineerTitleMapper.mapToEngineerTitle(engineerTitleDto);
            EngineerTitle savedEngineer = engineerTitleRepository.save(engineer);
            return EngineerTitleMapper.mapToEngineerTitleDto(savedEngineer);
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to save Engineer Title: " + dae.getMessage(), dae);
        }
    }

    @Override
    public void deleteEngineerTitle(Long id) {
        try {
            EngineerTitle title = engineerTitleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Engineer Title not found with id: " + id));
            engineerTitleRepository.delete(title);
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to delete Engineer Title: " + dae.getMessage(), dae);
        }
    }

    @Override
    public List<EngineerTitleDto> getAllEngineerTitles() {
        try {
            return engineerTitleRepository.findAll().stream()
                    .map(EngineerTitleMapper::mapToEngineerTitleDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to fetch Engineer Titles: " + dae.getMessage(), dae);
        }
    }

    @Override
    public EngineerTitleDto getEngineerTitleById(Long id) {
        try {
            EngineerTitle title = engineerTitleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Engineer Title not found with id: " + id));
            return EngineerTitleMapper.mapToEngineerTitleDto(title);
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to fetch Engineer Title: " + dae.getMessage(), dae);
        }
    }

    @Override
    public EngineerTitleDto updateEngineerTitle(Long id, EngineerTitleDto engineerTitleDto) {
        try {
            EngineerTitle currentTitle = engineerTitleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Engineer Title not found with id: " + id));
            currentTitle.setTitle(engineerTitleDto.getTitle());
            currentTitle.setTitleCode(engineerTitleDto.getTitleCode());
            currentTitle.setDescription(engineerTitleDto.getDescription());
            currentTitle.setIsActive(engineerTitleDto.getIsActive());

            EngineerTitle newTitle = engineerTitleRepository.save(currentTitle);

            return EngineerTitleMapper.mapToEngineerTitleDto(newTitle);
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to fetch Engineer Title: " + dae.getMessage(), dae);
        }
    }
}
