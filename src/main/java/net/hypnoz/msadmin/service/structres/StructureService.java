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

package net.hypnoz.msadmin.service.structres;

import net.hypnoz.msadmin.domain.Structures;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.mappers.StructuresMapper;
import net.hypnoz.msadmin.repository.StructuresRepository;
import net.hypnoz.msadmin.utils.OsUtils;
import net.hypnoz.msadmin.utils.validators.ValidationCommunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class StructureService implements IStructureService {

    private final Logger log = LoggerFactory.getLogger(StructureService.class);

    private final StructuresRepository structuresRepository;
    private final StructuresMapper structuresMapper;

    // Constants for log messages
    private static final String STRUCTURE_NOT_FOUND_MSG = "Failed to find Structure id: {}";
    private static final String ATTEMPT_UPDATE_MSG = "Attempting to update Structure with info: {}";
    private static final String ATTEMPT_CREATE_MSG = "Attempting to create Structure with info: {}";
    private static final String ATTEMPT_DELETE_MSG = "Attempting to delete Structure with info: {}";
    private static final String ATTEMPT_GET_MSG = "Attempting to get Structure by id: {}";
    private static final String ATTEMPT_UPLOAD_LOGO_MSG = "Attempting to upload a logo for structure ID: {}";
    private static final String UPLOAD_LOGO_ERROR_MSG = "Error while uploading logo for structure ID: {}";
    private static final String DIR_CREATED_MSG = "Directory 'structures-logo' didn't exist and was created";
    private static final String LOGO_DELETION_MSG = "Previous logo has been deleted";

    public StructureService(StructuresRepository structuresRepository, StructuresMapper structuresMapper) {
        this.structuresRepository = structuresRepository;
        this.structuresMapper = structuresMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public StructuresDto creationStructure(StructuresDto structuresDto) {
        ValidationCommunUtils.validate(structuresDto);
        log.debug(ATTEMPT_CREATE_MSG, structuresDto);
        var structures = structuresMapper.toEntity(structuresDto);
        structures = structuresRepository.saveAndFlush(structures);

        return structuresMapper.toDto(structures);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public StructuresDto updateStructure(StructuresDto structuresDto) {
        log.debug(ATTEMPT_UPDATE_MSG, structuresDto);
        ValidationCommunUtils.validate(structuresDto);
        Structures existingStructure = getOneStructure(structuresDto.getId());
        existingStructure = structuresMapper.partialUpdate(structuresDto, existingStructure);
        existingStructure = structuresRepository.saveAndFlush(existingStructure);

        return structuresMapper.toDto(existingStructure);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteStructure(Long sid) {
        log.debug(ATTEMPT_DELETE_MSG, sid);
        Structures existingStructure = getOneStructure(sid);
        structuresRepository.delete(existingStructure);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ResourceNotFoundException.class, readOnly = true)
    public StructuresDto getStructure(Long sid) {
        log.debug(ATTEMPT_GET_MSG, sid);
        Structures structures = getOneStructure(sid);
        return structuresMapper.toDto(structures);
    }

    /**
     * @param structuresDto
     * @param file          The logo file.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void uploadStructureLogo(StructuresDto structuresDto, MultipartFile file) {
        log.debug(ATTEMPT_UPLOAD_LOGO_MSG, structuresDto.getId());

            validateStructureExists(structuresDto.getId());

        Path logoPath = prepareLogoPath(file,structuresDto.getId());

        try {
            deleteExistingLogo(logoPath);

            saveLogo(logoPath, file);

        } catch (IOException e) {
            log.error(UPLOAD_LOGO_ERROR_MSG, structuresDto.getId(), e);
            throw new RuntimeException("Could not upload logo for structure ID: " + structuresDto.getId(), e);
        }
    }

    private void validateStructureExists(Long id) {
        getOneStructure(id);
    }

    private Structures getOneStructure(Long id) {
       return structuresRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(STRUCTURE_NOT_FOUND_MSG, id);
                    return new ResourceNotFoundException("Structure with given id not found");
                });
    }

    private Path prepareLogoPath(MultipartFile file, Long id) {
        Path dir = Paths.get(OsUtils.getOsHomeDir()+ "/structure" + id + "/image/logos/");
        if (!Files.exists(dir)) {
            createLogoDirectory(dir);
        }
        return dir.resolve(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
    }

    private void createLogoDirectory(Path dir) {
        try {
            Files.createDirectories(dir);
            log.debug(DIR_CREATED_MSG);
        } catch (IOException e) {
            log.error("Error while creating 'structures-logo' directory", e);
            throw new RuntimeException("Could not create directory for the logos", e);
        }
    }

    public void deleteExistingLogo(Path logoPath) throws IOException {
        if (Files.exists(logoPath)) {
            Files.delete(logoPath);
            log.debug(LOGO_DELETION_MSG);
        }
    }

    private void saveLogo(Path logoPath, MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), logoPath);
    }
}