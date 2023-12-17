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

package net.hypnoz.msadmin.web.rest.errors;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "401", description = "Unauthorised", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "405", description = "Method not Allowed", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "409", description = "Conflit", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "413", description = "Payload To Large", content = {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
                @ApiResponse(responseCode = "500", description = "Une erreur est survenue", content =
                        {@Content(mediaType =
                        "application/json", schema =@Schema(implementation = ErrorDto.class))}),
        }
)
public @interface DefaultErrorApiResponse {
}
