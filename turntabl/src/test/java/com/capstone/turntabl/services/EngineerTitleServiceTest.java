package com.capstone.turntabl.services;


import com.capstone.turntabl.dto.EngineerTitleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class EngineerTitleServiceTest {
    @Mock
    private EngineerTitleService engineerTitleService;

    @Test
    public void createEngineerTitleTest(){
        EngineerTitleDto requestDto = mock(EngineerTitleDto.class);
        EngineerTitleDto expectedDto = mock(EngineerTitleDto.class);

        when(engineerTitleService.createEngineerTitle(any(EngineerTitleDto.class))).thenReturn(expectedDto);

        EngineerTitleDto actual =engineerTitleService.createEngineerTitle(requestDto);

        assertSame(expectedDto, actual, "Service should return the stubbed DTO");

        ArgumentCaptor<EngineerTitleDto> captor = ArgumentCaptor.forClass(EngineerTitleDto.class);
        verify(engineerTitleService, times(1)).createEngineerTitle(captor.capture());
        assertNotNull(captor.getValue(), "Captured argument should not be null");
        assertSame(requestDto, captor.getValue(), "Captured argument should be the passed request DTO");
    }
}
