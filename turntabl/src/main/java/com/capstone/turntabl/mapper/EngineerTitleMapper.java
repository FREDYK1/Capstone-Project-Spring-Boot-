package com.capstone.turntabl.mapper;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.entity.EngineerTitle;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class EngineerTitleMapper {
    public static EngineerTitleDto mapToEngineerTitleDto(EngineerTitle engineerTitle){
        return new EngineerTitleDto(
                engineerTitle.getId(),
                engineerTitle.getTitle(),
                engineerTitle.getTitleCode(),
                engineerTitle.getDescription(),
                engineerTitle.getIsActive()
        );
    }

    public static EngineerTitle mapToEngineerTitle(EngineerTitleDto engineerTitleDto) {
        Instant now = Instant.now();
        return new EngineerTitle(
                engineerTitleDto.getId(),
                engineerTitleDto.getTitle(),
                engineerTitleDto.getTitleCode(),
                engineerTitleDto.getDescription(),
                engineerTitleDto.getIsActive(),
                now,
                now
        );
    }
}
