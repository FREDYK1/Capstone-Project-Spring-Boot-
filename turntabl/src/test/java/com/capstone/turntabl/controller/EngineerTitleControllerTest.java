package com.capstone.turntabl.controller;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.exception.ResourceNotFoundException;
import com.capstone.turntabl.services.EngineerTitleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EngineerTitleController.class)
public class EngineerTitleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EngineerTitleService engineerTitleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEngineerTitle_shouldReturnCreatedAndBody() throws Exception {
        EngineerTitleDto request = new EngineerTitleDto(null, "Senior Engineer", "SE01", "Senior level", true);
        EngineerTitleDto saved = new EngineerTitleDto(1L, "Senior Engineer", "SE01", "Senior level", true);

        when(engineerTitleService.createEngineerTitle(any(EngineerTitleDto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/engineer-titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Senior Engineer"));

        verify(engineerTitleService).createEngineerTitle(any(EngineerTitleDto.class));
    }

    @Test
    void deleteEngineerTitle_shouldReturnNoContent() throws Exception {
        // Arrange: service.deleteEngineerTitle is void; stub to do nothing
        doNothing().when(engineerTitleService).deleteEngineerTitle(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/engineer-titles/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verify service was called
        verify(engineerTitleService).deleteEngineerTitle(1L);
    }

    @Test
    void getEngineerTitleById_shouldReturnOkAndBody() throws Exception {
        EngineerTitleDto dto = new EngineerTitleDto(2L, "Mid", "M01", "Mid level", true);
        when(engineerTitleService.getEngineerTitleById(2L)).thenReturn(dto);

        mockMvc.perform(get("/api/engineer-titles/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Mid"));

        verify(engineerTitleService).getEngineerTitleById(2L);
    }

    @Test
    void getEngineerTitleById_notFound_returns404() throws Exception {
        when(engineerTitleService.getEngineerTitleById(99L)).thenThrow(new ResourceNotFoundException("Engineer Title not found with id: 99"));

        mockMvc.perform(get("/api/engineer-titles/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Engineer Title not found with id: 99"));
    }

    @Test
    void getAllEngineerTitles_noFilter_returnsAll() throws Exception {
        EngineerTitleDto a = new EngineerTitleDto(1L, "A", "A01", "d1", true);
        EngineerTitleDto b = new EngineerTitleDto(2L, "B", "B01", "d2", false);
        List<EngineerTitleDto> list = Arrays.asList(a, b);
        when(engineerTitleService.getAllEngineerTitles()).thenReturn(list);

        mockMvc.perform(get("/api/engineer-titles/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("A"))
                .andExpect(jsonPath("$[1].title").value("B"));

        verify(engineerTitleService).getAllEngineerTitles();
    }

    @Test
    void getAllEngineerTitles_withFilter_isActiveTrue_returnsOnlyActive() throws Exception {
        EngineerTitleDto a = new EngineerTitleDto(1L, "A", "A01", "d1", true);
        EngineerTitleDto b = new EngineerTitleDto(2L, "B", "B01", "d2", false);
        List<EngineerTitleDto> list = Arrays.asList(a, b);
        when(engineerTitleService.getAllEngineerTitles()).thenReturn(list);

        mockMvc.perform(get("/api/engineer-titles/all").param("isActive", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].isActive").value(true));

        verify(engineerTitleService).getAllEngineerTitles();
    }

    @Test
    void create_invalidPayload_returns400() throws Exception {
        // missing title and titleCode (both are @NotBlank)
        EngineerTitleDto request = new EngineerTitleDto(null, "", "", "desc", true);

        mockMvc.perform(post("/api/engineer-titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void create_wrongContentType_returns415() throws Exception {
        String body = "title=Senior Engineer&titleCode=SE01";

        mockMvc.perform(post("/api/engineer-titles")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.status").value(415));
    }

    @Test
    void updateEngineerTitle_shouldReturnOkAndUpdatedBody() throws Exception {
        EngineerTitleDto request = new EngineerTitleDto(null, "Updated", "U01", "updated desc", true);
        EngineerTitleDto updated = new EngineerTitleDto(3L, "Updated", "U01", "updated desc", true);

        when(engineerTitleService.updateEngineerTitle(eq(3L), any(EngineerTitleDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/engineer-titles/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Updated"));

        verify(engineerTitleService).updateEngineerTitle(eq(3L), any(EngineerTitleDto.class));
    }

    @Test
    void put_update_notFound_returns404() throws Exception {
        EngineerTitleDto request = new EngineerTitleDto(null, "X", "X01", "d", true);
        when(engineerTitleService.updateEngineerTitle(eq(42L), any(EngineerTitleDto.class))).thenThrow(new ResourceNotFoundException("Engineer Title not found with id: 42"));

        mockMvc.perform(put("/api/engineer-titles/{id}", 42L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Engineer Title not found with id: 42"));
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Engineer Title not found with id: 88")).when(engineerTitleService).deleteEngineerTitle(88L);

        mockMvc.perform(delete("/api/engineer-titles/{id}", 88L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Engineer Title not found with id: 88"));
    }
}
