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

package net.hypnoz.msadmin.web.rest;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import net.hypnoz.msadmin.domain.Users;
import net.hypnoz.msadmin.dtos.ApplicationsDto;
import net.hypnoz.msadmin.dtos.FonctionnaliteDto;
import net.hypnoz.msadmin.dtos.ModulesDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.service.menus.DroiteFonctionnaliteEnum;
import net.hypnoz.msadmin.service.menus.MenuApplicatifService;
import net.hypnoz.msadmin.service.menus.MenuDto;
import net.hypnoz.msadmin.web.rest.errors.DefaultErrorApiResponse;
import net.hypnoz.msadmin.web.rest.errors.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;

@RestController
@DefaultErrorApiResponse
@RequestMapping("/api/menus")
public class MenusResource {
    private static final String ENTITY_NAME = "menus";
    private final Logger log = LoggerFactory.getLogger(MenusResource.class);
    @Value("${mariasoft.clientApp.name}")
    private String applicationName;
    private final MenuApplicatifService moduleService;

    public MenusResource(MenuApplicatifService menuApplicatifService) {
        this.moduleService = menuApplicatifService;
    }
    @PostMapping("/affectationStructure/{sid}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Affectation Module Structure with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModulesDto> affectModuleStructure(@Valid @RequestBody ModulesDto modulesDto,@PathVariable Long sid) throws URISyntaxException {
        log.debug("Request to affectModuleStructure: {}", modulesDto);
        ModulesDto result = moduleService.affectationModuleStructure(modulesDto,sid);
        log.debug("Response from affectModuleStructure: {}", result);
        return ResponseEntity.created(new URI(MessageFormat.format("/api/module/{0}", result.getId())))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getName()))
                .body(result);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting List Module with Success", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{sid}/modulesNotLinked")
    public ResponseEntity<List<ModulesDto>> getModulesNotLinked(@PathVariable Long sid) {
        List<ModulesDto> modules = moduleService.getAllModulesNotLinked(sid);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME,"")).body(modules);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting List Module with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{sid}/modulesByStructures")
    public ResponseEntity<List<ModulesDto>> getModulesByStructures(@PathVariable Long sid) {
        List<ModulesDto> modules = moduleService.getAllModuleByStructures(sid);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(modules);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting Module with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<ModulesDto> getModule(@PathVariable String id) {
        ModulesDto module = moduleService.getModule(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, module.getName())).body(module);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unlinking Module to Structure with Success")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{sid}/unlinkedModuleToStructure")
    public void unlinkedModuleToStructure(@RequestBody List<ModulesDto> modulesDtoList, @PathVariable Long sid) {
        moduleService.unLinkedModuleToStructure(modulesDtoList, sid);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting List Application Default by Module with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{codeModule}/applicationsDefault")
    public ResponseEntity<List<ApplicationsDto>> getApplicationsDefaultByModule(@PathVariable String codeModule) {
        List<ApplicationsDto> applications = moduleService.getAllApplicationDefaultByModule(codeModule);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(applications);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adding Modules for Groupes with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addModuleForGroupes")
    public ResponseEntity<List<ModulesDto>> addModuleForGroupes(@RequestBody List<ModulesDto> modulesDtos, @RequestHeader("groupe")  Long idGroupe) {
        List<ModulesDto> mod = moduleService.affectationModuleGroupe(modulesDtos,  idGroupe);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(mod);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adding Applications for Groupes with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addApplicationsForGroupes")
    public ResponseEntity<List<ApplicationsDto>> addApplicationsForGroupes(@RequestBody List<ApplicationsDto> applicationsDtos, @RequestHeader("module") String idModule, @RequestHeader("groupe")  Long idGroupe) {
        List<ApplicationsDto> applications = moduleService.addApplicationForGroupes(applicationsDtos, idModule, idGroupe);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(applications);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adding Fonctionnalite for Application and Groupes with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addFonctionnaliteForApplicationAndGroupes")
    public ResponseEntity<List<FonctionnaliteDto>> addFonctionnaliteForApplicationAndGroupes(@RequestBody List<FonctionnaliteDto> fonctionnaliteDtos, @RequestHeader("idApp") String idApp, @RequestHeader("idGroupe") Long idGroupe) {
        List<FonctionnaliteDto> fonctionnalites = moduleService.addFonctionnaliteForApplicationAndGroupes(fonctionnaliteDtos, idApp, idGroupe);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(fonctionnalites);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All Fonctionnalite by Application adn Module with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fonctionnaliteDefault")
    public ResponseEntity<List<FonctionnaliteDto>> getAllFonctionnaliteDefaultByApplication( @RequestHeader("codeModule") String codeModule,
                                                                                             @RequestHeader("codeApplication") String codeApplication) {
        List<FonctionnaliteDto> fonctionnaliteDtos = moduleService.getAllFonctionnaliteDefaultByApplication(codeApplication,codeApplication);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(fonctionnaliteDtos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All  Module By groupeID with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/moduleLinkedGroupe")
    public ResponseEntity<List<ModulesDto>> getModuleLinkedGroupe( @RequestHeader("idGroupe") Long groupeID) {
        List<ModulesDto> modulesDtos = moduleService.getModuleLinkedGroupe(groupeID);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(modulesDtos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Application  Linked Groupe with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/applicationLinkedGroupe")
    public ResponseEntity<List<ApplicationsDto>> getAllApplicationLinkedGroupeByModule(@RequestHeader("idGroupe") Long idGroupe, @RequestHeader("codeModule") String codeModule) {
        List<ApplicationsDto> linkedApplications = moduleService.getAllApplicationLinkedGroupeByModule(codeModule,idGroupe);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(linkedApplications);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Fonctionnalite  Linked Groupe with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fonctionnalitesLinkedGroupe")
    public ResponseEntity<List<FonctionnaliteDto>> getAllFonctionaliteLinkedGroupeByApplication(@RequestHeader("idGroupe") Long idGroupe,
                                                                                                @RequestHeader("codeApplication") String codeApplication) {
        List<FonctionnaliteDto> linkedFonctionnalite = moduleService.getAllFonctionaliteLinkedGroupeByApplication(codeApplication,idGroupe);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(linkedFonctionnalite);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add Linked Module Users with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/linkUserToModules/{uid}")
    public ResponseEntity<List<ModulesDto>> addlinkUserToModules(@RequestBody List<ModulesDto> modulesDtos, @PathVariable Long uid) {
        List<ModulesDto> response = moduleService.addlinkUserToModules(modulesDtos,uid);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(response);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add Linked Application Users with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/linkUserToApplication")
    public ResponseEntity<List<ApplicationsDto>> addlinkUserToApplication(@RequestBody List<ApplicationsDto> applicationsDtos,
                                                                          @RequestHeader("uid") Long uid,@RequestHeader("codeModule") String codeModule) {
        List<ApplicationsDto> response = moduleService.addlinkUserToApplication(applicationsDtos,uid,codeModule);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(response);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add Linked Fonctionnalite Users with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/linkUserToFonctionnalite")
    public ResponseEntity<List<FonctionnaliteDto>> addlinkUserToFonctionalite(@RequestBody List<FonctionnaliteDto> applicationsDtos,
                                                                          @RequestHeader("uid") Long uid,@RequestHeader("codeApplication") String codeApplication) {
        List<FonctionnaliteDto> response = moduleService.addlinkUserToFonctionalite(applicationsDtos,uid,codeApplication);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(response);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add droit Fonctionnalite Users with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/DroutUserToFonctionnalite")
    public ResponseEntity<FonctionnaliteDto> addDroitUserToFonctionnalite(@RequestBody List<DroiteFonctionnaliteEnum> droiteFonctionnaliteEnums,
                                                                              @RequestHeader("uid") Long uid,@RequestHeader("codeFonctionnalite") String codeFonctionnalite) {
       FonctionnaliteDto response = moduleService.addDroitUserToFonctionnalite(droiteFonctionnaliteEnums,uid,codeFonctionnalite);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(response);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Fonctionnalite  Linked Groupe with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenusResource.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/useerMenus/{uid}")
    public ResponseEntity<MenuDto> getMenusByUsers(@PathVariable Long uid) {
        MenuDto menuDto = moduleService.getMenusByUsers(uid);
        return ResponseEntity.accepted().headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, "")).body(menuDto);
    }
}
