package com.capstone.turntabl.mapper;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.entity.EngineerTitle;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class EngineerTitleMapperTest {

    @Test
    void mapToEngineerTitleDto_roundTrip_preservesFields() {
        // original entity with all fields set
        Instant now = Instant.now();
        EngineerTitle original = new EngineerTitle(5L, "Senior", "S01", "Senior desc", true, now, now);

        EngineerTitleDto dto = EngineerTitleMapper.mapToEngineerTitleDto(original);
        EngineerTitle mapped = EngineerTitleMapper.mapToEngineerTitle(dto);

        assertEquals(original.getId(), mapped.getId());
        assertEquals(original.getTitle(), mapped.getTitle());
        assertEquals(original.getTitleCode(), mapped.getTitleCode());
        assertEquals(original.getDescription(), mapped.getDescription());
        assertEquals(original.getIsActive(), mapped.getIsActive());
    }

    @Test
    void mapToEngineerTitle_handlesNullFields() {
        // DTO with all nullable fields null
        EngineerTitleDto dto = new EngineerTitleDto(null, null, null, null, null);

        EngineerTitle mapped = EngineerTitleMapper.mapToEngineerTitle(dto);

        assertNull(mapped.getId());
        assertNull(mapped.getTitle());
        assertNull(mapped.getTitleCode());
        assertNull(mapped.getDescription());
        assertNull(mapped.getIsActive());
        // timestamps should be set by the mapper to now
        assertNotNull(mapped.getCreatedAt());
        assertNotNull(mapped.getUpdatedAt());
    }
}

