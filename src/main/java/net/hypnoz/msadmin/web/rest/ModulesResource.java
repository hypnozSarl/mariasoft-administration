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
import net.hypnoz.msadmin.dtos.ModulesDto;
import net.hypnoz.msadmin.service.modules.ModuleService;
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
@RequestMapping("/api/modules")
public class ModulesResource {
    private static final String ENTITY_NAME = "modules";
    private final Logger log = LoggerFactory.getLogger(ModulesResource.class);
    @Value("${mariasoft.clientApp.name}")
    private String applicationName;
    private final ModuleService moduleService;

    public ModulesResource(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    @PostMapping("/affectationStructure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Affectation Module Structure with Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ModulesResource.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModulesDto> affectModuleStructure(@Valid @RequestBody ModulesDto modulesDto) throws URISyntaxException {
        log.debug("Request to affectModuleStructure: {}", modulesDto);
        ModulesDto result = moduleService.affectationModuleStructure(modulesDto);
        log.debug("Response from affectModuleStructure: {}", result);
        return ResponseEntity.created(new URI(MessageFormat.format("/api/module/{0}", result.getId())))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getName()))
                .body(result);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting List Module with Success", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ModulesResource.class)))
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
                            schema = @Schema(implementation = ModulesResource.class)))
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
                            schema = @Schema(implementation = ModulesResource.class)))
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

}
