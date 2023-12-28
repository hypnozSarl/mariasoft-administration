/*
 *
 *  * Mariasoft - Enterprise Management Software
 *  * Copyright (c) 2023. Hypnoz Technologie. All rights reserved.
 *  *
 *  * This software and associated documentation files (the "Software") is the
 *  * proprietary and confidential information of Hypnoz Technologie and is subject
 *  * to a license agreement. The Software is protected by international copyright
 *  * and other intellectual property laws and treaties.
 *  *
 *  * No part of this Software may be copied, modified, distributed, or reproduced
 *  * in any form or by any means without prior written permission from Hypnoz Technologie.
 *  *
 *  * ANY USE OF THE SOFTWARE NOT EXPRESSLY PERMITTED BY THE TERMS OF THE LICENSE
 *  * AGREEMENT IS A VIOLATION OF COPYRIGHT LAW AND MAY RESULT IN SEVERE
 *  * CIVIL AND CRIMINAL PENALTIES.
 *
 */

package net.hypnoz.msadmin.service;

import net.hypnoz.msadmin.domain.Modules;
import net.hypnoz.msadmin.domain.Structures;
import net.hypnoz.msadmin.dtos.ModulesDto;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.mappers.ModulesMapper;
import net.hypnoz.msadmin.repository.ModulesRepository;
import net.hypnoz.msadmin.repository.StructuresRepository;
import net.hypnoz.msadmin.service.menus.ModuleDefault;
import net.hypnoz.msadmin.service.menus.MenuApplicatifService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuApplicatifServiceTest {
    @InjectMocks
    private MenuApplicatifService menuApplicatifService;

    @Mock
    private ModulesRepository moduleRepository;

    @Mock
    private StructuresRepository structureRepository;

    @Mock
    private ModulesMapper moduleMapper;

    private ModulesDto modulesDto;
    private StructuresDto structuresdto;
    private Structures structures;
    private Modules modules;

    @BeforeEach
    void setUp() {
        initCasSuccess();
    }

    private void initCasSuccess() {
        structuresdto = new StructuresDto();
        structuresdto.setId(1L);

        structures = new Structures();
        structures.setId(1L);

        modulesDto = new ModulesDto();
        modulesDto.setId("CodeModule");
        modulesDto.setName("name module");
        modulesDto.setUrl("url module");
        modulesDto.setHost("host module");
        modulesDto.setIcon("icon module");
        modulesDto.setStructureses(List.of(structuresdto));

        modules = new Modules();
        modules.setId("CodeModule");
        modules.setName("name module");
        modules.setUrl("url module");
        modules.setHost("host module");
        modules.setIcon("icon module");
        modules.setStructureses(List.of(structures));
    }

    @Test
    void affectationModuleStructure_Success() {
        initCasSuccess();
        when(structureRepository.findById(any(Long.class))).thenReturn(Optional.of(structures));
        when(moduleRepository.existsByIdAndStructureses_Id(any(String.class), any(Long.class))).thenReturn(false);
        when(moduleMapper.toEntity(any(ModulesDto.class))).thenReturn(modules);
        when(moduleRepository.saveAndFlush(any(Modules.class))).thenReturn(modules);
        when(moduleMapper.toDto(any(Modules.class))).thenReturn(modulesDto);

        ModulesDto result = menuApplicatifService.affectationModuleStructure(modulesDto,structures.getId());

        assertEquals(modulesDto, result);
    }
    @Test
    void affectationModuleStructure_StructureNotFound() {

        when(structureRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            menuApplicatifService.affectationModuleStructure(modulesDto,structures.getId());
        });

        String expectedMessage = "Structure not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void affectationModuleStructure_ModuleAlreadyLinked() {
        when(structureRepository.findById(any(Long.class))).thenReturn(Optional.of(structures));
        when(moduleRepository.existsByIdAndStructureses_Id(any(String.class), any(Long.class))).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            menuApplicatifService.affectationModuleStructure(modulesDto,structures.getId());
        });

        String expectedMessage = "Module is already linked with the structure";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testGetAllModulesNotLinked() {
        // Given
        Long sid = 1L;
        Structures structure = new Structures();
        structure.setId(sid);

        when(structureRepository.findById(sid)).thenReturn(Optional.of(structure));

        List<ModulesDto> allModulesDto = Arrays.stream(ModuleDefault.values())
                .map(md -> ModulesDto.builder()
                        .id(md.getId())
                        .name(md.getName())
                        .icon(md.getIcon())
                        .build())
                .toList();

        List<Modules> linkedModules = new ArrayList<>();
        Modules module = new Modules();
        module.setId("code 2");
        module.setIcon("icon2");
        module.setUrl("url 2");
        module.setHost("host 2");
        linkedModules.add(module);

        when(moduleRepository.findByStructureses_Id(sid)).thenReturn(linkedModules);

        // When
        List<ModulesDto> result = menuApplicatifService.getAllModulesNotLinked(sid);

        // Then
        assertEquals(allModulesDto.size(), result.size());
    }
    @Test
    void shouldThrowResourceNotFoundExceptiontestGetAllModulesNotLinked() {
        when(structureRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuApplicatifService.getAllModulesNotLinked(1L));
    }
    @Test
     void shoutGetAllModuleByStructures() {
        // Prepare data for testing
        Long sid = 1L;
        Modules existingModule = new Modules();
        existingModule.setId("id");
        existingModule.setId("code 2");
        existingModule.setIcon("icon2");
        existingModule.setUrl("url 2");
        existingModule.setHost("host 2");

        ModulesDto mappedModule = new ModulesDto();
        mappedModule.setId(existingModule.getId());
        when(moduleRepository.findByStructureses_Id(sid)).thenReturn(Collections.singletonList(existingModule));
        when(moduleMapper.toDto(existingModule)).thenReturn(mappedModule);

        // Call the service method
        List<ModulesDto> actualResult = menuApplicatifService.getAllModuleByStructures(sid);

        // Verify the interactions and the result
        assertEquals(Collections.singletonList(mappedModule), actualResult);
    }
    @Test
     void shoutGetModule() {
        // Prepare data for testing
        String id = "id";
        Modules existingModule = new Modules();
        existingModule.setId(id);
        ModulesDto modulesDto = new ModulesDto();
        modulesDto.setId(id);
        when(moduleRepository.findById(id)).thenReturn(Optional.of(existingModule));
        when(moduleMapper.toDto(existingModule)).thenReturn(modulesDto);

        // Call the service method
        ModulesDto actualResult = menuApplicatifService.getModule(id);

        // Verify the interactions and the result
        assertEquals(modulesDto, actualResult);
    }
    @Test
     void shoutUnLinkedModuleToStructure() {
        // Prepare data for testing
        Long sid = 1L;
        ModulesDto modulesDto = new ModulesDto();
        modulesDto.setId("id");
        List<ModulesDto> modulesDtoList = Collections.singletonList(modulesDto);

        when(moduleRepository.existsByIdAndStructureses_Id(modulesDto.getId(), sid)).thenReturn(true);

        // Call the service method
        menuApplicatifService.unLinkedModuleToStructure(modulesDtoList, sid);

        // Verify the interaction
        verify(moduleRepository, times(1)).deleteModuleStructures(modulesDto.getId(), sid);
    }
    @Test
     void shoutAffectationModuleStructure_StructureNotFound() {
        ModulesDto modulesDto = new ModulesDto();
        modulesDto.setUrl("Url test");
        modulesDto.setIcon("Icon test");
        modulesDto.setName("name test");
        modulesDto.setHost("host url");
        modulesDto.setStructureses(List.of(new StructuresDto()));
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> menuApplicatifService.affectationModuleStructure(modulesDto,structures.getId()));
        assertEquals("Structure not found", exception.getMessage());
    }
    @Test
     void shoutAffectationModuleStructure_ModuleAlreadyLinked() {
        StructuresDto structuresdto = new StructuresDto();
        structuresdto.setId(1L);

        Structures structures = new Structures();
        structures.setId(1L);

        ModulesDto modulesDto = new ModulesDto();
        modulesDto.setUrl("Url test");
        modulesDto.setIcon("Icon test");
        modulesDto.setName("name test");
        modulesDto.setHost("host url");
        modulesDto.setStructureses(List.of(structuresdto));
        when(structureRepository.findById(structuresdto.getId())).thenReturn(Optional.of(structures));
        when(moduleRepository.existsByIdAndStructureses_Id(modulesDto.getId(), structuresdto.getId())).thenReturn(true);
        // Module is already linked with the structure
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> menuApplicatifService.affectationModuleStructure(modulesDto,structures.getId()));
        assertEquals("Module is already linked with the structure", exception.getMessage());
    }
    @Test
    void shouldGetAllModuleNotLinkedStructureWhenStructureNotFound(){
      when(structureRepository.findById(1L)).thenReturn(Optional.empty());
      assertThrows(ResourceNotFoundException.class,()->menuApplicatifService.getAllModulesNotLinked(1L));

    }
}
