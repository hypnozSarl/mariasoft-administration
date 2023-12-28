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

import com.fasterxml.jackson.databind.ObjectMapper;
import net.hypnoz.msadmin.dtos.ModulesDto;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.service.menus.MenuApplicatifService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModulesResource.class)
 class ModulesResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuApplicatifService menuApplicatifService;

    @Test
     void modulesResourceShouldReturnModulesNotLinked() throws Exception {
       List<StructuresDto> structuresDtoList = List.of(
               StructuresDto.builder()
                       .id(1L)
                       .strEcommerceVal(1)
                       .strEtat(2)
                       .strMode(1)
                       .strMaitreCabinet(1)
                       .strRaisonSociale("Raison Sociale")
                       .strDescriptif("Descriptif")
                       .strSigle("SIG")
                       .strNomPays("Country")
                       .strCodePays("C-01")
                       .strIsoPays("ISO")
                       .strDevise("Devise")
                       .strSiteDevise(1)
                       .strLangue("Langue")
                       .strZoneFiscale("Fiscale Zone")
                       .build()
       );

       List<ModulesDto> modules = List.of(
               ModulesDto.builder()
                       .id("1")
                       .name("Module1")
                       .host("localhost")
                       .icon("icon1")
                       .url("http://localhost/module1")
                       .iconClass("iconClass1")
                       .ordre(1)
                       .structureses(structuresDtoList)
                       .build(),
               ModulesDto.builder()
                       .id("2")
                       .name("Module2")
                       .host("localhost")
                       .icon("icon2")
                       .url("http://localhost/module2")
                       .iconClass("iconClass2")
                       .ordre(2)
                       .structureses(structuresDtoList)
                       .build()
       );
        when(menuApplicatifService.getAllModulesNotLinked(1L)).thenReturn(modules);

        mockMvc.perform(get("/api/modules/1/modulesNotLinked"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(modules))); // Validate the response body/contents here
    }
    @Test
     void modulesResourceShouldReturnModulesByStructures() throws Exception {
       List<StructuresDto> structuresDtoList = List.of(
               StructuresDto.builder()
                       .id(1L)
                       .strEcommerceVal(1)
                       .strEtat(2)
                       .strMode(1)
                       .strMaitreCabinet(1)
                       .strRaisonSociale("Raison Sociale")
                       .strDescriptif("Descriptif")
                       .strSigle("SIG")
                       .strNomPays("Country")
                       .strCodePays("C-01")
                       .strIsoPays("ISO")
                       .strDevise("Devise")
                       .strSiteDevise(1)
                       .strLangue("Langue")
                       .strZoneFiscale("Fiscale Zone")
                       .build()
       );

       List<ModulesDto> modules = List.of(
               ModulesDto.builder()
                       .id("1")
                       .name("Module1")
                       .host("localhost")
                       .icon("icon1")
                       .url("http://localhost/module1")
                       .iconClass("iconClass1")
                       .ordre(1)
                       .structureses(structuresDtoList)
                       .build(),
               ModulesDto.builder()
                       .id("2")
                       .name("Module2")
                       .host("localhost")
                       .icon("icon2")
                       .url("http://localhost/module2")
                       .iconClass("iconClass2")
                       .ordre(2)
                       .structureses(structuresDtoList)
                       .build()
       );
        when(menuApplicatifService.getAllModuleByStructures(1L)).thenReturn(modules);

        mockMvc.perform(get("/api/modules/1/modulesByStructures"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(modules))); // Validate the response body/contents here
    }


    @Test
     void modulesResourceShouldUnlinkModuleToStructure() throws Exception {
        mockMvc.perform(delete("/api/modules/1/unlinkedModuleToStructure")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.emptyList()))) // Define your ModulesDto list data here
                .andExpect(status().isOk());
    }
}
