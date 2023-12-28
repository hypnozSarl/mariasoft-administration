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

import net.hypnoz.msadmin.dtos.ApplicationsDto;
import net.hypnoz.msadmin.dtos.FonctionnaliteDto;
import net.hypnoz.msadmin.dtos.ModulesDto;

import java.util.List;

public interface IMenuApplicatifService {
    ModulesDto affectationModuleStructure(ModulesDto modulesDto , Long sid);
    List<ModulesDto> getAllModulesNotLinked(Long sid);
    List<ModulesDto> getAllModuleByStructures(Long sid);
    ModulesDto getModule(String id);
    void unLinkedModuleToStructure(List<ModulesDto> modulesDtoList,Long sid);

    List<ModulesDto> affectationModuleGroupe(List<ModulesDto> modulesDtoList,Long groupeId);
    List<ApplicationsDto> getAllApplicationDefaultByModule(String codeModule);
    List<ApplicationsDto> addApplicationForGroupes(List<ApplicationsDto> applicationsDtos,String idModule,Long idGroupe);
    List<FonctionnaliteDto> addFonctionnaliteForApplicationAndGroupes(List<FonctionnaliteDto> fonctionnaliteDtos, String idApp, Long idGroupe);
    List<FonctionnaliteDto> getAllFonctionnaliteDefaultByApplication(String codeModule,String codeApplication);
    List<ModulesDto> getModuleLinkedGroupe(Long groupeId);
    List<ApplicationsDto> getAllApplicationLinkedGroupeByModule(String codeModule,Long groupeId);
    List<FonctionnaliteDto> getAllFonctionaliteLinkedGroupeByApplication(String codeApplication,Long groupeId);
    List<ModulesDto> linkUserToModules(List<ModulesDto> modulesDtoList,Long uid);
    List<ApplicationsDto> linkUserToApplication(List<ApplicationsDto> applicationsDtos,Long uid,String codeModule);
    List<FonctionnaliteDto> linkUserToFonctionalite(List<FonctionnaliteDto> fonctionaliteDtos, Long uid, String codeApplication);
    FonctionnaliteDto  addDroitUserToFonctionnalite(List<DroiteFonctionnaliteEnum> droiteFonctionnaliteEnums,Long uid,String codeFonctionnalite);
}
