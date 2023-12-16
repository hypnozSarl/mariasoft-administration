package net.hypnoz.msadmin.service;


import jakarta.validation.ConstraintViolationException;
import net.hypnoz.msadmin.domain.Structures;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.mappers.StructuresMapper;
import net.hypnoz.msadmin.repository.StructuresRepository;
import net.hypnoz.msadmin.service.structres.StructureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StructureServiceTest {
    @InjectMocks
    StructureService structureService ; 

    @Mock
    private  StructuresRepository structuresRepository ;
    @Mock
    private StructuresMapper structuresMapper;

@Test
void shouldSuccessCreatingStructureWhenRaisonSocialeIsNull() {
    StructuresDto structuresDto = new StructuresDto();
    structuresDto.setStrRaisonSociale("Valid Raison Sociale");
    structuresDto.setStrSigle("Valid Sigle");
    Structures structures = new Structures();
        when(structuresMapper.toEntity(any(StructuresDto.class))).thenReturn(structures);
        when(structuresRepository.saveAndFlush(any(Structures.class))).thenReturn(structures);
        when(structuresMapper.toDto(any(Structures.class))).thenReturn(structuresDto);

        StructuresDto result = structureService.creationStructure(structuresDto);

        assertEquals(structuresDto, result);

        verify(structuresMapper).toEntity(structuresDto);
        verify(structuresRepository).saveAndFlush(structures);
        verify(structuresMapper).toDto(structures);
    }

    @Test
    void shouldFailCreatingStructureWhenRaisonSocialeIsNull() {

        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale(null);

        assertThrows(ConstraintViolationException.class, () -> {
            structureService.creationStructure(structuresDto);
        });
    }
    @Test
    public void shouldThrowExceptionWhenCreatingStructureWithNullRaisonSociale() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale(null);
        structuresDto.setStrSigle("Sigle");

        assertThrows(ConstraintViolationException.class,
                () -> structureService.creationStructure(structuresDto));
    }
    @Test
    public void shouldThrowExceptionWhenCreatingStructureWithShortSigle() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Raison Sociale");
        structuresDto.setStrSigle("S");

        assertThrows(ConstraintViolationException.class,
                () -> structureService.creationStructure(structuresDto));
    }
    @Test
    public void shouldThrowExceptionWhenCreatingStructureWithLongSigle() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Raison Sociale");
        structuresDto.setStrSigle("S".repeat(51)); // a Sigle of 51 characters

        assertThrows(ConstraintViolationException.class,
                () -> structureService.creationStructure(structuresDto));
    }
    @Test
    public void shouldThrowExceptionWhenCreatingStructureWithLongRaisonSocial() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Raison Sociale".repeat(200));
        structuresDto.setStrSigle("SIGLE"); // a Sigle of 51 characters

        assertThrows(ConstraintViolationException.class,
                () -> structureService.creationStructure(structuresDto));
    }
    @Test
    void shouldUpdateStructureWhenValidDataIsGiven() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Valid Raison Sociale");
        structuresDto.setStrSigle("Valid Sigle");
        structuresDto.setId(1L);

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));
        when(structuresMapper.partialUpdate(any(), any())).thenReturn(new Structures());
        when(structuresRepository.saveAndFlush(any())).thenReturn(new Structures());
        when(structuresMapper.toDto(any())).thenReturn(structuresDto);

        StructuresDto result = structureService.updateStructure(structuresDto);

        assertNotNull(result);
        assertEquals(structuresDto.getStrRaisonSociale(), result.getStrRaisonSociale());
        assertEquals(structuresDto.getStrSigle(), result.getStrSigle());
    }
    @Test
    void shouldThrowExceptionWhenUpdatingStructureWithNullRaisonSociale() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale(null);
        structuresDto.setStrSigle("Valid Sigle");

        assertThrows(ConstraintViolationException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    void shouldThrowExceptionWhenUpdatingStructureWithNullSigle() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Valid Raison Sociale");
        structuresDto.setStrSigle(null);

        assertThrows(ConstraintViolationException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    void shouldThrowExceptionWhenUpdatingStructureWithInvalidSigleLength() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Valid Raison Sociale");
        structuresDto.setStrSigle("S"); // S doesn't meet minimum length of 2

        assertThrows(ConstraintViolationException.class,
                () -> structureService.updateStructure(structuresDto));

    }
    @Test
    void shouldThrowExceptionWhenCreatingStructureWithNonUniqueRaisonSociale() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Duplicate Raison Sociale");
        structuresDto.setStrSigle("Valid Sigle");

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));
        when(structuresMapper.partialUpdate(any(), any())).thenReturn(new Structures());
        when(structuresRepository.saveAndFlush(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    void shouldThrowExceptionWhenUpdatingStructureWithSigleLengthExceedingMax() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Valid Raison Sociale");
        structuresDto.setStrSigle("S".repeat(51)); // String of length 51, which exceeds the maximum length

        assertThrows(ConstraintViolationException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    void shouldThrowExceptionWhenTryingToUpdateNonExistentStructure() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Valid Raison Sociale");
        structuresDto.setStrSigle("Valid Sigle");
        structuresDto.setId(1L);

        when(structuresRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    void shouldThrowExceptionWhenUpdatingStructureWithNonUniqueRaisonSociale() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Duplicate Raison Sociale");
        structuresDto.setStrSigle("Valid Sigle");
        structuresDto.setId(1L);

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));
        when(structuresMapper.partialUpdate(any(), any())).thenReturn(new Structures());
        when(structuresRepository.saveAndFlush(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    void shouldThrowExceptionWhenUpdatingStructureWithNonUniqueSigle() {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("Valid Raison Sociale");
        structuresDto.setStrSigle("Duplicate Sigle");
        structuresDto.setId(1L);

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));
        when(structuresMapper.partialUpdate(any(), any())).thenReturn(new Structures());
        when(structuresRepository.saveAndFlush(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class,
                () -> structureService.updateStructure(structuresDto));
    }
    @Test
    public void shouldDeleteStructureWhenExists() {
        Long existingId = 1L;

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));

        assertDoesNotThrow(() -> structureService.deleteStructure(existingId));

        verify(structuresRepository, times(1)).delete(any(Structures.class));
    }
    @Test
    public void shouldThrowExceptionWhenDeletingNonExistingStructure() {
        Long nonExistentId = 1L;

        when(structuresRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> structureService.deleteStructure(nonExistentId));

        verify(structuresRepository, times(0)).delete(any(Structures.class));
    }
    @Test
    public void shouldReturnStructureWhenExists() {
        Long existingId = 1L;
        StructuresDto expectedStructuresDto = new StructuresDto();
        expectedStructuresDto.setId(existingId);

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));
        when(structuresMapper.toDto(any())).thenReturn(expectedStructuresDto);

        StructuresDto result = structureService.getStructure(existingId);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
    }
    @Test
    public void shouldThrowExceptionWhenStructureDoesNotExist() {
        Long nonExistentId = 1L;
        when(structuresRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> structureService.getStructure(nonExistentId));
    }
    @Test
    public void shouldUploadLogoWhenStructureExistsAndFileIsValid() throws IOException {
        Long existingId = 1L;
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setId(existingId);
        MultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        when(structuresRepository.findById(any())).thenReturn(Optional.of(new Structures()));

        // No need to assert anything because if the method executes without error, it means the logo was uploaded successfully
        assertDoesNotThrow(() -> structureService.uploadStructureLogo(structuresDto, file));
    }
    @Test
    public void shouldThrowExceptionWhenUploadingLogoAndStructureDoesNotExist() {
        Long nonExistentId = 1L;
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setId(nonExistentId);
        MultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        when(structuresRepository.findById(any())).thenReturn(Optional.empty());

        // assert an exception is thrown when trying to upload a logo for the non-existing structure
        assertThrows(IllegalArgumentException.class,
                () -> structureService.uploadStructureLogo(structuresDto, file));
    }

}
