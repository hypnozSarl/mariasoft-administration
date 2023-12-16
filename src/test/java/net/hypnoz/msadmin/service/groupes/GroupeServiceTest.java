package net.hypnoz.msadmin.service.groupes;

import jakarta.validation.ConstraintViolationException;
import net.hypnoz.msadmin.domain.Groupes;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupeServiceTest {
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
}