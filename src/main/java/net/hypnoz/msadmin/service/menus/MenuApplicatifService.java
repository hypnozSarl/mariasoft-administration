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

package net.hypnoz.msadmin.service.menus;

import net.hypnoz.msadmin.domain.*;
import net.hypnoz.msadmin.dtos.ApplicationsDto;
import net.hypnoz.msadmin.dtos.FonctionnaliteDto;
import net.hypnoz.msadmin.dtos.ModulesDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.mappers.ApplicationsMapper;
import net.hypnoz.msadmin.mappers.FonctionnaliteMapper;
import net.hypnoz.msadmin.mappers.ModulesMapper;
import net.hypnoz.msadmin.repository.*;
import net.hypnoz.msadmin.utils.validators.ValidationCommunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import static net.hypnoz.msadmin.utils.validators.ConstantLogger.*;


@Service
public class MenuApplicatifService implements IMenuApplicatifService {


    private final Logger log = LoggerFactory.getLogger(MenuApplicatifService.class);
    private final ModulesRepository moduleRepository;
    private final ApplicationsRepository applicationsRepository;
    private final ApplicationsMapper applicationsMapper;
    private final StructuresRepository structureRepository;
    private final ModulesMapper moduleMapper;
    private final GroupesRepository groupesRepository;
    private final FonctionnaliteRepository fonctionnaliteRepository;
    private final FonctionnaliteMapper fonctionnaliteMapper;
    private final UsersRepository usersRepository;
    private final UserModulesRepository userModulesRepository;
    private final UserApplicationsRepository userApplicationsRepository;
    private final UserFonctionalitesRepository userFonctionalitesRepository;


    public MenuApplicatifService(ModulesRepository moduleRepository, ApplicationsRepository applicationsRepository, ApplicationsMapper applicationsMapper, StructuresRepository structureRepository, ModulesMapper moduleMapper, GroupesRepository groupesRepository, FonctionnaliteRepository fonctionnaliteRepository, FonctionnaliteMapper fonctionnaliteMapper,
                                 UsersRepository usersRepository,
                                 UserModulesRepository userModulesRepository,
                                 UserApplicationsRepository userApplicationsRepository,
                                 UserFonctionalitesRepository userFonctionalitesRepository) {
        this.moduleRepository = moduleRepository;
        this.applicationsRepository = applicationsRepository;
        this.applicationsMapper = applicationsMapper;
        this.structureRepository = structureRepository;
        this.moduleMapper = moduleMapper;
        this.groupesRepository = groupesRepository;
        this.fonctionnaliteRepository = fonctionnaliteRepository;
        this.fonctionnaliteMapper = fonctionnaliteMapper;
        this.usersRepository = usersRepository;
        this.userModulesRepository = userModulesRepository;
        this.userApplicationsRepository = userApplicationsRepository;
        this.userFonctionalitesRepository = userFonctionalitesRepository;
    }

