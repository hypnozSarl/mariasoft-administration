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

package net.hypnoz.msadmin.service.modules;

import net.hypnoz.msadmin.domain.Modules;
import net.hypnoz.msadmin.domain.Structures;
import net.hypnoz.msadmin.dtos.ModulesDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.mappers.ModulesMapper;
import net.hypnoz.msadmin.repository.ModulesRepository;
import net.hypnoz.msadmin.repository.StructuresRepository;
import net.hypnoz.msadmin.utils.validators.ValidationCommunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class ModuleService implements IModuleService{

    private static final String STRUCTURE_NOT_FOUND_MSG = "Structure not found";
    private static final String MODULE_NOT_FOUND_MSG = "Module not found";
    private static final String MODULE_LINKED_STRUCTURE_MSG = "Module is already linked with the structure";

    private final Logger log = LoggerFactory.getLogger(ModuleService.class);
    private final ModulesRepository moduleRepository;
    private final StructuresRepository structureRepository;
    private final ModulesMapper moduleMapper;

    public ModuleService(ModulesRepository moduleRepository, StructuresRepository structureRepository, ModulesMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.structureRepository = structureRepository;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public ModulesDto affectationModuleStructure(ModulesDto modulesDto) {

        ValidationCommunUtils.validate(modulesDto);
        var firstStructure = modulesDto.getStructureses().stream().findFirst().orElseThrow();
        Optional<Structures> structure = structureRepository.findById(firstStructure.getId());

        log.debug("Checking if structure exists");
        if(structure.isEmpty()) {
            log.error(STRUCTURE_NOT_FOUND_MSG);
            throw new ResourceNotFoundException(STRUCTURE_NOT_FOUND_MSG);
        }

        var moduleLinkedStructure = moduleRepository.existsByIdAndStructureses_Id(modulesDto.getId(), structure.get().getId());
        log.debug("Checking if module is already linked with the structure");
        if(moduleLinkedStructure) {
           log.error(MODULE_LINKED_STRUCTURE_MSG);
           throw new IllegalArgumentException(MODULE_LINKED_STRUCTURE_MSG);
        }

        Modules module = moduleMapper.toEntity(modulesDto);
        moduleRepository.saveAndFlush(module);
        return moduleMapper.toDto(module);
    }

    @Override
    public List<ModulesDto> getAllModulesNotLinked(Long sid) {
        Structures myStructure = structureRepository.findById(sid).orElseThrow(() -> {
            log.error(STRUCTURE_NOT_FOUND_MSG);
            return new ResourceNotFoundException(STRUCTURE_NOT_FOUND_MSG);
        });

        List<ModulesDto> allModules = Arrays.stream(ModuleDefault.values())
                .map(md ->ModulesDto.builder()
                        .id(md.getId())
                        .name(md.getName())
                        .icon(md.getIcon())
                        .build())
                .toList();

        List<Modules> linkedModules = moduleRepository.findByStructureses_Id(myStructure.getId());

        return allModules.stream()
                .filter(mod -> linkedModules.stream().noneMatch(lm -> lm.getId().equals(mod.getId())))
                .toList();
    }

    @Override
    public List<ModulesDto> getAllModuleByStructures(Long sid) {
        return moduleRepository.findByStructureses_Id(sid).stream().map(moduleMapper::toDto).toList();
    }

    @Override
    public ModulesDto getModule(String id) {
        var module= moduleRepository.findById(id).orElseThrow(()->{
            log.debug(MODULE_NOT_FOUND_MSG);
            return new ResourceNotFoundException(MODULE_NOT_FOUND_MSG);
        });
        return moduleMapper.toDto(module);
    }
    @Override
    public void unLinkedModuleToStructure(List<ModulesDto> modulesDtoList, Long sid) {
        for (ModulesDto mod : modulesDtoList) {
            if (moduleRepository.existsByIdAndStructureses_Id(mod.getId(), sid)) {
                moduleRepository.deleteModuleStructures(mod.getId(), sid);
            }
        }
    }
}