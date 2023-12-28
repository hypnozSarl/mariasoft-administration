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
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.dtos.UsersDto;
import net.hypnoz.msadmin.service.structres.StructureService;
import net.hypnoz.msadmin.service.users.UsersService;
import net.hypnoz.msadmin.web.rest.errors.BadRequestAlertException;
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
import java.text.MessageFormat;

@RestController
@DefaultErrorApiResponse
@RequestMapping("/api/users")
public class UsersResource {
    private static final String ENTITY_NAME = "structures";
    private final Logger log = LoggerFactory.getLogger(UsersResource.class);
    @Value("${mariasoft.clientApp.name}")
    private String applicationName;
    private final  UsersService usersService;

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Creation Users avec Success", content =
                            {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation =
                                    StructuresDto.class))})
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsersDto> creationUsers(@Valid @RequestBody UsersDto usersDto) {
        log.debug("REST request to save Users : {}", usersDto);
        try {
            if(usersDto.getId() != null) {
                throw new BadRequestAlertException("A new Users cannot already have an ID", "userManagement", "idexists");
            }
            UsersDto result = usersService.createUsers(usersDto);
            return ResponseEntity.created(new URI(MessageFormat.format("/api/users/{0}", result.getId())))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getUsrMatricule()))
                    .body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(usersDto);
        }
    }
}
