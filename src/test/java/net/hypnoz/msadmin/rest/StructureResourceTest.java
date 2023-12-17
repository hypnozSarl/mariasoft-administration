package net.hypnoz.msadmin.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.hypnoz.msadmin.dtos.GroupesDto;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.service.groupes.GroupeService;
import net.hypnoz.msadmin.service.structres.StructureService;
import net.hypnoz.msadmin.web.rest.GroupesResource;
import net.hypnoz.msadmin.web.rest.StructureResource;
import net.hypnoz.msadmin.web.rest.errors.BadRequestAlertException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StructureResource.class)
class StructureResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StructureService structureService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateGroupeWhenDataIsValid() throws Exception {
        // Arrange
        StructuresDto newStructure = new StructuresDto();
        newStructure.setStrSigle("Code");
        newStructure.setStrRaisonSociale("Libelle Raison Social");



        when(structureService.creationStructure(any())).thenReturn(newStructure);

        // Act and Assert
        mockMvc.perform(post("/api/structures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newStructure)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.strSigle", is(newStructure.getStrSigle())))
                .andExpect(jsonPath("$.strRaisonSociale", is(newStructure.getStrRaisonSociale())));
    }
    @Test
    void houldReturnInternalServerErrorWhenCreationStructureFails() throws Exception {
        StructuresDto input = new StructuresDto();
        input.setId(1L);
        input.setStrRaisonSociale("test");

        mockMvc.perform(post("/api/structures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"strRaisonSociale\": \"test\"}"))
                .andExpect(status().isBadRequest());
    }
    // Test getStructure method, success case.
    @Test
    void shouldReturnAcceptedWhenStructureExists() throws Exception {
        StructuresDto response = new StructuresDto();
        response.setStrRaisonSociale("test");
        response.setId(1L);

        given(structureService.getStructure(any(Long.class))).willReturn(response);

        mockMvc.perform(get("/api/structures/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.strRaisonSociale", is("test")));
    }
    @Test
    void shouldReturnCreatedAndStructureDtoWhenStructureUpdateSucceeds() throws Exception {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setId(1L);
        structuresDto.setStrRaisonSociale("test");
        structuresDto.setStrSigle("testSigle");
        given(structureService.updateStructure(any(StructuresDto.class))).willReturn(structuresDto);

        mockMvc.perform(put("/api/structures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"strRaisonSociale\": \"test\", \"strSigle\": \"testSigle\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.strRaisonSociale", is("test")))
                .andExpect(jsonPath("$.strSigle", is("testSigle")));
    }
    @Test
    void shouldReturnInternalServerErrorWhenStructureUpdateFails() throws Exception {
        StructuresDto structuresDto = new StructuresDto();
        structuresDto.setStrRaisonSociale("test");
        structuresDto.setStrSigle("testSigle");

        given(structureService.updateStructure(any(StructuresDto.class)))
                .willThrow(new BadRequestAlertException("A update structure cannot already haven't an ID",
                        "userManagement", "idnotexists"));

        mockMvc.perform(put("/api/structures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"strRaisonSociale\": \"test\", \"strSigle\": \"testSigle\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Test deleteStructure method, success case.
    @Test
    void shouldReturnOkWhenStructureDeleteSucceeds() throws Exception {
        doNothing().when(structureService).deleteStructure(any(Long.class));

        mockMvc.perform(delete("/api/structures/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Test deleteStructure method, failure case.
    @Test
    void shouldReturnInternalServerErrorWhenStructureDeleteFails() throws Exception {
        doThrow(new RuntimeException()).when(structureService).deleteStructure(any(Long.class));

        mockMvc.perform(delete("/api/structures/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
