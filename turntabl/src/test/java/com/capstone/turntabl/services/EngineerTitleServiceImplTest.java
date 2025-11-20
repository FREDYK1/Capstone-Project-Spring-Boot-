package com.capstone.turntabl.services;

import com.capstone.turntabl.dto.EngineerTitleDto;
import com.capstone.turntabl.entity.EngineerTitle;
import com.capstone.turntabl.exception.ResourceNotFoundException;
import com.capstone.turntabl.services.impl.EngineerTitleServiceImpl;
import com.capstone.turntabl.repository.EngineerTitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EngineerTitleServiceImplTest {

    @Mock
    private EngineerTitleRepository repository;

    private EngineerTitleServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EngineerTitleServiceImpl(repository);
    }

    private EngineerTitle makeEntity(Long id, String title, String code, String desc, Boolean isActive) {
        return new EngineerTitle(id, title, code, desc, isActive, Instant.now(), Instant.now());
    }

    private EngineerTitleDto makeDto(Long id, String title, String code, String desc, Boolean isActive) {
        return new EngineerTitleDto(id, title, code, desc, isActive);
    }

    @Test
    void createEngineerTitle_happyPath_returnsCreatedDto() {
        EngineerTitleDto request = makeDto(null, "Senior Engineer", "SE01", "Senior level", true);
        EngineerTitle saved = makeEntity(1L, "Senior Engineer", "SE01", "Senior level", true);

        when(repository.save(any(EngineerTitle.class))).thenReturn(saved);

        EngineerTitleDto result = service.createEngineerTitle(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Senior Engineer", result.getTitle());
        assertEquals("SE01", result.getTitleCode());
        assertTrue(result.getIsActive());

        ArgumentCaptor<EngineerTitle> captor = ArgumentCaptor.forClass(EngineerTitle.class);
        verify(repository, times(1)).save(captor.capture());
        EngineerTitle passed = captor.getValue();
        assertEquals(request.getTitle(), passed.getTitle());
    }

    @Test
    void createEngineerTitle_repositoryThrows_DataAccess_wrappedAsRuntime() {
        EngineerTitleDto request = makeDto(null, "X", "X01", "desc", true);
        when(repository.save(any(EngineerTitle.class))).thenThrow(new DataAccessResourceFailureException("boom"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.createEngineerTitle(request));
        assertTrue(ex.getMessage().contains("Failed to save"));
    }

    @Test
    void getAllEngineerTitles_returnsList() {
        EngineerTitle one = makeEntity(1L, "A", "A01", "d1", true);
        EngineerTitle two = makeEntity(2L, "B", "B01", "d2", false);
        when(repository.findAll()).thenReturn(Arrays.asList(one, two));

        List<EngineerTitleDto> all = service.getAllEngineerTitles();
        assertEquals(2, all.size());
        assertEquals("A", all.get(0).getTitle());
        assertEquals("B", all.get(1).getTitle());
    }

    @Test
    void getAllEngineerTitles_repositoryThrows_wrapped() {
        when(repository.findAll()).thenThrow(new DataAccessResourceFailureException("boom"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getAllEngineerTitles());
        assertTrue(ex.getMessage().contains("Failed to fetch"));
    }

    @Test
    void getEngineerTitleById_found_returnsDto() {
        EngineerTitle entity = makeEntity(5L, "C", "C01", "d", true);
        when(repository.findById(5L)).thenReturn(Optional.of(entity));

        EngineerTitleDto dto = service.getEngineerTitleById(5L);
        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("C", dto.getTitle());
    }

    @Test
    void getEngineerTitleById_notFound_throwsResourceNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getEngineerTitleById(99L));
    }

    @Test
    void updateEngineerTitle_happyPath_updatesAndReturns() {
        EngineerTitle existing = makeEntity(10L, "Old", "OLD01", "old desc", true);
        EngineerTitle saved = makeEntity(10L, "New", "NEW01", "new desc", false);
        EngineerTitleDto updateDto = makeDto(null, "New", "NEW01", "new desc", false);

        when(repository.findById(10L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(saved);

        EngineerTitleDto result = service.updateEngineerTitle(10L, updateDto);
        assertEquals(10L, result.getId());
        assertEquals("New", result.getTitle());
        assertEquals("NEW01", result.getTitleCode());
        assertFalse(result.getIsActive());

        verify(repository, times(1)).findById(10L);
        verify(repository, times(1)).save(existing);
    }

    @Test
    void updateEngineerTitle_notFound_throwsResourceNotFound() {
        when(repository.findById(123L)).thenReturn(Optional.empty());
        EngineerTitleDto dto = makeDto(null, "x", "x", "x", true);
        assertThrows(ResourceNotFoundException.class, () -> service.updateEngineerTitle(123L, dto));
    }

    @Test
    void deleteEngineerTitle_happyPath_deletes() {
        EngineerTitle existing = makeEntity(7L, "Del", "D01", "d", true);
        when(repository.findById(7L)).thenReturn(Optional.of(existing));

        service.deleteEngineerTitle(7L);

        verify(repository, times(1)).delete(existing);
    }

    @Test
    void deleteEngineerTitle_notFound_throwsResourceNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteEngineerTitle(999L));
    }

    @Test
    void deleteEngineerTitle_repositoryThrows_wrapped() {
        EngineerTitle existing = makeEntity(8L, "X", "X01", "d", true);
        when(repository.findById(8L)).thenReturn(Optional.of(existing));
        doThrow(new DataAccessResourceFailureException("boom")).when(repository).delete(existing);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteEngineerTitle(8L));
        assertTrue(ex.getMessage().contains("Failed to delete"));
    }
}