    @Override
    public ModulesDto affectationModuleStructure(ModulesDto modulesDto, Long sid) {

        ValidationCommunUtils.validate(modulesDto);
        Optional<Structures> structure = structureRepository.findById(sid);

        log.debug("Checking if structure exists");
        if (structure.isEmpty()) {
            log.error(STRUCTURE_NOT_FOUND_MSG,sid );
            throw new ResourceNotFoundException(STRUCTURE_NOT_FOUND_MSG);
        }

        var moduleLinkedStructure = moduleRepository.existsByIdAndStructureses_Id(modulesDto.getId(), structure.get().getId());
        log.debug("Checking if module is already linked with the structure");
        if (moduleLinkedStructure) {
            log.error(MODULE_LINKED_STRUCTURE_MSG);
            throw new IllegalArgumentException(MODULE_LINKED_STRUCTURE_MSG);
        }

        Modules module = moduleMapper.toEntity(modulesDto);
        module.setStructureses(List.of(structure.get()));
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
                .map(md -> ModulesDto.builder()
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
        var module = moduleRepository.findById(id).orElseThrow(() -> {
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

    @Override
    public List<ModulesDto> affectationModuleGroupe( List<ModulesDto> modulesDtoList, Long groupeId) {
        var moduleList = modulesDtoList.stream().map(mod -> moduleRepository.findById(mod.getId()).orElseThrow(() -> {
            log.error(MODULE_NOT_FOUND_MSG);
            return new ResourceNotFoundException(MODULE_NOT_FOUND_MSG);
        })).toList();

        Groupes groupe = groupesRepository.findById(groupeId).orElseThrow(() -> new ResourceNotFoundException("Groupe not found"));

        for (Modules module : moduleList) {
            if (!groupesRepository.existsByIdAndModuleses_IdIn(groupe.getId(), Set.of(module.getId()))) {
                groupe.getModuleses().add(module);
            }
        }
        groupesRepository.saveAndFlush(groupe);
        return groupe.getModuleses().stream().map(moduleMapper::toDto).toList();
    }

    @Override
    public List<ApplicationsDto> getAllApplicationDefaultByModule(String codeModule) {
        Modules module = moduleRepository.findById(codeModule).orElseThrow(() -> {
            log.error(MODULE_NOT_FOUND_MSG);
            return new ResourceNotFoundException(MODULE_NOT_FOUND_MSG);
        });

        List<ApplicationsDto> appLinkedToModule = applicationsRepository.findByModules_Id(codeModule)
                .stream().map(applicationsMapper::toDto).toList();

        return Arrays.stream(ApplicationDefault.values())
                .filter(app -> app.getModule().equals(codeModule))
                .map(app -> ApplicationsDto.builder()
                        .id(app.getId())
                        .label(app.getLabel())
                        .url(app.getUrl())
                        .modules(moduleMapper.toDto(module))
                        .build())
                .filter(app -> appLinkedToModule.stream().noneMatch(dbApp -> dbApp.getId().equals(app.getId())))
                .toList();

    }

    @Override
    public List<ApplicationsDto> addApplicationForGroupes( List<ApplicationsDto> applicationsDtos, String idModule, Long idGroupe) {
        Groupes groupe = groupesRepository.findById(idGroupe).orElseThrow(() -> new ResourceNotFoundException(GROUPE_NOT_FOUND_MSG));
        Modules modules = moduleRepository.findById(idModule).orElseThrow(() -> new ResourceNotFoundException(MODULE_NOT_FOUND_MSG));

        for (ApplicationsDto applicationDto : applicationsDtos) {
            if (!groupesRepository.existsByIdAndApplicationses_IdIn(groupe.getId(), Set.of(applicationDto.getId()))) {
                var application = applicationsMapper.toEntity(applicationDto);
                if(!applicationsRepository.existsById(application.getId())){
                    application.setModules(modules);
                    applicationsRepository.saveAndFlush(application);
                }
                groupe.getApplicationses().add(application);
            }
        }
        groupesRepository.saveAndFlush(groupe);
        return groupe.getApplicationses().stream().map(applicationsMapper::toDto).toList();
    }

    @Override
    public List<FonctionnaliteDto> addFonctionnaliteForApplicationAndGroupes( List<FonctionnaliteDto> fonctionnaliteDtos, String idApp, Long idGroupe) {
        Applications application = applicationsRepository.findById(idApp).orElseThrow(() -> new ResourceNotFoundException(APPLICATION_NOT_FOUND_MSG));
        Groupes groupe = groupesRepository.findById(idGroupe).orElseThrow(() -> new ResourceNotFoundException(GROUPE_NOT_FOUND_MSG));
        Set<Fonctionnalite> fonctionnaliteSet = new HashSet<>();
        fonctionnaliteDtos.stream()
                .map(fonctionnaliteMapper::toEntity)
                .filter(fonctionnalite -> !groupesRepository.existsByIdAndFonctionnalites_IdIn(groupe.getId(), Set.of(fonctionnalite.getId())))
                .forEach(fonctionnalite -> {
                    if (!fonctionnaliteRepository.existsById(fonctionnalite.getId())) {
                        fonctionnalite.setApplications(application);
                        fonctionnaliteRepository.saveAndFlush(fonctionnalite);
                    }
                    fonctionnaliteSet.add(fonctionnalite);
                });

        groupe.setFonctionnalites(fonctionnaliteSet);
        groupesRepository.saveAndFlush(groupe);
        return fonctionnaliteDtos;
    }

    @Override
    public List<FonctionnaliteDto> getAllFonctionnaliteDefaultByApplication(String codeModule, String codeApplication) {
        Applications application = applicationsRepository.findById(codeApplication).orElseThrow(() -> {
            log.error(APPLICATION_NOT_FOUND_MSG);
            return new ResourceNotFoundException(APPLICATION_NOT_FOUND_MSG);
        });

        List<FonctionnaliteDto> fonctionnalites = fonctionnaliteRepository
                .findByApplications_IdAndApplications_Modules_Id(codeApplication,codeModule)
                .stream().map(fonctionnaliteMapper::toDto).toList();

        return Arrays.stream(FonctionnaliteDefault.values())
                .filter(fonc -> fonc.getCodeApplication().equals(codeApplication))
                .map(fonc -> FonctionnaliteDto.builder()
                        .id(fonc.getId())
                        .label(fonc.getLabel())
                        .link(fonc.getLink())
                        .applications(applicationsMapper.toDto(application))
                        .build())
                .filter(fonc -> fonctionnalites.stream().noneMatch(dbFonc -> dbFonc.getId().equals(fonc.getId())))
                .toList();
    }

    @Override
    public List<ModulesDto> getModuleLinkedGroupe(Long groupeId) {
        return groupesRepository.findById(groupeId)
                .map(Groupes::getModuleses)
                .orElseThrow(() -> new ResourceNotFoundException("Groupe not found"))
                .stream()
                .map(moduleMapper::toDto)
                .toList();
    }

    @Override
    public List<ApplicationsDto> getAllApplicationLinkedGroupeByModule(String codeModule, Long groupeId) {
      return groupesRepository.findById(groupeId)
              .map(Groupes::getApplicationses)
              .orElseThrow(() -> new ResourceNotFoundException("Groupe not found"))
              .stream()
              .map(applicationsMapper::toDto)
              .toList();

    }

    @Override
    public List<FonctionnaliteDto> getAllFonctionaliteLinkedGroupeByApplication(String codeApplication, Long groupeId) {
        return groupesRepository.findById(groupeId)
                .map(Groupes::getFonctionnalites)
                .orElseThrow(() -> new ResourceNotFoundException("Groupe not found"))
                .stream()
                .map(fonctionnaliteMapper::toDto)
                .toList();
    }

    @Override
    public List<ModulesDto> linkUserToModules( List<ModulesDto> modulesDtos, Long uid) {
        Users user = usersRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        for (ModulesDto modulesDto : modulesDtos) {
            Modules module = moduleRepository.findById(modulesDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Module not found"));
            String moduleId = module.getId();

            if (isEligibleForAddingUserModule(user.getGroupes().getId(), moduleId)
                    && (!doesUserModuleExist(moduleId, uid))) {

                UserModules newUserModule = new UserModules();
                newUserModule.setUser(user);
                newUserModule.setModules(module);
                userModulesRepository.saveAndFlush(newUserModule);
            }
        }

        return modulesDtos;
    }

    @Override
    public List<ApplicationsDto> linkUserToApplication( List<ApplicationsDto> applicationsDtos, Long uid, String codeModule) {
        List<Applications> applications = applicationsRepository.findAllById(applicationsDtos.stream()
                .map(ApplicationsDto::getId)
                .toList());

        Users user = usersRepository.findById(uid).orElseThrow(() -> {
            log.error(USER_NOT_FOUND_MSG, uid);
            return new ResourceNotFoundException(USER_NOT_FOUND_MSG);
        });

        Modules module = moduleRepository.findById(codeModule)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found"));

        Groupes userGroup = user.getGroupes();

        for (Applications app : applications) {
            linkUserAppIfEligible(user, module, userGroup, app);
        }

        return applicationsDtos;
    }

    @Override
    public List<FonctionnaliteDto> linkUserToFonctionalite( List<FonctionnaliteDto> fonctionaliteDtos, Long uid, String codeApplication) {
        List<Fonctionnalite> fonctionnalites = fonctionnaliteRepository.findAllById(fonctionaliteDtos.stream().map(FonctionnaliteDto::getId).toList());
        Users user = usersRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
        Groupes groupe = groupesRepository.findById(user.getGroupes().getId()).orElseThrow(() -> new ResourceNotFoundException(GROUPE_NOT_FOUND_MSG));
        Applications application = applicationsRepository.findById(codeApplication).orElseThrow(() -> new ResourceNotFoundException(APPLICATION_NOT_FOUND_MSG));
        for (Fonctionnalite fonctionnalite : fonctionnalites) {
            if (groupesRepository.existsByIdAndFonctionnalites_IdIn(groupe.getId(), Set.of(fonctionnalite.getId()))
                    && fonctionnalite.getApplications().getId().equals(application.getId())
                    && !userFonctionalitesRepository.existsById(UserFonctionalityId.builder()
                    .userId(user.getId()).fonctionalityId(fonctionnalite.getId()).build())) {
                UserFonctionalites userFonctionalites = UserFonctionalites.builder()
                        .id(UserFonctionalityId.builder()
                                .fonctionalityId(fonctionnalite.getId())
                                .userId(user.getId())
                                .build())
                        .fonctionalite(fonctionnalite)
                        .user(user)
                        .build();
                userFonctionalitesRepository.saveAndFlush(userFonctionalites);
            }
        }
        return fonctionaliteDtos;
    }

    @Override
    public FonctionnaliteDto addDroitUserToFonctionnalite( List<DroiteFonctionnaliteEnum> droiteFonctionnaliteEnums,
                                                          Long uid, String codeFonctionnalite) {
        UserFonctionalites userFonctionalites = userFonctionalitesRepository
                .findById(new UserFonctionalityId(uid, codeFonctionnalite)).
                orElseThrow(()->new ResourceNotFoundException(USER_FONCTIONNALITE_NOT_LINKED_MSG));

        for(DroiteFonctionnaliteEnum droite: droiteFonctionnaliteEnums){

            switch (droite.name()) {
                case "WRITED" -> userFonctionalites.setEcriture(true);
                case "READ" -> userFonctionalites.setLecture(true);
                case "UPDATE" -> userFonctionalites.setModification(true);
                case "DELETE" -> userFonctionalites.setSuppression(true);
                case "DUPLICATED" -> userFonctionalites.setDuplicated(true);
                case "TRANSFERT" -> userFonctionalites.setTransfert(true);
                case "IMPRESSION" -> userFonctionalites.setImpression(true);
            }
            userFonctionalitesRepository.saveAndFlush(userFonctionalites);
        }
      return fonctionnaliteMapper.toDto(userFonctionalites.getFonctionalite());
    }

    private void linkUserAppIfEligible(Users user,  Modules module, Groupes userGroup,  Applications app){
        if (app.getModules().getId().equals(module.getId())
                && groupesRepository.existsByIdAndApplicationses_IdIn(userGroup.getId(), Set.of(app.getId()))) {
            UserApplications userApplications = UserApplications.builder()
                    .id(UserApplicationId.builder()
                            .userId(user.getId())
                            .applicationId(app.getId())
                            .build())
                    .user(user)
                    .applications(app)
                    .build();

            userApplicationsRepository.saveAndFlush(userApplications);
        }
    }

    private boolean isEligibleForAddingUserModule(Long groupId, String moduleId) {
        return groupesRepository.existsByIdAndModuleses_IdIn(groupId, Set.of(moduleId));
    }

    private boolean doesUserModuleExist(String moduleId, Long userId) {
        UserModuleId userModuleId = UserModuleId.builder().moduleId(moduleId).userId(userId).build();
        return userModulesRepository.existsById(userModuleId);
    }
}