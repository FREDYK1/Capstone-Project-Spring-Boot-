package com.capstone.turntabl.mapper;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.entity.EngineerTitle;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EngineerTitleMapperTest {

    @Test
    void mapToEngineerTitleDto_roundTrip_preservesFields() {
        Instant now = Instant.parse("2025-01-01T00:00:00Z");
        EngineerTitle entity = new EngineerTitle(
                10L,
                "Senior Engineer",
                "SE01",
                "Senior level",
                Boolean.TRUE,
                now,
                now
        );

        EngineerTitleDto dto = EngineerTitleMapper.mapToEngineerTitleDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getTitleCode(), dto.getTitleCode());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getIsActive(), dto.getIsActive());
    }

    @Test
    void mapToEngineerTitle_setsTimestamps_and_preservesFields() {
        EngineerTitleDto dto = new EngineerTitleDto(
                null,
                "Junior Engineer",
                "JE01",
                "Junior level",
                Boolean.FALSE
        );

        EngineerTitle entity = EngineerTitleMapper.mapToEngineerTitle(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getTitleCode(), entity.getTitleCode());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getIsActive(), entity.getIsActive());
        assertNotNull(entity.getCreatedAt(), "createdAt should be set by mapper");
        assertNotNull(entity.getUpdatedAt(), "updatedAt should be set by mapper");
        // createdAt and updatedAt should be close to now
        Instant now = Instant.now();
        assertFalse(entity.getCreatedAt().isAfter(now));
        assertFalse(entity.getUpdatedAt().isAfter(now));
    }

    @Test
    void mapToEngineerTitle_handlesNullDtoFields() {
        EngineerTitleDto dto = new EngineerTitleDto();
        dto.setId(null);
        dto.setTitle("T");
        dto.setTitleCode("TC");
        dto.setDescription(null);
        dto.setIsActive(null);

        EngineerTitle entity = EngineerTitleMapper.mapToEngineerTitle(dto);

        assertNotNull(entity);
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getTitleCode(), entity.getTitleCode());
        assertNull(entity.getDescription());
        assertNull(entity.getIsActive());
    }

}

