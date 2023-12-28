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

package net.hypnoz.msadmin.service.groupes;

import net.hypnoz.msadmin.domain.Groupes;
import net.hypnoz.msadmin.dtos.GroupesDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.mappers.GroupesMapper;
import net.hypnoz.msadmin.repository.GroupesRepository;
import net.hypnoz.msadmin.repository.StructuresRepository;
import net.hypnoz.msadmin.utils.validators.ValidationCommunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupeService implements IGroupeService {
    private final Logger log = LoggerFactory.getLogger(GroupeService.class);

    private final GroupesMapper groupesMapper;
    private final GroupesRepository groupesRepository;
    private final StructuresRepository structuresRepository;
    private static final String STRUCTURE_NOT_FOUND_MSG = "Failed to find Structure id: {}";
    private static final String LOG_CREATE_GROUP = "Request to create a group: {}";
    private static final String LOG_GROUP_CREATED = "Group created: {}";
    private static final String LOG_FIND_GROUP = "Unable to find group with id: {}";
    private static final String LOG_UPDATE_GROUP = "Request to update group: {}";
    private static final String LOG_GROUP_UPDATED = "Group updated: {}";
    private static final String LOG_DELETE_GROUP = "Attempting to delete Group with info: {}";
    private static final String LOG_FAILED_FIND_GROUP = "Failed to find Group id: {}";
    private static final String LOG_GROUP_DELETED = "Group deleted with info: {}";
    private static final String LOG_FETCHED_GROUPS = "Fetched all groups with group id: {}";

    public GroupeService(GroupesMapper groupesMapper, GroupesRepository groupesRepository, StructuresRepository structuresRepository) {
        this.groupesMapper = groupesMapper;
        this.groupesRepository = groupesRepository;
        this.structuresRepository = structuresRepository;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public GroupesDto createGroupe(GroupesDto groupesDto) {
        log.debug(LOG_CREATE_GROUP, groupesDto);
       var structures = structuresRepository.findById(groupesDto.getStructures().getId())
                .orElseThrow(()->{
                    log.error(STRUCTURE_NOT_FOUND_MSG, groupesDto.getStructures().getId());
                    return new ResourceNotFoundException("Structure with given id not found");
                });
        ValidationCommunUtils.validate(groupesDto);
        Groupes groupes = groupesMapper.toEntity(groupesDto);
        groupes.setStructures(structures);
        Groupes addedGroup = groupesRepository.saveAndFlush(groupes);
        log.debug(LOG_GROUP_CREATED, addedGroup);
        return groupesMapper.toDto(addedGroup);
    }

    @Override
    public GroupesDto getGroupeById(Long id) {
        Groupes existingGroup = groupesRepository.findById(id).orElseThrow(() -> {
            log.error(LOG_FIND_GROUP, id);
            return new IllegalArgumentException(LOG_FIND_GROUP);
        });
        return groupesMapper.toDto(existingGroup);
    }

    @Override
    public GroupesDto updateGroupe(GroupesDto groupesDto) {
        log.debug(LOG_UPDATE_GROUP, groupesDto);
        ValidationCommunUtils.validate(groupesDto);
        Groupes existingGroup = groupesRepository.findById(groupesDto.getId()).orElseThrow(() -> {
            log.error(LOG_FIND_GROUP, groupesDto.getId());
            return new IllegalArgumentException(LOG_FIND_GROUP);
        });
        existingGroup = groupesMapper.partialUpdate(groupesDto, existingGroup);
        existingGroup = groupesRepository.saveAndFlush(existingGroup);
        log.debug(LOG_GROUP_UPDATED, groupesDto);
        return groupesMapper.toDto(existingGroup);
    }

    @Override
    public void deleteGroupe(Long id) {
        log.debug(LOG_DELETE_GROUP, id);
        Groupes existingGroup = groupesRepository.findById(id).orElseThrow(() -> {
            log.error(LOG_FAILED_FIND_GROUP, id);
            return new IllegalArgumentException(LOG_FIND_GROUP);
        });
        groupesRepository.delete(existingGroup);
        log.debug(LOG_GROUP_DELETED, existingGroup);
    }

    @Override
    public List<GroupesDto> getAllGroupeByStructure(Long sid) {
        List<GroupesDto> groupes = groupesRepository.findByStructures_Id(sid).stream()
                .map(groupesMapper::toDto)
                .toList();
        log.debug(LOG_FETCHED_GROUPS, sid);
        return groupes;
    }
}