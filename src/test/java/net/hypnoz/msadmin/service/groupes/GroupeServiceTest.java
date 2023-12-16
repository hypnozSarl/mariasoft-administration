package net.hypnoz.msadmin.service.groupes;

import jakarta.validation.ConstraintViolationException;
import net.hypnoz.msadmin.domain.Groupes;
import net.hypnoz.msadmin.domain.Structures;
import net.hypnoz.msadmin.dtos.GroupesDto;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.mappers.GroupesMapper;
import net.hypnoz.msadmin.repository.GroupesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class GroupeServiceTest {
    @InjectMocks
    private GroupeService groupeService;
    @Mock
    private GroupesMapper groupesMapper;

    @Mock
    private GroupesRepository groupesRepository;
    @Test
    void shouldCreateGroupeWhenValidDataIsGiven() {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setGrpCode("Code");
        groupesDto.setGrpLibelle("Valid Group Label");
        groupesDto.setStructures(new StructuresDto());

        when(groupesMapper.toEntity(any(GroupesDto.class))).thenReturn(new Groupes());
        when(groupesRepository.save(any(Groupes.class))).thenReturn(new Groupes());
        when(groupesMapper.toDto(any(Groupes.class))).thenReturn(groupesDto);

        GroupesDto result = groupeService.createGroupe(groupesDto);

        assertNotNull(result);
        assertEquals(groupesDto.getGrpCode(), result.getGrpCode());
        assertEquals(groupesDto.getGrpLibelle(), result.getGrpLibelle());
        assertEquals(groupesDto.getStructures(), result.getStructures());
    }
    @Test
    void shouldThrowExceptionWhenGroupeDataIsInvalid() {
        GroupesDto groupesDto = new GroupesDto();
        assertThrows(ConstraintViolationException.class,
                () -> groupeService.createGroupe(groupesDto));
    }
    @ParameterizedTest
    @MethodSource("dataForGroupeServiceTest")
    void shouldThrowExceptionForInvalidGroup(String grpCode, StructuresDto structures, Class<? extends Exception> exceptionType) {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setGrpCode(grpCode);
        groupesDto.setGrpLibelle("Valid Group Label");
        groupesDto.setStructures(structures);

        assertThrows(exceptionType, () -> groupeService.createGroupe(groupesDto));
    }

    static Stream<Arguments> dataForGroupeServiceTest(){
        return Stream.of(
                Arguments.of(null, new StructuresDto(), ConstraintViolationException.class),
                Arguments.of("Code", null, ConstraintViolationException.class),
                Arguments.of("", new StructuresDto(), ConstraintViolationException.class),
                Arguments.of("123456789AB", new StructuresDto(), ConstraintViolationException.class)
        );
    }
    @Test
    void shouldCreateGroupeWhenGrpLibelleIsNull() {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setGrpCode("Code");
        groupesDto.setGrpLibelle(null);
        groupesDto.setStructures(new StructuresDto());

        when(groupesMapper.toEntity(any(GroupesDto.class))).thenReturn(new Groupes());
        when(groupesRepository.save(any(Groupes.class))).thenReturn(new Groupes());
        when(groupesMapper.toDto(any(Groupes.class))).thenReturn(groupesDto);

        GroupesDto result = groupeService.createGroupe(groupesDto);

        assertNotNull(result);
        assertEquals(groupesDto.getGrpCode(), result.getGrpCode());
        assertNull(result.getGrpLibelle());
        assertEquals(groupesDto.getStructures(), result.getStructures());
    }
    @Test
    void shouldThrowExceptionWhenNonUniqueGrpCodeIsGiven() {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setGrpCode("DupCode");
        groupesDto.setGrpLibelle("Valid Group Label");
        groupesDto.setStructures(new StructuresDto());

        when(groupesMapper.toEntity(any(GroupesDto.class))).thenReturn(new Groupes());
        when(groupesRepository.save(any(Groupes.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> groupeService.createGroupe(groupesDto));
    }
    @Test
    void shouldReturnGroupeWhenExists() {
        Long existingId = 1L;
        GroupesDto expectedGroupeDto = new GroupesDto();
        expectedGroupeDto.setId(existingId);

        when(groupesRepository.findById(any())).thenReturn(Optional.of(new Groupes()));
        when(groupesMapper.toDto(any())).thenReturn(expectedGroupeDto);

        GroupesDto result = groupeService.getGroupeById(existingId);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenGroupeDoesNotExist() {
        Long nonExistentId = 1L;
        when(groupesRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> groupeService.getGroupeById(nonExistentId));
    }
    @Test
    void shouldUpdateStructureWhenValidDataIsGiven() {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setGrpCode("Code");
        groupesDto.setGrpLibelle("Valid Sigle");
        groupesDto.setStructures(new StructuresDto());
        groupesDto.setId(1L);

        Groupes groupes = new Groupes();
        groupes.setId(1L);
        groupes.setGrpCode("Code");
        groupes.setGrpLibelle("Valid Sigle");
        groupes.setStructures(new Structures());

        when(groupesRepository.findById(any())).thenReturn(Optional.of(groupes));
        when(groupesMapper.partialUpdate(any(), any())).thenReturn(groupes);
        when(groupesRepository.saveAndFlush(any())).thenReturn(groupes);
        when(groupesMapper.toDto(any())).thenReturn(groupesDto);

        GroupesDto result = groupeService.updateGroupe(groupesDto);

        assertNotNull(result);
        assertEquals(groupesDto.getGrpCode(), result.getGrpCode());
        assertEquals(groupesDto.getGrpLibelle(), result.getGrpLibelle());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVotesForTest")
    void shouldThrowExceptionWhenStructureHasInvalidValues(String code, String libelle,StructuresDto structuresDto) {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setGrpCode(code);
        groupesDto.setGrpLibelle(libelle);
        groupesDto.setStructures(structuresDto);

        assertThrows(ConstraintViolationException.class,
                () -> groupeService.updateGroupe(groupesDto));
    }

    private static Stream<Arguments> provideInvalidVotesForTest() {
        return Stream.of(
                Arguments.of(null, "Libelle groupe",new StructuresDto()),
                Arguments.of("Code", "Libelle groupe",null),
                Arguments.of("Code".repeat(10), "Libelle groupe",new StructuresDto())
        );
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentGroupe() {
        // Arrange
        Long nonExistentId = 1L; // adjust based on your data
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setId(nonExistentId);
        groupesDto.setGrpCode("Code");
        groupesDto.setGrpLibelle("Valid Sigle");
        groupesDto.setStructures(new StructuresDto());

        // Mock the behavior of the repository to return empty Optional
        when(groupesRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            groupeService.updateGroupe(groupesDto);
        });

        // Additionally verify that certain methods have not been called
        verify(groupesMapper, never()).toEntity(any());
        verify(groupesRepository, never()).save(any());
    }
    @Test
    void shouldDeleteGroupWhenIdExists() {
        // Arrange
        Long existingId = 1L; // adjust based on your data
        Groupes existingGroup = new Groupes();
        existingGroup.setId(existingId);

        when(groupesRepository.findById(existingId)).thenReturn(Optional.of(existingGroup));

        // Act
        groupeService.deleteGroupe(existingId);

        // Assert
        verify(groupesRepository, times(1)).findById(existingId);
        verify(groupesRepository, times(1)).delete(existingGroup);
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentGroup() {
        // Arrange
        Long nonExistentId = 1L; // adjust based on your data

        // Mock the behavior of the repository to return empty Optional
        when(groupesRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            // Act
            groupeService.deleteGroupe(nonExistentId);
        });

        // Additionally verify that certain methods have not been called
        verify(groupesRepository, never()).delete(any());
    }
    @Test
    void shouldGetAllGroupeByStructureWhenIdExists() {
        // Arrange
        Long existingStructureId = 1L; // adjust based on your data
        List<Groupes> existingGroups = List.of(
                new Groupes(),
                new Groupes()
        );

        when(groupesRepository.findByStructures_Id(existingStructureId)).thenReturn(existingGroups);
        when(groupesMapper.toDto(any())).thenAnswer(invocation -> new GroupesDto());

        // Act
        List<GroupesDto> resultingGroupesDto = groupeService.getAllGroupeByStructure(existingStructureId);

        // Assert
        assertEquals(existingGroups.size(), resultingGroupesDto.size());
        verify(groupesRepository, times(1)).findByStructures_Id(existingStructureId);
        verify(groupesMapper, times(existingGroups.size())).toDto(any());
    }

}